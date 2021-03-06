package com.pjhu.medicine.common.utils;

public class Constant {

    private static final String VERSION = "v1";
    public static final String ROOT = "/api/" + VERSION;
    public static final String ADMIN = ROOT + "/admin";
    public static final String USER = ROOT + "/external-user";
    public static final String USER_SIGN_IN = ROOT + "/auth/external-user/signin";
    public static final String USER_SIGN_OUT = ROOT + "/auth/external-user/signout";
    public static final String ADMIN_SIGN_IN = ROOT + "/auth/admin/signin";
    public static final String ADMIN_SIGN_OUT = ROOT + "/auth/admin/signout";
}
