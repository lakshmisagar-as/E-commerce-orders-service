package com.eop.order.core.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderPaymentDetails extends BaseEntity {

    public BigDecimal paidAmount;
    public PaymentMode paymentMode;

    @OneToOne(cascade = CascadeType.ALL)
    private Address billingAddress;

    @ManyToOne
    @JsonBackReference
    @ToString.Exclude
    public OrderDetails order;
}
