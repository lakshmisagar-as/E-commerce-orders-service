package com.eop.order.core.helper;

import com.eop.order.core.entities.Address;
import com.eop.order.core.entities.OrderDetails;
import com.eop.order.core.entities.OrderItems;
import com.eop.order.core.entities.OrderPaymentDetails;
import com.eop.order.core.model.AddressPojo;
import com.eop.order.core.model.ItemPojo;
import com.eop.order.core.model.OrderPojo;
import com.eop.order.core.model.PaymentPojo;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class HelperFunctions {

    public OrderPojo transformToOrderPojo(OrderDetails order) {
        OrderPojo orderDetails = OrderPojo.builder()
                .customerId(order.getCustomerId())
                .orderId(order.getId())
                .orderStatus(order.getOrderStatus())
                .subTotal(order.getSubTotal())
                .tax(order.getTax())
                .shippingAddress(transToAddress(order.getShippingAddress()))
                .isBillingAddressSame(order.isBillingAddressSame())
                .totalAmount(order.getTotalAmount())
                .deliveryType(order.getDeliveryType())
                .build();

        orderDetails.setPaymentDetails(order.getPaymentDetails().stream().map(d ->
                PaymentPojo.builder()
                        .paymentId(d.getId())
                        .paidAmount(d.getPaidAmount())
                        .paymentMode(d.getPaymentMode())
                        .billingAddress(transToAddress(d.getBillingAddress()))
                        .build()
        ).collect(Collectors.toList()));


        orderDetails.setItems(order.getOrderItems().stream().map(d ->
                ItemPojo.builder()
                        .orderItemId(d.getId())
                        .amount(d.getAmount())
                        .productId(d.getProductId())
                        .quantity(d.getQuantity())
                        .build()
        ).collect(Collectors.toList()));

        return orderDetails;
    }


    public AddressPojo transToAddress(Address ad) {
        if(ad == null) {
            return null;
        }

        return AddressPojo.builder()
                .city(ad.getCity())
                .customerId(ad.getCustomerId())
                .state(ad.getState())
                .addressLine1(ad.getAddressLine1())
                .addressId(ad.getId())
                .addressLine2(ad.getAddressLine2()).build();
    }

    public Address transformAddress(AddressPojo ad) {
        if(ad == null ) {
            return null;
        }

        Address address = Address.builder()
                .city(ad.getCity())
                .customerId(ad.getCustomerId())
                .state(ad.getState())
                .addressLine1(ad.getAddressLine1())
                .addressLine2(ad.getAddressLine2())
                .zip(ad.getZip())
                .build();
        address.setId(ad.getAddressId());

        return address;
    }


    public OrderDetails transformToOrderDetails(OrderPojo order) {
        OrderDetails orderDetails = OrderDetails.builder()
                .customerId(order.getCustomerId())
                .orderStatus(order.getOrderStatus())
                .subTotal(order.getSubTotal())
                .shippingAddress(transformAddress(order.getShippingAddress()))
                .isBillingAddressSame(order.isBillingAddressSame())
                .tax(order.getTax())
                .deliveryType(order.getDeliveryType())
                .totalAmount(order.getTotalAmount())
                .build();

        orderDetails.setPaymentDetails(order.getPaymentDetails().stream().map(d ->
                OrderPaymentDetails.builder()
                        .order(orderDetails)
                        .billingAddress(order.isBillingAddressSame()?orderDetails.getShippingAddress():transformAddress(d.getBillingAddress()))
                        .paidAmount(d.getPaidAmount())
                        .paymentMode(d.getPaymentMode())
                        .build()
        ).collect(Collectors.toList()));


        orderDetails.setOrderItems(order.getItems().stream().map(d ->
                OrderItems.builder()
                        .amount(d.getAmount())
                        .order(orderDetails)
                        .productId(d.getProductId())
                        .quantity(d.getQuantity())
                        .build()
        ).collect(Collectors.toList()));

        return orderDetails;
    }

}
