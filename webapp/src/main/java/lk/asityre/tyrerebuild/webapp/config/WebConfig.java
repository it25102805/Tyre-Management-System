package lk.asityre.tyrerebuild.webapp.config;
import org.springframework.context.annotation.Configuration;
import lk.asityre.tyrerebuild.webapp.security.AccessInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AccessInterceptor accessInterceptor;

    public WebConfig(AccessInterceptor accessInterceptor) {
        this.accessInterceptor = accessInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accessInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/CSS/**", "/js/**", "/images/**", "/logo.png", "/favicon.ico", "/error");
    }
}