package com.eop.order.core.services;

import com.eop.order.core.entities.Address;
import com.eop.order.core.entities.OrderDetails;
import com.eop.order.core.entities.OrderPaymentDetails;
import com.eop.order.core.entities.OrderStatus;
import com.eop.order.core.exception.OrderException;
import com.eop.order.core.helper.HelperFunctions;
import com.eop.order.core.model.OrderPojo;
import com.eop.order.core.repositories.AddressRepository;
import com.eop.order.core.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderBulkService {
    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;
    private final HelperFunctions helper;

    @Transactional
    public List<OrderPojo> createBulkOrder(List<OrderPojo> orders) {

        if (orders == null || orders.isEmpty()) {
            throw new OrderException("Invalid Orders detail");
        }

        List<OrderDetails> ordersDetail = new ArrayList<>();
        for(OrderPojo order : orders) {
            OrderDetails orderDetails = helper.transformToOrderDetails(order);
            ordersDetail.add(orderDetails);
        }

        addressRepository.saveAll(ordersDetail.stream().map(OrderDetails::getShippingAddress).collect(Collectors.toList()));

        ordersDetail.stream().forEach( orderDetails -> orderDetails.setOrderStatus(OrderStatus.PENDING));

        List<Address> addresses = new ArrayList<>();
        for(OrderDetails orderDetails : ordersDetail) {
            if(!orderDetails.isBillingAddressSame()) {
                addresses.addAll(orderDetails.getPaymentDetails().stream().map(OrderPaymentDetails::getBillingAddress).collect(Collectors.toList()));
            }
        }

        addressRepository.saveAll(addresses);
        orderRepository.saveAll(ordersDetail);

        return ordersDetail.stream().map( orderDetails -> helper.transformToOrderPojo(orderDetails)).collect(Collectors.toList());
    }

    public List<OrderPojo> updateBulkOrder(List<OrderPojo> orders) {

        List<OrderDetails> ordersDetail = new ArrayList<>();
        for(OrderPojo orderPojo : orders) {
            OrderDetails order = orderRepository.findById(orderPojo.getOrderId()).orElseThrow(() -> new OrderException("Orders Not Found"));
            order.setOrderStatus(orderPojo.getOrderStatus());
            ordersDetail.add(order);
        }

        orderRepository.saveAll(ordersDetail);
        return ordersDetail.stream().map( orderDetails -> helper.transformToOrderPojo(orderDetails)).collect(Collectors.toList());
    }

}
