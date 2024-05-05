package org.javacoreuocx.alquilatusvehiculos.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.Map;

public class InternalApiKeyAuthenticationFilter implements Filter {

    private final String internalApiKey;
    InternalApiKeyAuthenticationFilter(String internalApiKey) {
        this.internalApiKey = internalApiKey;
    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        // get Bearer token from header
        String bearerToken = httpServletRequest.getHeader("Authorization");
        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            unauthorized(httpServletResponse);
            return;
        }
        String token = bearerToken.substring(7);
        if (!internalApiKey.equals(token)) {
            unauthorized(httpServletResponse);
            return;
        }
        chain.doFilter(request, response);
    }
    @Override
    public void destroy() {
    }
    private void unauthorized(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setStatus(401);
        Map<String, Object> response = Map.of("message", "SC_UNAUTHORIZED");
        String responseBody = new ObjectMapper().writeValueAsString(response);
        httpServletResponse.getWriter().write(responseBody);
    }
}
