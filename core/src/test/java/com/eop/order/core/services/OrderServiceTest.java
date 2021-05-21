package com.eop.order.core.services;

import com.eop.order.core.entities.*;
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
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderServiceTest {

    @Mock
    OrderRepository orderRepository;

    @Mock
    AddressRepository addressRepository;

    @Mock
    HelperFunctions helperFunctions;

    @InjectMocks
    OrderService orderService;

    OrderDetails orderDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        List<OrderPaymentDetails> orderPaymentDetails=new ArrayList<>();

        Address address=Address.builder().customerId("111").addressLine1("aaa").addressLine2("bbb")
                .city("aaa").state("aaa").zip("111").build();

        orderPaymentDetails.add(OrderPaymentDetails.builder().paymentMode(PaymentMode.CREDIT_CARD)
                .billingAddress(address).paidAmount(BigDecimal.valueOf(100)).build());

        orderDetails=OrderDetails.builder().orderStatus(OrderStatus.PENDING).customerId("aaa")
                .subTotal(BigDecimal.valueOf(100)).tax(BigDecimal.valueOf(10)).totalAmount(BigDecimal.valueOf(110))
                .deliveryType(DeliveryType.HOME_DELIVERY).shippingAddress(address).orderItems(new ArrayList<>())
                .paymentDetails(orderPaymentDetails).build();

    }

    @Test
    void getOrderById() {
        when(orderRepository.findById(anyString())).thenReturn(java.util.Optional.ofNullable(orderDetails));

        orderService.getOrderById(anyString());

        verify(orderRepository).findById(anyString());
    }

    @Test
    void createOrder() {

        List<PaymentPojo> paymentPojoList=new ArrayList<>();
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


        when(addressRepository.save(any())).thenReturn(null);
        when(orderRepository.save(any())).thenReturn(orderDetails);
        when(helperFunctions.transformToOrderDetails(any())).thenReturn(orderDetails);
        when(helperFunctions.transformToOrderPojo(any())).thenReturn(orderPojo);

        assertEquals(orderService.createOrder(orderPojo).getCustomerId(),orderPojo.getCustomerId());
    }

    @Test
    void createOrderNull() {

        Exception exception=assertThrows(OrderException.class,()->orderService.createOrder(null));

        assertEquals("Invalid Order details",exception.getMessage());
    }

    @Test
    void cancelOrder() {
        when(orderRepository.findById(anyString())).thenReturn(java.util.Optional.ofNullable(orderDetails));

        orderService.cancelOrder(anyString());

        verify(orderRepository).findById(anyString());
    }
}