package com.pjhu.medicine.domain.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthManager {

    private final UserTokenRepository userTokenRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public void signOut(String token) {
//        SecurityContextHolder.clearContext();
//        String tokenUuid = AuthenticationUtil.tokenExtract(token, TokenType.ADMIN);
//        UserMeta operatorMeta = userTokenRepository.getBy(tokenUuid);
//        if (null != operatorMeta) {
//            userTokenRepository.delete(tokenUuid);
//        }
    }
}
