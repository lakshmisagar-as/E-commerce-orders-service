package com.eop.order.core.services;

import com.eop.order.core.entities.DeliveryType;
import com.eop.order.core.entities.OrderDetails;
import com.eop.order.core.entities.OrderStatus;
import com.eop.order.core.entities.PaymentMode;
import com.eop.order.core.exception.OrderException;
import com.eop.order.core.helper.HelperFunctions;
import com.eop.order.core.model.AddressPojo;
import com.eop.order.core.model.ItemPojo;
import com.eop.order.core.model.OrderPojo;
import com.eop.order.core.model.PaymentPojo;
import com.eop.order.core.repositories.AddressRepository;
import com.eop.order.core.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class OrderBulkServiceTest {

    @Mock
    OrderRepository orderRepository;

    @Mock
    AddressRepository addressRepository;

    @Mock
    HelperFunctions helperFunctions;

    @InjectMocks
    OrderBulkService orderBulkService;

    List<OrderPojo> orderPojoList;

    OrderDetails orderDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        List<PaymentPojo> paymentPojoList=new ArrayList<>();
        orderPojoList=new ArrayList<>();
        paymentPojoList.add(PaymentPojo.builder().paymentId("111").paymentMode(PaymentMode.CREDIT_CARD)
                .billingAddress(AddressPojo.builder().addressId("aa")
                        .addressLine1("aa").city("aaa").state("aaa").zip("aaa").customerId("aaa").addressLine2("aaa").build())
                .paidAmount(BigDecimal.valueOf(100))
                .build());

        OrderPojo orderPojo=OrderPojo.builder().orderId("123").orderStatus(OrderStatus.PENDING).customerId("a12")
                .deliveryType(DeliveryType.HOME_DELIVERY).shippingAddress(AddressPojo.builder().addressId("aa")
                        .addressLine1("aa").city("aaa").state("aaa").zip("aaa").customerId("aaa").addressLine2("aaa").build())
                .isBillingAddressSame(true)
                .paymentDetails(paymentPojoList).items(new ArrayList<>()).subTotal(BigDecimal.valueOf(100))
                .tax(BigDecimal.valueOf(10)).totalAmount(BigDecimal.valueOf(110)).build();
        orderPojoList.add(orderPojo);

        orderDetails = OrderDetails.builder()
                .customerId(orderPojo.getCustomerId())
                .orderStatus(orderPojo.getOrderStatus())
                .subTotal(orderPojo.getSubTotal())
                .isBillingAddressSame(orderPojo.isBillingAddressSame())
                .tax(orderPojo.getTax())
                .deliveryType(orderPojo.getDeliveryType())
                .totalAmount(orderPojo.getTotalAmount())
                .build();
    }

    @Test
    void createBulkOrderNUll() {
        Exception exception=assertThrows(OrderException.class,()->orderBulkService.createBulkOrder(null));

        assertEquals("Invalid Orders detail",exception.getMessage());
    }

    @Test
    void createOrder() {

        when(addressRepository.saveAll(any())).thenReturn(null);

        when(orderRepository.saveAll(any())).thenReturn(null);

        when(helperFunctions.transformToOrderDetails(any())).thenReturn(orderDetails);

        assertEquals(1,orderBulkService.createBulkOrder(orderPojoList).size());

        Mockito.verify(addressRepository,times(2)).saveAll(any());
        Mockito.verify(orderRepository).saveAll(any());
        verify(helperFunctions).transformToOrderDetails(any());
    }

    @Test
    void updateBulkOrderNull() {
        Exception exception=assertThrows(OrderException.class,()->orderBulkService.updateBulkOrder(orderPojoList));

        assertEquals("Orders Not Found",exception.getMessage());
    }

    @Test
    void updateBulkOrder(){

        when(orderRepository.findById(anyString())).thenReturn(java.util.Optional.ofNullable(orderDetails));

        when(orderRepository.saveAll(any())).thenReturn(null);

        when(helperFunctions.transformToOrderPojo(any())).thenReturn(orderPojoList.get(0));

        assertEquals(1,orderBulkService.updateBulkOrder(orderPojoList).size());

        Mockito.verify(orderRepository).saveAll(any());
        Mockito.verify(orderRepository).findById(anyString());
        Mockito.verify(helperFunctions).transformToOrderPojo(any());

    }
}