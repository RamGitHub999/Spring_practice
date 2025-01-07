package com.ecommerce.ECommerceApp.services.ServiceImpl;

import com.ecommerce.ECommerceApp.Enums.OrderStatus;
import com.ecommerce.ECommerceApp.Exceptions.ResourceNotFoundException;
import com.ecommerce.ECommerceApp.Model.Cart;
import com.ecommerce.ECommerceApp.Model.OrderItem;
import com.ecommerce.ECommerceApp.Model.Orders;
import com.ecommerce.ECommerceApp.Model.Product;
import com.ecommerce.ECommerceApp.PayLoad.OrderDto;
import com.ecommerce.ECommerceApp.Repository.OrderRepository;
import com.ecommerce.ECommerceApp.Repository.ProductRepository;
import com.ecommerce.ECommerceApp.services.IOrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements IOrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartServicesImpl cartService;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public Orders placeOrder(Long userId) {
        Cart cart   = cartService.getCartByUserId(userId);
        Orders order = createOrder(cart);
        List<OrderItem> orderItemList = createOrderItems(order, cart);
        order.setOrderItems(new HashSet<>(orderItemList));
        order.setTotalAmount(calculateTotalAmount(orderItemList));
        Orders savedOrder = orderRepository.save(order);
        cartService.removeCart(cart.getId());
        return savedOrder;
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItemList) {
        return  orderItemList
                .stream()
                .map(item -> item.getPrice()
                        .multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private List<OrderItem> createOrderItems(Orders order, Cart cart) {
        return  cart.getItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory() - cartItem.getQuantity());
            productRepository.save(product);
            return  new OrderItem(
                    order,
                    product,
                    cartItem.getQuantity(),
                    cartItem.getUnitPrice());
        }).toList();
    }

    private Orders createOrder(Cart cart) {
        Orders order = new Orders();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return  order;
    }

    @Override
    public OrderDto getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .map(this :: convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    @Override
    public List<OrderDto> getUserOrders(Long userId) {
        List<Orders> orders = orderRepository.findByUserId(userId);
        return  orders.stream().map(this :: convertToDto).toList();
    }

    private OrderDto convertToDto(Orders order) {
        return modelMapper.map(order, OrderDto.class);
    }
}
