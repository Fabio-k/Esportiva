package org.fatec.esportiva.dto.projection;

import java.time.LocalDate;

public interface CategoryProductHistoryView {
    LocalDate getPurchaseDate();
    Long getTotalQuantity();
}
