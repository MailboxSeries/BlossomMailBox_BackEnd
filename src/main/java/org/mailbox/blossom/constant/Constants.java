package org.mailbox.blossom.constant;

import java.time.LocalDate;
import java.util.List;

public class Constants {
    public static String USER_ID_ATTRIBUTE_NAME = "USER_ID";
    public static String USER_ID_CLAIM_NAME = "uid";
    public static String USER_ROLE_CLAIM_NAME = "rol";
    public static String BEARER_PREFIX = "Bearer ";
    public static String AUTHORIZATION_HEADER = "Authorization";
    public static String ACCESS_TOKEN = "accessToken";
    public static String REFRESH_TOKEN = "refreshToken";
    public static String HAIR_SKIN = "hair";
    public static String FACE_SKIN = "face";
    public static String TOP_SKIN = "top";
    public static String BOTTOM_SKIN = "bottom";
    public static String ANIMAL_SKIN = "cat";
    public static String RIGHT_STORE_SKIN = "rightStore";
    public static String LEFT_STORE_SKIN = "leftStore";
    public static String LETTER_DISABLE_STATUS = "disable";
    public static String LETTER_ACTIVE_STATUS = "active";
    public static String LETTER_INACTIVE_STATUS = "inactive";

    public static LocalDate START_DATE = LocalDate.of(2024, 3, 6);

    public static List<String> NO_NEED_AUTH_URLS = List.of(
            "/api/v1/auth/reissue",
            "/api/v1/users",
            "/oauth2/authorization/naver",
            "/oauth2/authorization/kakao",
            "/oauth2/authorization/google",
            "/login/oauth2/code/naver",
            "/login/oauth2/code/kakao",
            "/login/oauth2/code/google",

            "/api-docs.html",
            "/api-docs/**",
            "/swagger-ui/**");

    public static List<String> USER_URLS = List.of(
            "/**");

    public static List<String> ADMIN_URLS = List.of(
            "/admin/**");
}
