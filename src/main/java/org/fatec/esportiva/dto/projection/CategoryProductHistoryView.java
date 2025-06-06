package org.fatec.esportiva.dto.projection;

import java.time.LocalDateTime;

public interface CategoryProductHistoryView {
    LocalDateTime getPurchaseDate();
    Long getTotalQuantity();
}
