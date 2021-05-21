package com.eop.order.core.helper;

import com.eop.order.core.entities.*;
import com.eop.order.core.model.AddressPojo;
import com.eop.order.core.model.ItemPojo;
import com.eop.order.core.model.OrderPojo;
import com.eop.order.core.model.PaymentPojo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class HelperFunctionsTest {

    HelperFunctions helperFunctions;

    @BeforeEach
    void setUp() {
        helperFunctions=new HelperFunctions();
    }

    @Test
    void transformToOrderPojo() {

        List<OrderPaymentDetails> orderPaymentDetails=new ArrayList<>();

        Address address=Address.builder().customerId("111").addressLine1("aaa").addressLine2("bbb")
                .city("aaa").state("aaa").zip("111").build();

        orderPaymentDetails.add(OrderPaymentDetails.builder().paymentMode(PaymentMode.CREDIT_CARD)
                .billingAddress(address).paidAmount(BigDecimal.valueOf(100)).build());

        List<OrderItems> orderItems=new ArrayList<>();

        orderItems.add(OrderItems.builder()
                .amount(BigDecimal.valueOf(100))
                .productId("aa")
                .quantity(BigDecimal.valueOf(1))
                .build());

        OrderDetails orderDetails= OrderDetails.builder().orderStatus(OrderStatus.PENDING).customerId("aaa")
                .subTotal(BigDecimal.valueOf(100)).tax(BigDecimal.valueOf(10)).totalAmount(BigDecimal.valueOf(110))
                .deliveryType(DeliveryType.HOME_DELIVERY).shippingAddress(new Address()).orderItems(orderItems)
                .paymentDetails(orderPaymentDetails).build();

        assertEquals(orderDetails.getCustomerId(),helperFunctions.transformToOrderPojo(orderDetails).getCustomerId());

    }

    @Test
    void transformAddress() {
       AddressPojo addressPojo= AddressPojo.builder()
                .city("aaa")
                .customerId("bbb")
                .state("ccc")
                .addressLine1("ddd")
                .addressId("eee")
                .addressLine2("fff").build();

       assertEquals(addressPojo.getAddressId(),helperFunctions.transformAddress(addressPojo).getId());
    }

    @Test
    void transToAddress() {
        Address address = Address.builder()
                .city("aaa")
                .customerId("bbb")
                .state("ccc")
                .addressLine1("ddd")
                .addressLine2("fff").build();

        assertEquals(address.getCustomerId(),helperFunctions.transToAddress(address).getCustomerId());
    }

    @Test
    void transformToOrderDetails() {
        List<PaymentPojo> paymentPojoList=new ArrayList<>();
        paymentPojoList.add(PaymentPojo.builder().paymentId("111").paymentMode(PaymentMode.CREDIT_CARD)
                .billingAddress(AddressPojo.builder().addressId("aa")
                        .addressLine1("aa").city("aaa").state("aaa").zip("aaa").customerId("aaa").addressLine2("aaa").build())
                .paidAmount(BigDecimal.valueOf(100))
                .build());

        List<ItemPojo> itemPojos=new ArrayList<>();

        itemPojos.add(ItemPojo.builder()
                .orderItemId("123")
                .amount(BigDecimal.valueOf(100))
                .productId("aaa")
                .quantity(BigDecimal.valueOf(1))
                .build());

        OrderPojo orderPojo=OrderPojo.builder().orderId("123").orderStatus(OrderStatus.PENDING).customerId("a12")
                .deliveryType(DeliveryType.HOME_DELIVERY).shippingAddress(AddressPojo.builder().addressId("aa")
                        .addressLine1("aa").city("aaa").state("aaa").zip("aaa").customerId("aaa").addressLine2("aaa").build())
                .isBillingAddressSame(true)
                .paymentDetails(paymentPojoList).items(itemPojos).subTotal(BigDecimal.valueOf(100))
                .tax(BigDecimal.valueOf(10)).totalAmount(BigDecimal.valueOf(110)).build();

        assertEquals(orderPojo.getCustomerId(),helperFunctions.transformToOrderDetails(orderPojo).getCustomerId());
    }
}