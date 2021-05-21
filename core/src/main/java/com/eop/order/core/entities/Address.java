package com.eop.order.core.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address extends BaseEntity {
    private String customerId;
    private String city;
    private String state;
    private String zip;
    private String addressLine1;
    private String addressLine2;
}
