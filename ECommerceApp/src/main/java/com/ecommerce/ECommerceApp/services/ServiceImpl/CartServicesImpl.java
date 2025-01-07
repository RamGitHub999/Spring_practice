package com.ecommerce.ECommerceApp.services.ServiceImpl;

import com.ecommerce.ECommerceApp.Exceptions.ResourceNotFoundException;
import com.ecommerce.ECommerceApp.Model.Cart;
import com.ecommerce.ECommerceApp.Model.User;
import com.ecommerce.ECommerceApp.Repository.CartItemRepository;
import com.ecommerce.ECommerceApp.Repository.CartRepository;
import com.ecommerce.ECommerceApp.services.ICartServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class CartServicesImpl implements ICartServices {

    private final CartRepository cartRepo;
    private final CartItemRepository cartItemRepo;
    private final AtomicLong cartIdGenerator = new AtomicLong(0);

    @Override
    public Cart getCart(Long cartId) {
        Cart cart= cartRepo.findById(cartId).orElseThrow(()->new ResourceNotFoundException("Cart Not Found"));
        BigDecimal getTotalAmount=cart.getTotalAmount();
        cart.setTotalAmount(getTotalAmount);
        return cartRepo.save(cart);
    }

    @Override
    public void removeCart(Long cartId) {
       Cart cart =getCart(cartId);
       cartItemRepo.deleteAllByCartId(cartId);
       cart.getItems().clear();
       cartRepo.deleteById(cartId);
    }

    @Override
    public BigDecimal getTotalPrice(Long cartId) {
       Cart cart=getCart(cartId);
       return cart.getTotalAmount();
    }

    @Override
    public Cart initializeNewCart(User user) {
        try {
            return Optional.ofNullable(getCartByUserId(user.getId()))
                     .orElseGet(()->{
                         Cart cart=new Cart();
                         cart.setUser(user);
                         return cartRepo.save(cart);
                     });
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepo.findByUserId(userId);
    }
}
