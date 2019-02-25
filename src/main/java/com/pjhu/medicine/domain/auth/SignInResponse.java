package com.pjhu.medicine.domain.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SignInResponse {
    private String token;
    private String username;
    private String role;
}
