package com.pjhu.medicine.infrastructure.common;

import java.util.UUID;

public class UuidHelper {

    private UuidHelper() {
        throw new IllegalStateException("Utility class");
    }

    public static String uuid32() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
