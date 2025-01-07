package com.ecommerce.ECommerceApp.PayLoad;

import com.ecommerce.ECommerceApp.Model.Category;
import com.ecommerce.ECommerceApp.Model.Image;
import lombok.Data;
import lombok.Setter;


import java.math.BigDecimal;
import java.util.List;

@Data
@Setter
public class ProductDto {

    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;

    private List<ImageDto> images;
}
