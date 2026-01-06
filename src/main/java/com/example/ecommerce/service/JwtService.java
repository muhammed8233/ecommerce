package com.example.ecommerce.service;

import com.example.ecommerce.user.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    String generateToken(UserDetails userDetails);
}
