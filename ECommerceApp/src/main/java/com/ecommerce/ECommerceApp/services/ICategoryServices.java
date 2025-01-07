package com.ecommerce.ECommerceApp.services;

import com.ecommerce.ECommerceApp.Model.Category;
import com.ecommerce.ECommerceApp.PayLoad.CategoryDto;

import java.util.List;

public interface ICategoryServices {

    Category getCategoryById(Long id);
    List<Category> getCategoryByName(String name);
    List<Category> getAllCategories();
    Category createCategory(Category categoryDto);
    Category updateCategory(Category categoryDto,Long id);
    void deleteCategoryById(Long Id);

    List<CategoryDto> categoryDtoList(List<Category> products);

    CategoryDto convertToDto(Category category);
}
