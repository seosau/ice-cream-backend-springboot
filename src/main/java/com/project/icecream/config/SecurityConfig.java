package com.project.icecream.config;

import com.project.icecream.utils.JwtTokenUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.crypto.spec.SecretKeySpec;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private final String[] PUBLIC_ENDPOINTS = {
            "/login",
            "/register",
            "/admin/register",
            "/admin/login",
            "/seller/register",
            "/seller/login",
            "/",
            "/product/add",
    };

    private final String[] CLIENT_ENDPOINTS = {
            "/quantityCartItems",
            "/getlogout",
    };
    private final String[] SELLER_ENDPOINTS = {
            "/seller/logout",
    };
    private final String[] ADMIN_ENDPOINTS = {
//            "/",
            "/admin/logout",
    };

//    @Bean
//    @Order(Ordered.HIGHEST_PRECEDENCE)
//    SecurityFilterChain unsecuredFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .securityMatcher(new OrRequestMatcher(new AntPathRequestMatcher("/login")))
//                .securityMatcher(new OrRequestMatcher(new AntPathRequestMatcher("/register"))) // Bị lỗi chỉ nhận cái dưới
//                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
//                    authorizationManagerRequestMatcherRegistry.requestMatchers("/**").permitAll();
//                })
//                .build();
//    }

    // Cấu hình filterchian cho các public endpoints, đặt oder cao nhất
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    SecurityFilterChain unsecuredFilterChain(HttpSecurity httpSecurity) throws Exception {
        List<RequestMatcher> matchers = Arrays.stream(PUBLIC_ENDPOINTS)
                .map(AntPathRequestMatcher::new)
                .collect(Collectors.toList());

        return httpSecurity
                .securityMatcher(new OrRequestMatcher(matchers))
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
                    authorizationManagerRequestMatcherRegistry.requestMatchers("/**").permitAll();
                })
                .build();
    }


    // Cấu hình filterchian cho các authenticate endpoints, đặt oder cao nhì
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE + 1)
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.cors(cors -> cors.configurationSource(corsConfigurationSource()));
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.oauth2ResourceServer(oauth2 ->
                oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder())
                        .jwtAuthenticationConverter(jwtAuthenticationConverter()))
        );
        httpSecurity.authorizeHttpRequests(request ->
                request
//                        .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                        .requestMatchers(CLIENT_ENDPOINTS).hasRole("client")
                        .requestMatchers(SELLER_ENDPOINTS).hasRole("seller")
                        .requestMatchers(ADMIN_ENDPOINTS).hasRole("admin")
                        .anyRequest().authenticated());
        return httpSecurity.build();
    }

    // Cấu hình AuthoritiesClaimName và setAuthorityPrefix
    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("user_type");
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtAuthenticationConverter;

    }

    //Cấu hình decoder cho việc decode token và xác thực ROLE để phân quyền
    @Bean
    JwtDecoder jwtDecoder() {
        SecretKeySpec secretKeySpec = new SecretKeySpec(JwtTokenUtil.getSecretKey().getBytes(), "HS512");
        return NimbusJwtDecoder
                .withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }


//      Cấu hình CORS trong spring security
//    @Bean
//    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.addAllowedOrigin("http://localhost:3000");
//        config.setAllowedHeaders(Arrays.asList(
//                HttpHeaders.AUTHORIZATION,
//                HttpHeaders.CONTENT_TYPE,
//                HttpHeaders.ACCEPT
//        ));
//        config.setAllowedMethods(Arrays.asList(
//                HttpMethod.GET.name(),
//                HttpMethod.POST.name(),
//                HttpMethod.PUT.name(),
//                HttpMethod.DELETE.name()
//        ));
//        config.setMaxAge(3600L);
//        source.registerCorsConfiguration("/**", config);
//        return source;
//    }
}
