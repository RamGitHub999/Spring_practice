package com.ecommerce.ECommerceApp.services;

import com.ecommerce.ECommerceApp.Model.Orders;
import com.ecommerce.ECommerceApp.PayLoad.OrderDto;

import java.util.List;

public interface IOrderService {
    Orders placeOrder(Long userId);
    OrderDto getOrder(Long orderId);
    List<OrderDto> getUserOrders(Long userId);
}
