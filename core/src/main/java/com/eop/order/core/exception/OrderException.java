package com.eop.order.core.exception;

public class OrderException extends RuntimeException {


    public OrderException(String msg) {
        super(msg);
    }

    public OrderException(String msg, Exception e) {
        super(msg, e);
    }
}
