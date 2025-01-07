package com.ecommerce.ECommerceApp.Controller;

import com.ecommerce.ECommerceApp.Exceptions.AlreadyExistException;
import com.ecommerce.ECommerceApp.Exceptions.ResourceNotFoundException;
import com.ecommerce.ECommerceApp.Model.User;
import com.ecommerce.ECommerceApp.PayLoad.CreateUserRequest;
import com.ecommerce.ECommerceApp.PayLoad.UpdateUserRequest;
import com.ecommerce.ECommerceApp.PayLoad.UserDto;
import com.ecommerce.ECommerceApp.response.ApiResponse;
import com.ecommerce.ECommerceApp.services.IUserService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {

    private final IUserService userService;

    @GetMapping("/{userId}/user")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId) {
        try {
            UserDto user = userService.getUserById(userId);
            return ResponseEntity.ok(new ApiResponse("success", user));
        } catch (ResourceNotFoundException e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Oops!", e.getMessage()));
        }
    }

    @PostMapping("/createUser")
    public ResponseEntity<ApiResponse> createUser(@NotNull @RequestBody  CreateUserRequest request) {
        System.out.println("controller user det"+request.getEmail());
        try {
            User user =  userService.createUser(request);
            UserDto userDto=userService.convertToDto(user);
            return ResponseEntity.ok(new ApiResponse("User Creation  Success!", userDto));
        } catch (AlreadyExistException e) {
            return  ResponseEntity.status(CONFLICT).body(new ApiResponse("Error Occurred!", e.getMessage()));
        }
    }

    @PutMapping("/updateUser")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UpdateUserRequest request,@RequestParam Long userId) {
        try {
            User user =  userService.updateUser(request,userId);
            UserDto userDto=userService.convertToDto(user);
            return ResponseEntity.ok(new ApiResponse("User Update  Success!", userDto));
        } catch (ResourceNotFoundException e) {
            return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse("User Not Found!", e.getMessage()));
        }

    }

    @DeleteMapping("/{userId}/userDelete")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId){
        try {
            userService.deleteUserById(userId);
            return ResponseEntity.ok(new ApiResponse("User deleteSuccess  Success!", userId));
        } catch (ResourceNotFoundException e) {
            return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse("User Not Found!", e.getMessage()));
        }


    }


}
