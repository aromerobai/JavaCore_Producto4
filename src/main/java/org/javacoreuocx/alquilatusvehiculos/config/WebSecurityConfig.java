package org.javacoreuocx.alquilatusvehiculos.config;

import javax.sql.DataSource;

import org.javacoreuocx.alquilatusvehiculos.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


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

    @Bean
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
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/v3/api-docs/**",
                    "/api-docs/**",
                    "/api/**"
                ).permitAll();
                requests.requestMatchers(
                        "/home"
                ).authenticated();
                requests.requestMatchers(
            "/administracion/**"
                ).hasRole("ADMIN");
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