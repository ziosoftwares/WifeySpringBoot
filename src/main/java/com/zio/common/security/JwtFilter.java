package com.zio.common.security;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer")) {
            response.setStatus(400);
            response.setContentType("application/json");
            response.getWriter().write("{\"body\": false,\"statusCode\": 400,\"error\": {\"message\": \"JWT unavailable\",\"code\": 1}}");

            return;
        }

        Long userId = -1L;

        try {
            String jwt = header.substring(7);
            userId = jwtUtil.getUserId(jwt);

            AuthenticationToken authToken = new AuthenticationToken(userId, true);
            SecurityContextHolder.getContext().setAuthentication(authToken);

            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            response.setStatus(400);
            response.setContentType("application/json");
            response.getWriter().write("{\"body\": false,\"statusCode\": 400,\"error\": {\"message\": \"JWT invalid or expired\",\"code\": 2}}");
        }

    }
}
