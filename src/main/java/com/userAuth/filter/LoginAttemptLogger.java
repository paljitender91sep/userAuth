package com.userAuth.filter;

import com.userAuth.entity.LoginAttempt;
import com.userAuth.repository.LoginAttemptRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class LoginAttemptLogger extends OncePerRequestFilter {
    @Autowired
    private LoginAttemptRepository loginAttemptRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if ("/login".equals(request.getRequestURI()) && "POST".equalsIgnoreCase(request.getMethod())) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String ip = request.getRemoteAddr();
            loginAttemptRepository.save(new LoginAttempt(username, password, ip, LocalDateTime.now()));
        }
        filterChain.doFilter(request, response);
    }

}