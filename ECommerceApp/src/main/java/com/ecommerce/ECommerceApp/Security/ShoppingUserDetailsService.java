package com.ecommerce.ECommerceApp.Security;

import com.ecommerce.ECommerceApp.Model.User;
import com.ecommerce.ECommerceApp.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ShoppingUserDetailsService implements UserDetailsService {
     private final UserRepository repo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user= Optional.ofNullable(repo.findByEmail(username)).orElseThrow(()-> new UsernameNotFoundException("User Not Found!..."));
        return ShoppingUserDetails.buildUserDetails(user);
    }
}
