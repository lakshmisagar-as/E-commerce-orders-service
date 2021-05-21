package com.eop.order.core.exception;

import com.eop.order.core.model.OrderResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class OrderExceptionController {

    @ExceptionHandler(value = OrderException.class)
    public ResponseEntity<OrderResponse> orderException(OrderException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<OrderResponse>(OrderResponse.builder().errorMessage(e.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<OrderResponse> exception(Exception e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<OrderResponse>(OrderResponse.builder().errorMessage("Internal Server Error").build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}