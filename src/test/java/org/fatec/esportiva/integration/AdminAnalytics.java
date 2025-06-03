package org.fatec.esportiva.integration;

import org.fatec.esportiva.entity.Client;
import org.fatec.esportiva.entity.Order;
import org.fatec.esportiva.entity.Transaction;
import org.fatec.esportiva.entity.address.Cep;
import org.fatec.esportiva.entity.enums.OrderStatus;
import org.fatec.esportiva.entity.product.Product;
import org.fatec.esportiva.repository.*;
import org.fatec.esportiva.utils.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AdminAnalytics extends Integration{
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CepRepository cepRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldRedirectUnauthenticatedUser() throws Exception{
        mockMvc.perform(get("/api/sales-history?id=1&isCategory=false"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @WithMockUser(username = "pedro", roles = "USER")
    void shouldRedirectUnauthorizedUser() throws Exception{
        mockMvc.perform(get("/api/sales-history?id=1&isCategory=false"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    @WithMockUser(username = "carlos", roles = "ADMIN")
    void shouldNotReturnOrdersOuOfSalesReportStatus() throws Exception{
        transactionRepository.deleteAll();

        Client client = ClientFactory.defaultClient();

        Cep cep = CepFactory.defaultCep();
        cep.setState("São Paulo");
        cep = cepRepository.save(cep);

        Transaction transaction = TransactionFactory.defaultTransaction(cep);
        transaction.setClient(client);

        Product product = ProductFactory.defaultProduct();
        productRepository.save(product);
        productRepository.flush();

        Order processingOrder = OrderFactory.defaultOrder(transaction);
        processingOrder.setStatus(OrderStatus.EM_PROCESSAMENTO);
        processingOrder.setProduct(product);

        Order inTransitOrder = OrderFactory.defaultOrder(transaction);
        inTransitOrder.setStatus(OrderStatus.EM_TRANSITO);
        inTransitOrder.setProduct(product);

        Order inTradeOrder = OrderFactory.defaultOrder(transaction);
        inTradeOrder.setStatus(OrderStatus.EM_TROCA);
        inTradeOrder.setProduct(product);

        transaction.setOrders(List.of(processingOrder, inTransitOrder, inTradeOrder));

        client.setTransactions(List.of(transaction));
        clientRepository.save(client);

        mockMvc.perform(get("/api/sales-history?id=" + product.getId() +  "&isCategory=false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.salesHistoryByDate.length()").value(0))
                .andExpect(jsonPath("$.salesHistoryByState.length()").value(0));

    }


    @Test
    @WithMockUser(username = "carlos", roles = "ADMIN")
    void shouldReturnStatistics() throws Exception{
        transactionRepository.deleteAll();

        Client client = ClientFactory.defaultClient();

        Cep cep = CepFactory.defaultCep();
        cep.setState("São Paulo");
        cep = cepRepository.save(cep);

        Transaction transaction = TransactionFactory.defaultTransaction(cep);
        transaction.setClient(client);
        transaction.setStatus(OrderStatus.ENTREGUE);

        Order order = OrderFactory.defaultOrder(transaction);
        order.setStatus(OrderStatus.ENTREGUE);

        transaction.setOrders(List.of(order));

        client.setTransactions(List.of(transaction));
        clientRepository.save(client);
        clientRepository.flush();


        mockMvc.perform(get("/api/sales-history?id=" + order.getProduct().getId() +  "&isCategory=false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.salesHistoryByDate.length()").value(1))
                .andExpect(jsonPath("$.salesHistoryByState.length()").value(1));

    }

    @Test
    @WithMockUser(username = "carlos", roles = "ADMIN")
    void shouldNotReturnProductsOutOfTimeInterval() throws Exception{
        transactionRepository.deleteAll();

        Client client = ClientFactory.defaultClient();

        Cep cep = CepFactory.defaultCep();
        cep.setState("São Paulo");
        cep = cepRepository.save(cep);

        Product product = ProductFactory.defaultProduct();
        productRepository.save(product);
        productRepository.flush();

        Transaction transactionBeforeFilter = TransactionFactory.defaultTransaction(cep);
        transactionBeforeFilter.setClient(client);
        Order order = OrderFactory.defaultOrder(transactionBeforeFilter);
        order.setProduct(product);
        order.setStatus(OrderStatus.ENTREGUE);
        transactionBeforeFilter.setStatus(OrderStatus.ENTREGUE);
        transactionBeforeFilter.setOrders(List.of(order));
        transactionBeforeFilter.setPurchaseDate(LocalDateTime.of(2025, 5, 4, 23, 59));

        Transaction validTransaction = TransactionFactory.defaultTransaction(cep);
        validTransaction.setClient(client);
        validTransaction.setStatus(OrderStatus.ENTREGUE);
        validTransaction.setPurchaseDate(LocalDateTime.of(2025, 5, 6, 10, 10));
        order = OrderFactory.deliveredOrder(validTransaction);
        order.setProduct(product);
        order.setQuantity(8);
        validTransaction.setOrders(List.of(order));

        Transaction transaction = TransactionFactory.defaultTransaction(cep);
        transaction.setClient(client);
        transaction.setStatus(OrderStatus.ENTREGUE);
        transaction.setPurchaseDate(LocalDateTime.of(2025, 5, 7, 0, 1));

        Order processingOrder = OrderFactory.defaultOrder(transaction);
        processingOrder.setStatus(OrderStatus.ENTREGUE);
        processingOrder.setProduct(product);

        transaction.setOrders(List.of(processingOrder));

        client.setTransactions(List.of(transaction, transactionBeforeFilter, validTransaction));
        clientRepository.save(client);

        mockMvc.perform(get("/api/sales-history?id=" + product.getId() +  "&isCategory=false&startDate=2025-05-05&endDate=2025-05-06"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.salesHistoryByDate.length()").value(1))
                .andExpect(jsonPath("$.salesHistoryByState.length()").value(1))
                .andExpect(jsonPath("$.salesHistoryByDate[0].totalQuantity").value(8))
                .andExpect(jsonPath("$.salesHistoryByState[0].totalQuantity").value(8));

    }
}
