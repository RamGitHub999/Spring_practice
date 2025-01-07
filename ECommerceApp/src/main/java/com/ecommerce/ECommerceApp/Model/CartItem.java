package com.ecommerce.ECommerceApp.Model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;
    private int quantity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="cart_id")
    private Cart cart;

    private BigDecimal unitPrice;
    private BigDecimal totalPrice;

    public void setTotalPrice()
    {
        this.totalPrice=this.unitPrice.multiply(new BigDecimal(quantity));
    }

}
