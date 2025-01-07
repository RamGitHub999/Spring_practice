package com.ecommerce.ECommerceApp.services;

import com.ecommerce.ECommerceApp.Model.CartItem;

public interface ICartItemServices {
    void addCartItem(Long cartId,Long productId,int quantity);
    void deleteCartItemFromCart(Long cartId,Long productId);

    CartItem getCartItem(Long cartId, Long productId);

    void updateCartItemQuantity(Long cartId, Long productId, int quantity);
}
