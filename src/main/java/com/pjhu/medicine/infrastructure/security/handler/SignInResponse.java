package com.pjhu.medicine.infrastructure.security.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignInResponse {
    private String token;
    private String username;
    private String role;
}
