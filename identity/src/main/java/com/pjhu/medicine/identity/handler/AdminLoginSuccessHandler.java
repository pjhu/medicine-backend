package com.pjhu.medicine.identity.handler;

import com.pjhu.medicine.identity.domain.model.AdminTokenRepository;
import com.pjhu.medicine.common.cache.RedisNamespace;
import com.pjhu.medicine.common.cache.AdminUserMeta;
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

public class AdminLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final AdminTokenRepository adminTokenRepository;
    private final SuppressObjectMapper suppressObjectMapper;

    public AdminLoginSuccessHandler(AdminTokenRepository adminTokenRepository,
                                    SuppressObjectMapper suppressObjectMapper) {
        this.adminTokenRepository = adminTokenRepository;
        this.suppressObjectMapper = suppressObjectMapper;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst().orElse(Strings.EMPTY);
        AdminUserMeta adminUserMeta = new AdminUserMeta(authentication.getName(), role);
        String token = adminTokenRepository.create(RedisNamespace.ADMIN_NAME_SPACE, adminUserMeta);
        AdminSignInResponse adminSignInResponse = new AdminSignInResponse(token, authentication.getName(), role);
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        PrintWriter printWriter = response.getWriter();
        printWriter.write(suppressObjectMapper.writeValueAsString(adminSignInResponse));
        printWriter.flush();
    }
}
