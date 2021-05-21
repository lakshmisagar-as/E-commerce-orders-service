package com.eop.order.resources;

import com.eop.order.core.entities.OrderDetails;
import com.eop.order.core.model.OrderPojo;
import com.eop.order.core.model.OrderResponse;
import com.eop.order.core.services.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/orders")
@AllArgsConstructor
@Slf4j
public class OrderResource {

    private OrderService orderService;

    @GetMapping("/{orderId}")
    public OrderResponse getOrderById(@PathVariable String orderId) {
        log.info("getOrderById");
        return OrderResponse.builder()
                .data(orderService.getOrderById(orderId))
                .status(HttpStatus.OK)
                .build();
    }

    @PostMapping()
    public OrderResponse createOrder(@RequestBody OrderPojo orderDetails) {
        log.info("createOrder");
        return OrderResponse.builder()
                .data(orderService.createOrder(orderDetails))
                .status(HttpStatus.OK)
                .build();
    }

    @PutMapping("/cancel/{orderId}")
    public OrderResponse cancelOrder(@PathVariable String orderId) {
        log.info("cancelOrder");
        return OrderResponse.builder()
                .data(orderService.cancelOrder(orderId) ? "Order Has been canceled successfully" : "Failed to cancel the order")
                .status(HttpStatus.OK)
                .build();
    }
}
