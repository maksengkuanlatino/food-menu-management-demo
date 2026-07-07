package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();


        if (uri.equals("/login") || uri.startsWith("/api/auth/")) {
            return true;
        }
        
        if (uri.startsWith("/menu")) {
            // Check headers first
            String token = request.getHeader("Authorization");
            
          
            if (token == null || token.isEmpty()) {
                token = request.getParameter("token");
            } else if (token.startsWith("Bearer ")) {
                token = token.substring(7); 
            }
            
        
            if (token != null && !token.isEmpty()) {
                try {
                    String username = jwtUtil.extractUsername(token); 
                    
                    if (jwtUtil.validateToken(token, username)) {
                        return true; 
                    }
                } catch (Exception e) {
                    System.out.println("JWT Validation failed: " + e.getMessage());
                }
            }
        }
        
        
        response.sendRedirect("/login");
        return false;
    }
}