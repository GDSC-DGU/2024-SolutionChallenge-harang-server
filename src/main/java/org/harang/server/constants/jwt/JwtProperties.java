package org.harang.server.constants.jwt;

public class JwtProperties {
    public static final String MEMBER_ID_CLAIM_NAME = "mid";
    public static final String MEMBER_ROLE_CLAIM_NAME = "mtype";
    public static final String BEARER = "Bearer ";

    public static final String[] AUTH_WHITELIST = {
            "/v1/auth/login",
            "/actuator/health",
            "/test/**",
    };
}
