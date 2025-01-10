package io.netstacker.latte.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.netstacker.latte.api.middleware.AuthInterceptor;
import io.netstacker.latte.domain.services.TokenService;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        TokenService tokenService = new TokenService();
        registry.addInterceptor(new AuthInterceptor(tokenService))
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/accounts/login")
                .excludePathPatterns("/api/accounts/register");
    }
}
