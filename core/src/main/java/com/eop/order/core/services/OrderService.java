package com.eop.order.core.services;

import com.eop.order.core.entities.*;
import com.eop.order.core.exception.OrderException;
import com.eop.order.core.helper.HelperFunctions;
import com.eop.order.core.model.OrderPojo;
import com.eop.order.core.repositories.AddressRepository;
import com.eop.order.core.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;
    private final HelperFunctions helper;

    public OrderPojo getOrderById(String orderId) {
        return helper.transformToOrderPojo(orderRepository.findById(orderId).orElseThrow(() -> new OrderException("Order Not Found")));
    }

    @Transactional
    public OrderPojo createOrder(OrderPojo order) {

        if (order == null) {
            throw new OrderException("Invalid Order details");
        }

        OrderDetails orderDetails = helper.transformToOrderDetails(order);
        addressRepository.save(orderDetails.getShippingAddress());
        orderDetails.setOrderStatus(OrderStatus.PENDING);

        if(!orderDetails.isBillingAddressSame()) {
            addressRepository.saveAll(orderDetails.getPaymentDetails().stream().map(OrderPaymentDetails::getBillingAddress).collect(Collectors.toList()));

        }

        orderRepository.save(orderDetails);

        return helper.transformToOrderPojo(orderDetails);
    }

    public boolean cancelOrder(String orderId) {
        OrderDetails order = orderRepository.findById(orderId).orElseThrow(() -> new OrderException("Order Not Found"));
        order.setOrderStatus(OrderStatus.CANCEL);
        orderRepository.save(order);
        return true;
    }
}
