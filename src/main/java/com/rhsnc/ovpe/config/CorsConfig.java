package com.rhsnc.ovpe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        // Vue.js 개발 서버 포트 허용 (기본 8080)
        // config.addAllowedOrigin("http://localhost:8080");
        // config.addAllowedOrigin("http://localhost:9090");
        // config.addAllowedOrigin("http://localhost:3002");
        config.addAllowedOriginPattern("*");

        // 모든 HTTP 메서드 허용
        config.addAllowedMethod("*");
        
        // 모든 헤더 허용
        config.addAllowedHeader("*");
        
        // 인증 정보 허용
        config.setAllowCredentials(true);

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}

