package com.ecommerce.ECommerceApp.services;

import com.ecommerce.ECommerceApp.Model.User;
import com.ecommerce.ECommerceApp.PayLoad.CreateUserRequest;
import com.ecommerce.ECommerceApp.PayLoad.UpdateUserRequest;
import com.ecommerce.ECommerceApp.PayLoad.UserDto;

public interface IUserService {
    UserDto getUserById(Long id);
    User createUser(CreateUserRequest request);
    User updateUser(UpdateUserRequest request, Long userId);
    void deleteUserById(Long userId);

    //UserDto convertToDto(Long userId);

    UserDto convertToDto(User user);

    User getAuthenticatedUser();
}
