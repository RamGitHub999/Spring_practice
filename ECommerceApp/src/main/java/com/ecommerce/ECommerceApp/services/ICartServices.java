package com.ecommerce.ECommerceApp.services;

import com.ecommerce.ECommerceApp.Model.Cart;
import com.ecommerce.ECommerceApp.Model.User;

import java.math.BigDecimal;

public interface ICartServices {

    Cart getCart(Long cartId);
    void removeCart(Long cartId);
    BigDecimal getTotalPrice(Long cartId);

    Cart initializeNewCart(User user);

    Cart getCartByUserId(Long userId);
}
