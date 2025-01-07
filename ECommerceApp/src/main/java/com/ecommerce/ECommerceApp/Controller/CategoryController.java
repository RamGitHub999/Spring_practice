package com.ecommerce.ECommerceApp.Controller;

import com.ecommerce.ECommerceApp.Exceptions.AlreadyExistException;
import com.ecommerce.ECommerceApp.Exceptions.CategoryNotFoundException;
import com.ecommerce.ECommerceApp.Model.Category;
import com.ecommerce.ECommerceApp.PayLoad.CategoryDto;
import com.ecommerce.ECommerceApp.response.ApiResponse;
import com.ecommerce.ECommerceApp.services.ServiceImpl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryServiceImpl;

    @GetMapping("/allCategories")
    public ResponseEntity<ApiResponse> getAllCategories(){
        try {
            List<Category> categories=categoryServiceImpl.getAllCategories();
            return ResponseEntity.ok(new ApiResponse("Fonded Categories!",categories));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Retrieval Failed!",INTERNAL_SERVER_ERROR));
        }

    }

    @PostMapping("/addCategory")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category name)
    {
        try {
            Category category=categoryServiceImpl.createCategory(name);
            return ResponseEntity.ok(new ApiResponse("Created",category));
        } catch (AlreadyExistException e) {
            //throw new RuntimeException(e);
            return ResponseEntity.status(CONFLICT).
                    body(new ApiResponse(e.getMessage(),null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).
                    body(new ApiResponse(e.getMessage(),INTERNAL_SERVER_ERROR));
        }

    }

    @GetMapping("/getCategory/{id}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id)
    {
        try {
            Category category=categoryServiceImpl.getCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("category Founded!",category));
        } catch (CategoryNotFoundException e) {
            //throw new RuntimeException(e);
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),NOT_FOUND));
        }
        catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).
                    body(new ApiResponse(e.getMessage(),INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/getCategoryByName/{name}")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name)
    {
        try {
            List<Category> category=categoryServiceImpl.getCategoryByName(name);
            List<CategoryDto> categoryDto=categoryServiceImpl.categoryDtoList(category);
            return ResponseEntity.ok(new ApiResponse("category Founded!",category));
        } catch (CategoryNotFoundException e) {
            //throw new RuntimeException(e);
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),NOT_FOUND));
        }
        catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).
                    body(new ApiResponse(e.getMessage(),INTERNAL_SERVER_ERROR));
        }
    }


    @DeleteMapping("/deleteCategory/{id}")
    public ResponseEntity<ApiResponse> deleteCategoryById(@PathVariable Long id)
    {
        try {
           categoryServiceImpl.deleteCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("category Founded!",null));
        } catch (CategoryNotFoundException e) {
            //throw new RuntimeException(e);
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),NOT_FOUND));
        }
        catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).
                    body(new ApiResponse(e.getMessage(),INTERNAL_SERVER_ERROR));
        }
    }




}
