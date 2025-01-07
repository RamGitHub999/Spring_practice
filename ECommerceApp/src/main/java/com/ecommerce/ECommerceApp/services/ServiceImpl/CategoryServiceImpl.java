package com.ecommerce.ECommerceApp.services.ServiceImpl;

import com.ecommerce.ECommerceApp.Exceptions.AlreadyExistException;
import com.ecommerce.ECommerceApp.Exceptions.CategoryNotFoundException;
import com.ecommerce.ECommerceApp.Model.Category;
import com.ecommerce.ECommerceApp.PayLoad.CategoryDto;
import com.ecommerce.ECommerceApp.Repository.CategoryRepository;
import com.ecommerce.ECommerceApp.services.ICategoryServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements ICategoryServices {
    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Category getCategoryById(Long id) {
       return categoryRepo.findById(id).
               orElseThrow(()->new CategoryNotFoundException("Category Not Found"));

    }

    @Override
    public List<Category> getCategoryByName(String name) {

                List<Category> categoryList=categoryRepo.getCategoryByName(name);
                return categoryList;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }



    @Override
    public Category createCategory(Category categoryDto) {
        return Optional.of(categoryDto).filter(c-> !categoryRepo.existsByName(categoryDto.getName()))
                .map(categoryRepo::save)
                .orElseThrow(()->new AlreadyExistException(categoryDto.getName()+"Already Exist!."));
    }

    @Override
    public Category updateCategory(Category categoryDto,Long id) {
        return Optional.ofNullable(getCategoryById(id))
                .map(existingRecord->{
                    existingRecord.setName(categoryDto.getName());
                    return categoryRepo.save(existingRecord);
                }).orElseThrow(()->new CategoryNotFoundException("Category Not Found"));
    }

    @Override
    public void deleteCategoryById(Long Id) {
        categoryRepo.findById(Id).ifPresentOrElse(categoryRepo :: delete ,
                ()->{throw new CategoryNotFoundException("Category Not Found");
        });
    }

    @Override
    public List<CategoryDto> categoryDtoList(List<Category> products){
        return products.stream().map(this::convertToDto).toList();
    }

    @Override
    public CategoryDto convertToDto(Category category){
        CategoryDto categoryDto=modelMapper.map(category,CategoryDto.class);
        return categoryDto;
    }


}
