package org.fatec.esportiva.dto.response;

import lombok.Getter;
import org.fatec.esportiva.dto.projection.CategoryProductHistoryView;
import org.fatec.esportiva.dto.projection.CategoryProductStateView;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SalesHistoryResponseDto {
    private List<CategoryProductHistoryView> salesHistoryByDate = new ArrayList<>();
    private List<CategoryProductStateView> salesHistoryByState = new ArrayList<>();
}
