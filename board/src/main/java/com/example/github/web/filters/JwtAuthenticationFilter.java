package com.example.github.web.filters;

import com.example.github.config.security.JwtTokenProvider;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwtToken = jwtTokenProvider.resolveToken(request);

            if (jwtToken != null && !jwtTokenProvider.isTokenBlackListed(jwtToken) && jwtTokenProvider.validateToken(jwtToken)) {
                Authentication auth = jwtTokenProvider.getAuthentication(jwtToken);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch(JwtException e){
            e.printStackTrace();
            throw new JwtException("해당 토큰은 만료되었거나 유효하지 않습니다.");
        } catch(Exception e){
            e.printStackTrace();
            throw new JwtException("모든 예외 잡기");
        }
        filterChain.doFilter(request, response);
    }
}