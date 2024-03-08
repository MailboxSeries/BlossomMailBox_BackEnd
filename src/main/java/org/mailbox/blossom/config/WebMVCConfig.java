package org.mailbox.blossom.config;

import lombok.RequiredArgsConstructor;
import org.mailbox.blossom.intercepter.pre.UserIdArgumentResolver;
import org.mailbox.blossom.intercepter.pre.UserIdInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebMvc
@RequiredArgsConstructor
public class WebMVCConfig implements WebMvcConfigurer {
    private final UserIdArgumentResolver userIdArgumentResolver;
    private final UserIdInterceptor userIdInterceptor;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
        resolvers.add(this.userIdArgumentResolver);
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(this.userIdInterceptor)
                .addPathPatterns("/**");
//                .excludePathPatterns(Constants.EXCLUDE_PATHS);
    }
}
