package org.mailbox.blossom.security.handler.signout;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONValue;
import org.mailbox.blossom.dto.type.ErrorCode;
import org.mailbox.blossom.security.handler.common.AbstractAuthenticationFailureHandler;
import org.mailbox.blossom.utility.CookieUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomSignOutResultHandler extends AbstractAuthenticationFailureHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        if (authentication == null) {
            setErrorResponse(response, ErrorCode.NOT_FOUND_USER);
            return;
        }

        CookieUtil.deleteCookie(request, response, "accessToken");
        CookieUtil.deleteCookie(request, response, "refreshToken");
        CookieUtil.deleteCookie(request, response, "JSESSIONID");

        setSuccessResponse(response);
    }

    private void setSuccessResponse(HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.OK.value());

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("data", null);
        result.put("error", null);

        response.getWriter().write(JSONValue.toJSONString(result));
    }
}
