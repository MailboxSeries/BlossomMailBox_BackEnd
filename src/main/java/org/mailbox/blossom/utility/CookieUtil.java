package org.mailbox.blossom.utility;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Arrays;
import java.util.Optional;

public class CookieUtil {

    public static Optional<String> refineCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();

        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(name))
                .findFirst().map(Cookie::getValue);
    }

    public static void addCookie(HttpServletResponse response, String cookieDomain, String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setDomain(cookieDomain);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static void addSecureCookie(HttpServletResponse response, String cookieDomain, String name, String value, Integer maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setDomain(cookieDomain);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String cookieDomain, String name) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            return;
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                Cookie removedCookie = new Cookie(name, null);
                if (cookieDomain != null) {
                    removedCookie.setDomain(cookieDomain);
                }
                removedCookie.setPath("/");
                removedCookie.setMaxAge(0);
                removedCookie.setHttpOnly(true);
                removedCookie.setSecure(true);

                response.addCookie(removedCookie);
            }
        }
    }
}
