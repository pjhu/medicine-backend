package com.pjhu.medicine.domain.auth;

import com.pjhu.medicine.domain.operator.Operator;
import com.pjhu.medicine.domain.operator.OperatorManager;
import com.pjhu.medicine.infrastructure.cache.OperatorMeta;
import com.pjhu.medicine.infrastructure.security.AuthenticationHelper;
import com.pjhu.medicine.infrastructure.security.TokenType;
import com.pjhu.medicine.infrastructure.security.TokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthManager {

    private final OperatorManager operatorManager;
    private final UserTokenRepository userTokenRepository;

    public SignInResponse signIn(SignInRequest signInRequest) {
        Operator operator = operatorManager.findOperatorBy(signInRequest.getUsername());
        if (null == operator) {
            return null;
        }
        String token = userTokenRepository.create(operator);
        Authentication authentication = AuthenticationHelper.create(operator.getUsername(), operator.getRoleAsText());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return SignInResponse.builder()
                .username(operator.getUsername())
                .role(operator.getRoleAsText())
                .token(token)
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void signOut(String token) {
        SecurityContextHolder.clearContext();
        String tokenUuid = TokenUtil.tokenExtract(token, TokenType.ADMIN);
        OperatorMeta operatorMeta = userTokenRepository.getBy(tokenUuid);
        if (null != operatorMeta) {
            userTokenRepository.delete(tokenUuid);
        }
    }
}
