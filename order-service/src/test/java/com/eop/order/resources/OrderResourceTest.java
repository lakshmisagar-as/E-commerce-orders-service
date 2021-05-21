package com.eop.order.resources;

import com.eop.order.core.entities.DeliveryType;
import com.eop.order.core.entities.OrderStatus;
import com.eop.order.core.model.AddressPojo;
import com.eop.order.core.model.OrderPojo;
import com.eop.order.core.services.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderResourceTest {

    @Mock
    OrderService orderService;

    @InjectMocks
    OrderResource resource;

    MockMvc mockMvc;

    OrderPojo orderPojo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc= MockMvcBuilders.standaloneSetup(resource).build();

        orderPojo=OrderPojo.builder().orderId("123").orderStatus(OrderStatus.PENDING).customerId("a12")
                .deliveryType(DeliveryType.HOME_DELIVERY).shippingAddress(AddressPojo.builder().addressId("aa")
                        .addressLine1("aa").city("aaa").state("aaa").zip("aaa").customerId("aaa").addressLine2("aaa").build())
                .isBillingAddressSame(true)
                .paymentDetails(new ArrayList<>()).items(new ArrayList<>()).subTotal(BigDecimal.valueOf(100))
                .tax(BigDecimal.valueOf(10)).totalAmount(BigDecimal.valueOf(110)).build();
    }

    @Test
    void getOrderById() throws Exception {

        when(orderService.getOrderById(anyString())).thenReturn(orderPojo);

        mockMvc.perform(get("/v1/orders/123"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.orderId").value(orderPojo.getOrderId()));
        verify(orderService).getOrderById(anyString());

    }

    @Test
    void createOrder() throws Exception {
        when(orderService.createOrder(any())).thenReturn(orderPojo);

        ObjectMapper objectMapper=new ObjectMapper();
        ObjectWriter objectWriter=objectMapper.writer();

        String requestBody=objectWriter.writeValueAsString(orderPojo);

        mockMvc.perform(post("/v1/orders").contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.orderId").value(orderPojo.getOrderId()));

        verify(orderService).createOrder(any());
    }

    @Test
    void cancelOrderSuccess() throws Exception {
        when(orderService.cancelOrder(anyString())).thenReturn(true);

        mockMvc.perform(put("/v1/orders/cancel/123").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("Order Has been canceled successfully"));

        verify(orderService).cancelOrder(anyString());
    }

    @Test
    void cancelOrderFailure() throws Exception {
        when(orderService.cancelOrder(anyString())).thenReturn(false);

        mockMvc.perform(put("/v1/orders/cancel/123").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("Failed to cancel the order"));

        verify(orderService).cancelOrder(anyString());
    }
}