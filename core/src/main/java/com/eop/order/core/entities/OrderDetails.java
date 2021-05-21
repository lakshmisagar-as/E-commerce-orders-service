package com.eop.order.core.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetails extends BaseEntity {

    private OrderStatus orderStatus;

    private String customerId;

    private BigDecimal subTotal;

    private BigDecimal tax;

    private BigDecimal totalAmount;

    private boolean isBillingAddressSame = false;

    private DeliveryType deliveryType;

    @OneToOne(cascade = CascadeType.ALL)
    private Address shippingAddress;

    @OneToMany(mappedBy="order", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrderItems> orderItems;

    @OneToMany(mappedBy="order", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrderPaymentDetails> paymentDetails;

}
