package com.eop.order.core.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItems extends BaseEntity{

    private BigDecimal quantity;
    private BigDecimal amount;
    private String productId;

    @ManyToOne
    @JsonBackReference
    @ToString.Exclude
    private OrderDetails order;
}
