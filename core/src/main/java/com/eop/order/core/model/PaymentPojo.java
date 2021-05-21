package com.eop.order.core.model;

import com.eop.order.core.entities.PaymentMode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentPojo {

    private String paymentId;
    private BigDecimal paidAmount;
    private PaymentMode paymentMode;

    private AddressPojo billingAddress;
}
