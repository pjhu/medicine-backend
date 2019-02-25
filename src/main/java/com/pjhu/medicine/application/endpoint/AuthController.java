package com.pjhu.medicine.application.endpoint;

import com.pjhu.medicine.domain.auth.SignInRequest;
import com.pjhu.medicine.domain.auth.AuthManager;
import com.pjhu.medicine.domain.auth.SignInResponse;
import com.pjhu.medicine.infrastructure.common.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;

@RestController
@RequestMapping(value = Constant.ROOT + "/auth", produces = APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class AuthController {

    private final AuthManager authManager;

    @PostMapping("/signIn")
    public ResponseEntity<Object> signIn(@RequestBody SignInRequest signInRequest) {
        SignInResponse signInResponse = authManager.signIn(signInRequest);
        if (null == signInResponse) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("user not exists in local");
        }
        return ResponseEntity.ok(signInResponse);
    }

    @RequestMapping(value = "/signOut", method = DELETE)
    public void signOut(@RequestHeader(value = "Authorization") String tokenId) {
        authManager.signOut(tokenId);
        ResponseEntity.status(HttpStatus.ACCEPTED).body("Sign out");
    }
}
