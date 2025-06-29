package org.fatec.esportiva.controller.admin;

import java.util.List;

import org.fatec.esportiva.entity.enums.OrderStatus;
import org.fatec.esportiva.dto.request.OrderDto;
import org.fatec.esportiva.dto.request.TransactionDto;
import org.fatec.esportiva.service.order.OrderService;
import org.fatec.esportiva.service.transaction.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/logistic")
public class AdminLogisticController {
    private final TransactionService transactionService;
    private final OrderService orderService;

    @GetMapping
    public String inProcessing(Model model) {
        return "admin/logistic/index";
    }
}
