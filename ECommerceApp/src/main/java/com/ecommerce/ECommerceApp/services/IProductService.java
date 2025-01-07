package com.ecommerce.ECommerceApp.services;

import com.ecommerce.ECommerceApp.Model.Category;
import com.ecommerce.ECommerceApp.Model.Product;
import com.ecommerce.ECommerceApp.PayLoad.ProductDto;

import java.util.List;

public interface IProductService {

    Product createProduct(ProductDto product);
    Product getProductById(Long productId);
    List<Product> getAllProducts();
    Product updateProduct(ProductDto product, Long productId);
    void deleteProduct(Long productId);
    List<Product> productsByCategory(String category);
    List<Product> productsByCategoryAndBrand(String category, String brand);
    List<Product> getProductByBrand(String brand);
    List<Product> getProductByName(String name);
    List<Product> getProductByBrandAndName(String brand ,String name);
    Long countProductsByBrandAndName(String brand,String name);

    List<ProductDto> productDtoList(List<Product> products);

    ProductDto convertToDto(Product product);
}
