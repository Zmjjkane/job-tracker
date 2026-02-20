package com.zmjjkane.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Global CORS (Cross-Origin Resource Sharing) configuration for REST APIs.
 *
 * What CORS means:
 * CORS is a browser security mechanism that controls whether a web page loaded
 * from one origin (scheme + host + port) can access resources from another origin
 * via JavaScript (e.g., fetch, XMLHttpRequest).
 *
 * Browsers enforce the Same-Origin Policy by default. If a frontend application
 * running at http://localhost:5173 attempts to call a backend API at
 * http://localhost:8080, the origins differ by port and the request is considered
 * cross-origin. The browser will block access unless the server explicitly allows
 * it via CORS response headers (e.g., Access-Control-Allow-Origin).
 *
 * Why CORS is needed in development:
 * In production deployments, frontend and backend are typically served under the
 * same origin (e.g., https://app.company.com and https://app.company.com/api)
 * through a reverse proxy such as Nginx. Because the browser sees the same origin,
 * no cross-origin restriction applies.
 *
 * During local development, however, frontend and backend usually run on different
 * ports (e.g., Vite dev server at 5173 and Spring Boot at 8080). This creates a
 * cross-origin scenario that requires CORS configuration.
 *
 * How this config works in Spring MVC:
 * - @Configuration marks this class as a Spring configuration component.
 * - The @Bean method registers a WebMvcConfigurer bean into the Spring container.
 * - WebMvcConfigurer is a Spring MVC extension interface. Multiple configurer
 *   beans may exist in an application, each contributing different MVC settings
 *   (e.g., CORS, interceptors, message converters, argument resolvers).
 * - During MVC initialization, Spring collects all WebMvcConfigurer beans and
 *   invokes their callback methods.
 * - Spring MVC internally creates a CorsRegistry instance and passes it into
 *   addCorsMappings(CorsRegistry registry).
 * - Inside addCorsMappings, we register path-based CORS rules (e.g., /api/**).
 * - Spring converts these mappings into internal CORS configuration and applies
 *   them to both preflight (OPTIONS) and actual requests by adding the appropriate
 *   CORS response headers.
 *
 * Notes:
 * - http://localhost:5173 is the default Vite dev server origin for React + Vite.
 *   Change it if your frontend runs on a different port.
 * - Only /api/** endpoints are exposed to cross-origin access in development.
 */

@Configuration
public class CorsConfig {

    // Registers a WebMvcConfigurer bean.
    // Spring MVC will call addCorsMappings(...) during MVC initialization,
    // providing a CorsRegistry created by the framework, and the rules are used
    // for both preflight (OPTIONS) and actual requests under /api/**.
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:5173")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
