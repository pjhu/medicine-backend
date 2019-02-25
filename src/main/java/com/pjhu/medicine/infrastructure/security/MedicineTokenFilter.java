package com.pjhu.medicine.infrastructure.security;

import com.pjhu.medicine.domain.auth.UserTokenRepository;
import com.pjhu.medicine.infrastructure.cache.OperatorMeta;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class MedicineTokenFilter implements Filter {

    private final UserTokenRepository userTokenRepository;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String requestHeader = request.getHeader("Authorization");
        if (StringUtils.isBlank(requestHeader)) {
            chain.doFilter(request, response);
        }

        String tokenUuid = TokenUtil.tokenExtract(requestHeader, TokenType.ADMIN);
        OperatorMeta operatorMeta = userTokenRepository.getBy(tokenUuid);

        if (operatorMeta == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }
        userTokenRepository.refresh(tokenUuid);
        Authentication authentication = AuthenticationHelper.create(operatorMeta.getUsername(), operatorMeta.getRole());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {
        log.info("medicine token destroy");
    }
}
