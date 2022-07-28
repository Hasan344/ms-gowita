package com.gowita.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AuthRole {
    public static final String ROLE_USER = "hasRole('ROLE_USER')";
    public static final String ROLE_ADMIN = "hasRole('ROLE_ADMIN')";
}
