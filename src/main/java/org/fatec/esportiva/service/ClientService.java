package org.fatec.esportiva.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.dto.request.CreditCardDto;
import org.fatec.esportiva.dto.response.CartItemResponseDto;
import org.fatec.esportiva.entity.address.Address;
import org.fatec.esportiva.entity.*;
import org.fatec.esportiva.entity.cart.Cart;
import org.fatec.esportiva.entity.enums.Gender;
import org.fatec.esportiva.entity.enums.UserStatus;
import org.fatec.esportiva.mapper.*;
import org.fatec.esportiva.repository.ClientRepository;
import org.fatec.esportiva.dto.request.ClientDto;
import org.fatec.esportiva.dto.response.AddressResponseDto;
import org.fatec.esportiva.dto.response.CartResponseDto;
import org.fatec.esportiva.dto.response.ExchangeVoucherResponseDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final AddressService addressService;
    private final ClientRepository clientRepository;
    private final CreditCardService creditCardService;
    private final ExchangeVoucherService exchangeVoucherService;
    private final AuthService authService;
    private final CurrencyService currencyService;
    private final FreightService freightService;
    private final CartMapper cartMapper;

    @Transactional
    public Client save(ClientDto clientDto) {
        Client client = ClientMapper.toUser(clientDto);
        client.setAddresses(addressService.createAddresses(client, clientDto.getAddresses()));
        client.setCreditCards(creditCardService.createCreditCards(client, clientDto.getCreditCards()));
        client.setStatus(UserStatus.ATIVO);
        return clientRepository.save(client);
    }

    @Transactional
    public Client update(Long id, ClientDto userDto) {
        Client existingUser = this.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        existingUser.setName(userDto.getName());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setCpf(userDto.getCpf());
        existingUser.setGender(userDto.getGender());
        existingUser.setStatus(userDto.getStatus());
        existingUser.setDateBirth(userDto.getDateBirth());
        existingUser.setTelephoneType(userDto.getTelephoneType());
        existingUser.setTelephone(userDto.getTelephone());

        List<Address> updatedAddresses = addressService.updateOrCreateAddress(existingUser, userDto.getAddresses());
        List<CreditCard> updatedCreditCards = creditCardService.updateOrCreateCreditCards(existingUser,
                userDto.getCreditCards());
        List<ExchangeVoucher> updatedExchangeVoucher = exchangeVoucherService
                .updateOrCreateExchangeVoucher(existingUser, userDto.getExchangeVoucherDtos());

        existingUser.getAddresses().clear();
        existingUser.getAddresses().addAll(updatedAddresses);

        existingUser.getCreditCards().clear();
        existingUser.getCreditCards().addAll(updatedCreditCards);

        existingUser.getExchangeVouchers().clear();
        existingUser.getExchangeVouchers().addAll(updatedExchangeVoucher);

        return clientRepository.save(existingUser);
    }

    public List<ClientDto> getClients(String name, String email, String cpf, UserStatus status, Gender gender) {
        String formattedName = normalize(name) == null ? null : "%" + name + "%";
        String formattedEmail = normalize(email) == null ? null : "%" + email + "%";
        List<ClientDto> clients = clientRepository
                .findWithFilter(formattedName, formattedEmail, normalize(cpf), status, gender).stream()
                .map(ClientMapper::toUserDto).toList();
        return clients;
    }

    public Client findClient(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return client;
    }

    public Client getAuthenticatedClient(){
        UserDetails userDetails = authService.getAuthenticatedUser();
        return clientRepository.findClientByEmail(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    public CartResponseDto getCart(){
        Cart cart = getAuthenticatedClient().getCart();

        return cartMapper.toCartResponseDto(cart);
    }

    public List<ExchangeVoucherResponseDto> getClientVouchers(){
        List<ExchangeVoucher> validExchangeVouchers = exchangeVoucherService.findAllValidExchangeVouchersByClientId(getAuthenticatedClient().getId());
        return  validExchangeVouchers.stream()
                .map(voucher -> ExchangeVoucherMapper.toExchangeVoucherResponseDto(voucher, currencyService.format(voucher.getValue())))
                .toList();
    }

    public List<AddressResponseDto> getClientAddresses(){
        return getAuthenticatedClient().getAddresses().stream()
                .map(address -> {
                    BigDecimal freight = freightService.calculate(address.getCep().getState(), getCart().items());
                    return AddressMapper.toAddressDtoResponse(address, freight, currencyService.format(freight));
                })
                .toList();
    }

    public List<CartItemResponseDto> getClientCartItems(){
        return getAuthenticatedClient().getCart().getCartItems()
                .stream().map(CartItemMapper::toCartItemResponseDto).toList();
    }

    public List<CreditCardDto> getClientCreditCards() {
        return getAuthenticatedClient().getCreditCards().stream()
                .map(CreditCardMapper::toCreditCardDto).toList();
    }

    public void deleteClient(Client user) {
        clientRepository.delete(user);
    }

    public Map<String, Integer> getClientHistory(){
        Map<String, Integer> productHistory = new HashMap<>();
        List<Transaction> transactions = getAuthenticatedClient().getTransactions();

        for(Transaction transaction : transactions){
            for(Order order : transaction.getOrders()){
                if(!order.isInDeliveryProcess()) continue;
                String productName = order.getProduct().getName();
                Integer productQuantity = order.getQuantity();
                productHistory.put(productName, productHistory.getOrDefault(productName, 0) + productQuantity);
            }
        }

        return productHistory;
    }

    private String normalize(String value) {
        return (value == null || value.trim().isEmpty()) ? null : value;
    }
}
