package com.pjhu.medicine.identity.handler;

import com.pjhu.medicine.common.cache.ExternalUserMeta;
import com.pjhu.medicine.common.cache.RedisNamespace;
import com.pjhu.medicine.identity.domain.model.ExternalUserTokenRepository;
import com.pjhu.medicine.common.utils.SuppressObjectMapper;
import com.pjhu.medicine.identity.domain.model.Role;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ExternalUserLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final ExternalUserTokenRepository externalUserTokenRepository;
    private final SuppressObjectMapper suppressObjectMapper;

    public ExternalUserLoginSuccessHandler(ExternalUserTokenRepository externalUserTokenRepository,
                                           SuppressObjectMapper suppressObjectMapper) {
        this.externalUserTokenRepository = externalUserTokenRepository;
        this.suppressObjectMapper = suppressObjectMapper;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        ExternalUserMeta externalUserMeta = new ExternalUserMeta(authentication.getName(), Role.USER.name());
        String token = externalUserTokenRepository.create(RedisNamespace.USER_NAME_SPACE, externalUserMeta);
        ExternalSignInResponse adminSignInResponse = new ExternalSignInResponse(token);
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        PrintWriter printWriter = response.getWriter();
        printWriter.write(suppressObjectMapper.writeValueAsString(adminSignInResponse));
        printWriter.flush();
    }
}
