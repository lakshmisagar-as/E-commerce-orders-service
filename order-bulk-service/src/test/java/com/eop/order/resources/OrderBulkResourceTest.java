package com.eop.order.resources;

import com.eop.order.core.entities.DeliveryType;
import com.eop.order.core.entities.OrderStatus;
import com.eop.order.core.model.AddressPojo;
import com.eop.order.core.model.OrderPojo;
import com.eop.order.core.services.OrderBulkService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderBulkResourceTest {

    @Mock
    OrderBulkService orderService;

    @InjectMocks
    OrderBulkResource resource;

    MockMvc mockMvc;

    List<OrderPojo> orderPojoList=new ArrayList<>();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc= MockMvcBuilders.standaloneSetup(resource).build();
        OrderPojo orderPojo=OrderPojo.builder().orderId("123").orderStatus(OrderStatus.PENDING).customerId("a12")
                .deliveryType(DeliveryType.HOME_DELIVERY).shippingAddress(AddressPojo.builder().addressId("aa")
                        .addressLine1("aa").city("aaa").state("aaa").zip("aaa").customerId("aaa").addressLine2("aaa").build())
                .isBillingAddressSame(true)
                .paymentDetails(new ArrayList<>()).items(new ArrayList<>()).subTotal(BigDecimal.valueOf(100))
                .tax(BigDecimal.valueOf(10)).totalAmount(BigDecimal.valueOf(110)).build();
        orderPojoList.add(orderPojo);
    }

    @Test
    void createBulkOrder() throws Exception {

        ObjectMapper objectMapper=new ObjectMapper();
        ObjectWriter objectWriter=objectMapper.writer();

        String data= objectWriter.writeValueAsString(orderPojoList);

        when(orderService.createBulkOrder(any())).thenReturn(orderPojoList);

        mockMvc.perform(post("/v1/bulk/orders").contentType(MediaType.APPLICATION_JSON).content(data))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].orderId").value(orderPojoList.get(0).getOrderId()));

        verify(orderService).createBulkOrder(any());
    }

    @Test
    void updateBulkOrder() throws Exception {

        ObjectMapper objectMapper=new ObjectMapper();
        ObjectWriter objectWriter=objectMapper.writer();

        String data= objectWriter.writeValueAsString(orderPojoList);

        when(orderService.updateBulkOrder(any())).thenReturn(orderPojoList);

        mockMvc.perform(put("/v1/bulk/orders").contentType(MediaType.APPLICATION_JSON).content(data))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].orderId").value(orderPojoList.get(0).getOrderId()));

        verify(orderService).updateBulkOrder(any());

    }
}