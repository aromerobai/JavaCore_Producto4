package org.javacoreuocx.alquilatusvehiculos.config;

import javax.sql.DataSource;

import jakarta.servlet.Filter;
import org.javacoreuocx.alquilatusvehiculos.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Value("${internal.api-key}")
    private String internalApiKey;
    @Bean
    @Order(1)
    public SecurityFilterChain filterChainPrivate(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/internal/**")
                .addFilterBefore(new InternalApiKeyAuthenticationFilter(internalApiKey), ChannelProcessingFilter.class)
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(requests -> {
                requests.requestMatchers(
                    "/",
                    "/register",
                    "/static/**",
                    "/images/**",
                    "/css/**",
                    "/js/**",
                    "/login",
                    "/api/v1/guest/**",
                    "/api/v1/auth/**",
                    "/api-docs/**",
                    "/v2/api-docs",
                    "/v3/api-docs",
                    "/v3/api-docs/**",
                    "/swagger-resources",
                    "/swagger-resources/**",
                    "/configuration/ui",
                    "/configuration/security",
                    "/swagger-ui/**",
                    "/webjars/**",
                    "/swagger-ui.html"
                ).permitAll();
                requests.requestMatchers("/home").authenticated();
                requests.requestMatchers("/administracion/**").hasRole("ADMIN");
                requests.anyRequest().authenticated();
            });
        http.csrf(csrf -> csrf.disable());
        http.formLogin(form -> {
            form.loginPage("/login");
            form.defaultSuccessUrl("/home", true);
            form.failureUrl("/login?error=true");
            form.permitAll();
        });
        http.logout(logout -> {
            logout.logoutSuccessUrl("/login?logout=true");
            logout.permitAll();
        });

        return http.build();
    }

}