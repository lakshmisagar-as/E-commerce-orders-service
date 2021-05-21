package com.eop.order.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressPojo {
    private String addressId;
    private String customerId;
    private String city;
    private String state;
    private String zip;
    private String addressLine1;
    private String addressLine2;
}
