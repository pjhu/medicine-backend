package com.pjhu.medicine.identity.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TokenType {
    ADMIN("Bearer"),
    USER("App");

    private String prefix;
}
