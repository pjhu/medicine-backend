package com.pjhu.medicine.identity.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminSignInResponse {
    private String token;
    private String username;
    private String role;
}
