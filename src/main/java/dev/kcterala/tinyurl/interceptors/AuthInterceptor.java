package dev.kcterala.tinyurl.interceptors;

import dev.kcterala.tinyurl.exceptions.InvalidTokenException;
import dev.kcterala.tinyurl.session.SessionTokenManager;
import dev.kcterala.tinyurl.session.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    private final SessionTokenManager sessionTokenManager;

    public AuthInterceptor(final SessionTokenManager sessionTokenManager) {
        this.sessionTokenManager = sessionTokenManager;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        if (handler instanceof HandlerMethod handlerMethod) {
            Method method = handlerMethod.getMethod();
            if (method.isAnnotationPresent(Unauthenticated.class)) {
                return true;
            }

            String authHeader = request.getHeader("Authorization");
            if (authHeader == null) {
                throw new InvalidTokenException();
            }

            String sessionToken = authHeader.substring(7);
            UserInfo userInfo = sessionTokenManager.getUserInfo(sessionToken);
            request.setAttribute("user", userInfo);
        }
        return true;

    }
}

