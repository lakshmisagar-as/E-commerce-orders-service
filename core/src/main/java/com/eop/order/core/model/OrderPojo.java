package com.eop.order.core.model;

import com.eop.order.core.entities.DeliveryType;
import com.eop.order.core.entities.OrderStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderPojo {

    private String orderId;

    private OrderStatus orderStatus;

    private String customerId;

    private BigDecimal subTotal;

    private BigDecimal tax;

    private BigDecimal totalAmount;

    private AddressPojo shippingAddress;

    private boolean isBillingAddressSame = false;

    private DeliveryType deliveryType;

    private List<ItemPojo> items;

    private List<PaymentPojo> paymentDetails;
}
