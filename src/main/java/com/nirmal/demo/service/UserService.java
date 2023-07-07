package com.nirmal.demo.service;

import com.nirmal.demo.config.JwtService;
import com.nirmal.demo.model.User;
import com.nirmal.demo.model.UserRepository;
import com.nirmal.demo.model.request.AuthenticationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtService jwtService;

    public String saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "success";
    }

    public UserDetails authenticateUser(AuthenticationRequest data) {
        UserDetails user = userDetailsService.loadUserByUsername(data.getUsername());
//        if (checkPassword(data, user)) {
//            return jwtService.generateToken(user);
//        }
//        return null;
        if(checkPassword(data,user)){
            return user;
        }
        return null;
    }



    public Boolean checkPassword(AuthenticationRequest data, UserDetails user) {
        return (user != null && passwordEncoder.matches(data.getPassword(), user.getPassword()));
    }
}
