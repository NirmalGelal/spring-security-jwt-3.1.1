package com.nirmal.demo.model.response;

import lombok.*;

@Data
@Builder
public class AuthenticationResponse {
    private String username;
    private String accessToken;
    private String refreshToken;
}
