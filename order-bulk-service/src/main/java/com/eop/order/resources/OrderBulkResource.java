package com.eop.order.resources;

import com.eop.order.core.model.OrderPojo;
import com.eop.order.core.model.OrderResponse;
import com.eop.order.core.services.OrderBulkService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/bulk/orders")
@AllArgsConstructor
@Slf4j
public class OrderBulkResource {

    private OrderBulkService orderBulkService;

    @PostMapping()
    public OrderResponse createBulkOrder(@RequestBody List<OrderPojo> orderDetails) {
        log.info("createBulkOrder");
        return OrderResponse.builder()
                .data(orderBulkService.createBulkOrder(orderDetails))
                .status(HttpStatus.OK)
                .build();
    }

    @PutMapping()
    public OrderResponse updateBulkOrder(@RequestBody List<OrderPojo> orderDetails) {
        log.info("updateBulkOrder");
        return OrderResponse.builder()
                .data(orderBulkService.updateBulkOrder(orderDetails))
                .status(HttpStatus.OK)
                .build();
    }
}
