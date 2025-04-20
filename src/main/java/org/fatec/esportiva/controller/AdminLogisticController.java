package org.fatec.esportiva.controller;

import java.util.List;

import org.fatec.esportiva.entity.enums.OrderStatus;
import org.fatec.esportiva.dto.request.OrderDto;
import org.fatec.esportiva.dto.request.TransactionDto;
import org.fatec.esportiva.service.OrderService;
import org.fatec.esportiva.service.TransactionService;
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

    @GetMapping("/in_processing")
    public String inProcessing(Model model) {
        List<TransactionDto> transactions = transactionService.getTransactions(OrderStatus.EM_PROCESSAMENTO);
        model.addAttribute("transactions", transactions);
        return "admin/logistic/in_processing";
    }

    @GetMapping("/in_transit")
    public String inTransit(Model model) {
        List<TransactionDto> transactions = transactionService.getTransactions(OrderStatus.EM_TRANSITO);
        model.addAttribute("transactions", transactions);
        return "admin/logistic/in_transit";
    }

    @GetMapping("/delivered")
    public String delivered(Model model) {
        List<TransactionDto> transactions = transactionService.getTransactions(OrderStatus.ENTREGUE);
        model.addAttribute("transactions", transactions);
        return "admin/logistic/delivered";
    }

    @GetMapping("/returning")
    public String returning(Model model) {
        List<OrderDto> orders = orderService.getTransactions(OrderStatus.EM_TROCA);
        model.addAttribute("orders", orders);
        return "admin/logistic/returning";
    }

    @GetMapping("/returned")
    public String returned(Model model) {
        List<OrderDto> orders = orderService.getTransactions(OrderStatus.TROCADO);
        model.addAttribute("orders", orders);
        return "admin/logistic/returned";
    }

    @GetMapping("/return_finished")
    public String returnFinished(Model model) {
        List<OrderDto> orders = orderService.getTransactions(OrderStatus.TROCA_FINALIZADA);
        model.addAttribute("orders", orders);
        return "admin/logistic/return_finished";
    }

    @GetMapping("/cancel_deliver")
    public String cancelDeliver(Model model) {
        List<TransactionDto> transactions = transactionService.getTransactions(OrderStatus.COMPRA_CANCELADA);
        model.addAttribute("transactions", transactions);
        return "admin/logistic/cancel_deliver";
    }

    @GetMapping("/cancel_refund")
    public String cancelRefund(Model model) {
        List<OrderDto> orders = orderService.getTransactions(OrderStatus.TROCA_RECUSADA);
        model.addAttribute("orders", orders);
        return "admin/logistic/cancel_refund";
    }

    @GetMapping("/approve")
    public String deliveryPipeline(Model model,
            HttpServletRequest request,
            @RequestParam(value = "approval", required = true) boolean approval,
            @RequestParam(value = "stock", required = true) boolean stock,
            @RequestParam(value = "transaction", required = false, defaultValue = "") String transactionId,
            @RequestParam(value = "order", required = false, defaultValue = "") String orderId) throws Exception {

        if (transactionId != "") {
            long id = Long.parseLong(transactionId);
            transactionService.changeState(id, approval);

        } else if (orderId != "") {
            long id = Long.parseLong(orderId);
            orderService.changeState(id, approval, stock);

        } else {
            throw new Exception("Ao aprovar uma transação/ordem, ambos os ID ficaram nulos");
        }

        // Redireciona para a mesma página que a chamou. E o Controller renderiza
        // corretamente interceptando a mesma URL
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

}
