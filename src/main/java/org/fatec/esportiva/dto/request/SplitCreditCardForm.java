package org.fatec.esportiva.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SplitCreditCardForm {
    private final List<SplitCreditCardDto> creditCards = new ArrayList<>();

}
