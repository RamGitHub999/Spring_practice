package com.ecommerce.ECommerceApp.Security;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {
    private JwtUtils jwtUtils;
    private ShoppingUserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {

        try {
            String jwt=parseJwt(request);
            if(StringUtils.hasText(jwt) && jwtUtils.validateToken(jwt))
            {
                String username=jwtUtils.getUsernameFromToken(jwt);
                UserDetails userDetails= userDetailsService.loadUserByUsername(username);
                Authentication auth= new UsernamePasswordAuthenticationToken(userDetails,userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (JwtException e) {
            //throw new RuntimeException(e);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(e.getMessage()+"the logged in Token is expired you may login again ");
            return;
        }catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(e.getMessage());
            return;
        }
        filterChain.doFilter(request,response);

    }

    private String parseJwt(HttpServletRequest request){
        String headerAuth=request.getHeader("Authorization");
        if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer "))
        {
            return headerAuth.substring(7);
        }
        return null;
    }
}
