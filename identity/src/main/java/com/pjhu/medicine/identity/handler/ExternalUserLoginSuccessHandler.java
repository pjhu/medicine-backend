package com.pjhu.medicine.identity.handler;

import com.pjhu.medicine.common.cache.RedisNamespace;
import com.pjhu.medicine.identity.domain.model.UserTokenRepository;
import com.pjhu.medicine.common.cache.UserMeta;
import com.pjhu.medicine.common.utils.SuppressObjectMapper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ExternalUserLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserTokenRepository userTokenRepository;
    private final SuppressObjectMapper suppressObjectMapper;

    public ExternalUserLoginSuccessHandler(UserTokenRepository userTokenRepository,
                                           SuppressObjectMapper suppressObjectMapper) {
        this.userTokenRepository = userTokenRepository;
        this.suppressObjectMapper = suppressObjectMapper;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst().orElse(Strings.EMPTY);
        UserMeta userMeta = new UserMeta(authentication.getName(), role);
        String token = userTokenRepository.create(RedisNamespace.USER_NAME_SPACE, userMeta);
        SignInResponse signInResponse = new SignInResponse(token, authentication.getName(), role);
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        PrintWriter printWriter = response.getWriter();
        printWriter.write(suppressObjectMapper.writeValueAsString(signInResponse));
        printWriter.flush();
    }
}
