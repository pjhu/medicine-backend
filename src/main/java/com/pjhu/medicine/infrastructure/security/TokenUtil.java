package com.pjhu.medicine.infrastructure.security;

import org.apache.commons.lang3.StringUtils;

public class TokenUtil {

    private TokenUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String tokenExtract(String header, TokenType tokenType) {
        if (StringUtils.isBlank(header)) {
            return StringUtils.EMPTY;
        }

        return StringUtils.substringAfter(header, tokenType.getPrefix()).trim();
    }

}
