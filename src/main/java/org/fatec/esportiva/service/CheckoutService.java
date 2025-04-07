package org.fatec.esportiva.service;

import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.entity.CreditCard;
import org.fatec.esportiva.entity.ExchangeVoucher;
import org.fatec.esportiva.entity.session.CheckoutSession;
import org.fatec.esportiva.mapper.CreditCardMapper;
import org.fatec.esportiva.request.CreditCardDto;
import org.fatec.esportiva.response.PromotionalCouponResponseDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckoutService {
    private final CartService cartService;
    private final ExchangeVoucherService exchangeVoucherService;
    private final PromotionalCouponService promotionalCouponService;
    private final CreditCardService creditCardService;

    public BigDecimal calculateTotalPrice(CheckoutSession checkoutSession){
        BigDecimal totalDiscount = BigDecimal.ZERO;
        if(checkoutSession.getExchangeVoucherIds() != null){
            totalDiscount = getTotalDiscount(checkoutSession);
        }

        return getCartTotalPrice().subtract(totalDiscount).max(BigDecimal.ZERO);
    }

    public BigDecimal getCartTotalPrice(){
        return cartService.getTotalCartPrice();
    }

    public BigDecimal getTotalDiscount(CheckoutSession checkoutSession){
        return getExchangeVouchersTotalPrice(checkoutSession).add(getPromotionalCouponDiscount(checkoutSession));
    }

    public BigDecimal getExchangeVouchersTotalPrice(CheckoutSession checkoutSession){
        List <ExchangeVoucher> vouchers = exchangeVoucherService.findAllById(checkoutSession.getExchangeVoucherIds());
        return vouchers.stream()
                .map(ExchangeVoucher::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public  BigDecimal getPromotionalCouponDiscount(CheckoutSession checkoutSession){
        BigDecimal promotionalCouponDiscount = BigDecimal.ZERO;
        PromotionalCouponResponseDto responseDto = promotionalCouponService.getPromotionalCouponOrReturnNull(checkoutSession.getPromotionalCouponCode());
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


    public void validateEachCartHasMinimumPaymentOfTenOnOnlyCreditCardPayment(CheckoutSession checkoutSession){
        if(getTotalDiscount(checkoutSession).compareTo(BigDecimal.ZERO) > 0) return;

        int numberOfCreditCards = getCreditCardsDto(checkoutSession).size();
        if (numberOfCreditCards == 0) return;

        BigDecimal totalPrice = calculateTotalPrice(checkoutSession);
        if (totalPrice.divide(BigDecimal.valueOf(numberOfCreditCards), RoundingMode.UP).compareTo(BigDecimal.TEN) < 0)
            throw new IllegalArgumentException("Cada cartão deve pagar pelo menos R$ 10,00.");
    }

    public void validateCreditCardsCannotBeUsedWhenTotalPriceIsZero(CheckoutSession checkoutSession){
        if (getCreditCardsDto(checkoutSession).isEmpty()) return;

        if(calculateTotalPrice(checkoutSession).compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("O pedido já está sendo totalmente pago através de descontos. Remova os cartões de crédito para continuar.");
    }

    public void validateUnusedExchangeVouchers(CheckoutSession checkoutSession){
        List <ExchangeVoucher> vouchers = exchangeVoucherService.findAllById(checkoutSession.getExchangeVoucherIds());
        if (vouchers.isEmpty()) return;

        BigDecimal priceWithoutExchangeVouchers = getCartTotalPrice().subtract(getPromotionalCouponDiscount(checkoutSession));
        if (getExchangeVouchersTotalPrice(checkoutSession).compareTo(priceWithoutExchangeVouchers) < 1) return;

        vouchers.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        BigDecimal accumulated = BigDecimal.ZERO;
        int usedVouchers = 0;
        for(ExchangeVoucher exchangeVoucher : vouchers){
            accumulated = accumulated.add(exchangeVoucher.getValue());
            usedVouchers++;
            if(accumulated.compareTo(priceWithoutExchangeVouchers) >= 0) break;
        }

        if(usedVouchers < vouchers.size())
            throw new IllegalArgumentException("Você selecionou cupons desnecessários. Remova os cupons extras.");
    }

    public void validateInsufficientPayment(CheckoutSession checkoutSession){
        boolean hasRemainingAmountToPay = calculateTotalPrice(checkoutSession).compareTo(BigDecimal.ZERO) > 0;
        if(hasRemainingAmountToPay && getCreditCardsDto(checkoutSession).isEmpty())
            throw new IllegalArgumentException("Meios de pagamento insuficientes");
    }
}
