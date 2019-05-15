package com.pjhu.medicine.user.domain.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum  Role {
    ADMIN("ADMIN"),
    USER("USER");

    private String name;

    @Override
    public String toString() {
        return this.name;
    }
}
