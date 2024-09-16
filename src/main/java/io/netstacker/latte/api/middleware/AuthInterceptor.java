package io.netstacker.latte.api.middleware;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import io.netstacker.latte.application.extensions.TokenManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull Object handler) throws Exception {
        var cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName() == "token") {
                Long userId = TokenManager.validateToken(cookie.getValue());
                request.setAttribute("userId", userId);
                return true;
            }
        }
        return false;
    }

    @Override
    public void postHandle(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull Object handler, 
    @Nullable ModelAndView modelAndView) throws Exception {}

    @Override
    public void afterCompletion(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull Object handler,
        @Nullable Exception ex)
            throws Exception {}
}
