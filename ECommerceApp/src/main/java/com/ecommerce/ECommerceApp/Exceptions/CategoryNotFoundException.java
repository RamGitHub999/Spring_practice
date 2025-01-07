package com.ecommerce.ECommerceApp.Exceptions;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String categoryNotFound) {
        super(categoryNotFound);
    }
}
