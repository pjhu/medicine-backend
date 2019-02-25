package com.pjhu.medicine.infrastructure.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TokenType {
    ADMIN("Bearer"),
    APP("App");

    private String prefix;
}
