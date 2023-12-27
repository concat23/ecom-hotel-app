package dev.concat.vab.ecomhotelappbackend.constant;

import java.util.Date;

public class SecurityConstant {
    //public static final long EXPIRATION_TIME = 20000;
    public static final long EXPIRATION_TIME = 432_000_000; // 5 days expressed in milliseconds
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String JWT_TOKEN_HEADER = "Jwt-Token";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token Cannot be verified";
    public static final String GET_ARRAYS_LLC = "Get Arrays, LLC.";
    public static final String GET_ARRAYS_ADMINISTRATION = "Ecom Hotel Management Portal";
    public static final String AUTHORITIES = "authorities";
    public static final String FORBIDDEN_MESSAGE = "You need to log in to access this page";
    public static final String ACCESS_DENIED_MESSAGE = "You do not have permission to access this page";
    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";

    public static final long ACCESS_TOKEN_EXPIRATION_TIME =  10*60*1000;
    public static final long REFRESH_TOKEN_EXPIRATION_TIME =  30*60*1000;
    public static final String[] PUBLIC_URLS = {
            "/api/auth/**",
            "/api/welcome/**",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/v2/api-docs",
            "/webjars/**",
            "/swagger-ui/index.html"};

}
