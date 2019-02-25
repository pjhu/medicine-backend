package com.pjhu.medicine.domain.auth;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

@Getter
@Setter
public class SignInRequest {

    private String username;
    private String password;
}
