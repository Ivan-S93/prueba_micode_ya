package zzz.com.micodeya.backend.core.util.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${server.servlet.context-path}")
    private String servletContextPath;

    @Bean
    public FilterRegistrationBean jwtFilter() {
        
        FilterRegistrationBean filter= new FilterRegistrationBean();
        filter.setFilter(new KuaaJwtFilter(secret, servletContextPath));
        
        // provide endpoints which needs to be restricted.
        // All Endpoints would be restricted if unspecified
    //    filter.addUrlPatterns("/api/v1/blog/restricted");
    //    filter.addUrlPatterns("/api/v2/auth/verificarPermiso");
        
        filter.addUrlPatterns("/*"); // Agrega los patrones de URL que deseas filtrar
        // filter.addInitParameter("excludeUrl", "/mi-url-a-omitir"); // Agrega la URL que deseas omitir
        

       
       return filter;

    }
}
