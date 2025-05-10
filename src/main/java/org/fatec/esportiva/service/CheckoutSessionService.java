package org.fatec.esportiva.service;

import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.dto.request.CreditCardDto;
import org.fatec.esportiva.dto.response.AddressResponseDto;
import org.fatec.esportiva.dto.response.PromotionalCouponResponseDto;
import org.fatec.esportiva.entity.CreditCard;
import org.fatec.esportiva.entity.ExchangeVoucher;
import org.fatec.esportiva.entity.session.CheckoutSession;
import org.fatec.esportiva.exception.CheckoutException;
import org.fatec.esportiva.mapper.CreditCardMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckoutSessionService {
    private final CartService cartService;
    private final ExchangeVoucherService exchangeVoucherService;
    private final PromotionalCouponService promotionalCouponService;
    private final CreditCardService creditCardService;
    private final ClientService clientService;


    public BigDecimal calculateTotalPrice(CheckoutSession checkoutSession){
        BigDecimal totalDiscount = getTotalDiscount(checkoutSession);
        BigDecimal freight = getFreight(checkoutSession.getAddress());
        return getCartTotalPrice().add(freight).subtract(totalDiscount).max(BigDecimal.ZERO);
    }

    public BigDecimal getFreight(AddressResponseDto address){
        BigDecimal freight = BigDecimal.ZERO;
        if(address != null) freight = address.getFreight();
        return freight;
    }

    public BigDecimal getCartTotalPrice(){
        return cartService.getTotalCartPrice();
    }

    public BigDecimal getTotalDiscount(CheckoutSession checkoutSession){
        return getExchangeVouchersTotalPrice(checkoutSession.getExchangeVoucherIds()).add(getPromotionalCouponDiscount(checkoutSession.getPromotionalCouponCode()));
    }

    public BigDecimal getExchangeVouchersTotalPrice(List<Long> exchangeVoucherIds){
        if(exchangeVoucherIds == null) return BigDecimal.ZERO;
        List <ExchangeVoucher> vouchers = exchangeVoucherService.findAllById(exchangeVoucherIds);
        return vouchers.stream()
                .map(ExchangeVoucher::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public  BigDecimal getPromotionalCouponDiscount(String promotionalCouponCode){
        BigDecimal promotionalCouponDiscount = BigDecimal.ZERO;
        PromotionalCouponResponseDto responseDto = promotionalCouponService.getPromotionalCouponOrReturnNull(promotionalCouponCode);
        if(responseDto != null)  {
            promotionalCouponDiscount = responseDto.discountPrice();
        }
        return promotionalCouponDiscount;
    }

    public List<CreditCardDto> getCreditCardsDto(CheckoutSession checkoutSession){
        return checkoutSession.getCreditCardIds().stream()
                .map(creditCardId -> {
                    CreditCard creditCard = creditCardService.findCreditCard(creditCardId);
                    return CreditCardMapper.toCreditCardDto(creditCard);
                })
                .toList();
    }

    public void validateInsufficientPayment(CheckoutSession checkoutSession){
        boolean hasRemainingAmountToPay = calculateTotalPrice(checkoutSession).compareTo(BigDecimal.ZERO) > 0;
        if(hasRemainingAmountToPay && getCreditCardsDto(checkoutSession).isEmpty())
            throw new CheckoutException("Meios de pagamento insuficientes", "/checkout/billing");
    }

    public void validatePayment(CheckoutSession checkoutSession){
        List<CreditCardDto> creditCards = getCreditCardsDto(checkoutSession);

        creditCards.forEach(creditCardDto -> {
            if(creditCardDto.getNumber().equals("5115199853098847")){
                throw new IllegalArgumentException("Cartão de crédito inválido");
            }
        });
    }

    public void generateExchangeVoucher(CheckoutSession checkoutSession){
        if(!checkoutSession.getCreditCardIds().isEmpty()) return;

        BigDecimal freight = getFreight(checkoutSession.getAddress());
        BigDecimal totalCost = getCartTotalPrice().add(freight);
        BigDecimal result = totalCost
                .subtract(getTotalDiscount(checkoutSession));

        if(result.compareTo(BigDecimal.ZERO) >= 0) return;
        BigDecimal extraExchangeCouponMoney = result.multiply(BigDecimal.valueOf(-1));
        exchangeVoucherService.createExchangeVoucher(clientService.getAuthenticatedClient(), extraExchangeCouponMoney);
    }

    public void clearCheckoutSession(CheckoutSession checkoutSession) {
        checkoutSession.getExchangeVoucherIds().clear();
        checkoutSession.getCreditCardPayments().clear();
        checkoutSession.setPromotionalCouponCode(null);
        checkoutSession.getCreditCardIds().clear();
        checkoutSession.setAddress(null);
    }
}
