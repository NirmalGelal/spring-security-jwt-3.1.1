package com.nirmal.demo.controller;

import com.nirmal.demo.config.JwtService;
import com.nirmal.demo.model.User;
import com.nirmal.demo.model.request.AuthenticationRequest;
import com.nirmal.demo.model.response.AuthenticationResponse;
import com.nirmal.demo.model.response.Response;
import com.nirmal.demo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class AuthApi {

    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/signin")
    public Response<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest data, HttpServletRequest request) throws Exception {
        Response<AuthenticationResponse> response = new Response<>();
        UserDetails user = userService.authenticateUser(data);
        if (user == null) {
            throw new Exception("User not found");
        }
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .username(data.getUsername())
                .build();
        response.setSuccessValues(authenticationResponse,"tokens generated successfully");
        return response;
    }

    @PostMapping("/user")
    public Response<String> addUser(@RequestBody User user) {
        Response<String> response = new Response<>();
        String message = userService.saveUser(user);
        if (message.equals("success")) {
            response.setSuccessValues("","user added successfully");
            return response;
        }
        response.setFailureValues("user registration failed");
        return response;
    }

    @GetMapping("/test")
    public Response<String> test() {
        Response<String> response = new Response<>();
        response.setSuccessValues("","test works!");
        return response;
    }

    @PostMapping("/refresh-token")
    public Response<AuthenticationResponse> refreshToken(
            HttpServletRequest req,
            HttpServletResponse res
    ) throws IOException {
        Response<AuthenticationResponse> response = new Response<>();
        AuthenticationResponse authenticationResponse = jwtService.refreshToken(req, res);
        response.setSuccessValues(authenticationResponse,"access token revived successfully");
        return response;
    }
}
