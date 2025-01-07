package com.ecommerce.ECommerceApp.services.ServiceImpl;

import com.ecommerce.ECommerceApp.Exceptions.AlreadyExistException;
import com.ecommerce.ECommerceApp.Exceptions.ResourceNotFoundException;
import com.ecommerce.ECommerceApp.Model.User;
import com.ecommerce.ECommerceApp.PayLoad.CreateUserRequest;
import com.ecommerce.ECommerceApp.PayLoad.UpdateUserRequest;
import com.ecommerce.ECommerceApp.PayLoad.UserDto;
import com.ecommerce.ECommerceApp.Repository.UserRepository;
import com.ecommerce.ECommerceApp.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepo;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto getUserById(Long id) {
        return userRepo.findById(id)
                .map( this :: convertToDto)
                .orElseThrow(()->new ResourceNotFoundException("User Not Found!.."));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        //System.out.println("new User Service Impl"+request.getLastName());
        return Optional.of(request).
                filter(user-> !userRepo.existsByEmail(request.getEmail()))
                .map(req->{
                    User usr=new User();
                    usr.setFirstName(request.getFirstName());
                    usr.setLastName(request.getLastName());
                    usr.setEmail(request.getEmail());
                    usr.setPassword(passwordEncoder.encode(request.getPassword()));
                    //System.out.println("usr obj after set"+usr.getLastName());
                    return userRepo.save(usr);
                }).orElseThrow(()-> new AlreadyExistException("Oops! " +request.getEmail() +" Already Exist"));
    }

    @Override
    public User updateUser(UpdateUserRequest request, Long userId) {
        return userRepo.findById(userId).map(existingUser->{
            existingUser.setFirstName(request.getFirstName());
            existingUser.setLastName(request.getLastName());
            return userRepo.save(existingUser);
        }).orElseThrow(()->new ResourceNotFoundException("User Not Found"));
    }

    @Override
    public void deleteUserById(Long userId) {

        userRepo.findById(userId).ifPresentOrElse(userRepo::delete,()->{
            throw new ResourceNotFoundException("User Not Found");
        });

    }

    @Override
    public UserDto convertToDto(User user){
        return modelMapper.map(user,UserDto.class);
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String email=authentication.getName();
        return userRepo.findByEmail(email);
    }
}
