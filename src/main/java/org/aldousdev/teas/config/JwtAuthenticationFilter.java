package org.aldousdev.teas.config;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aldousdev.teas.service.user.CustomUserDetailsServiceImpl;
import org.aldousdev.teas.service.user.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final CustomUserDetailsServiceImpl customUserDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, CustomUserDetailsServiceImpl customUserDetailsService) {
        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
    }

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String jwt = jwtService.getJwtFromCookie(request);
            jwtService.validateToken(jwt);
            String userEmail = jwtService.extractEmail();

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(userEmail);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authToken);
            SecurityContextHolder.setContext(context);
        } catch (ExpiredJwtException e) {
            // Handle the expired token exception
            SecurityContextHolder.clearContext(); // This effectively logs the user out
            jwtService.removeTokenFromCookie(response);
            logger.error("Invalid JWT token: {}", e.getMessage());

        } catch (Exception e) {
            jwtService.removeTokenFromCookie(response);
            logger.error("Invalid JWT token: {}", e.getMessage());

        }

        filterChain.doFilter(request, response);
    }
}
//@Override
//protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//    try {
//        String jwt = jwtService.getJwtFromCookie(request);
//        logger.info("JWT from cookie: {}", jwt); // debug лог
//
//        jwtService.validateToken(jwt);
//        String userEmail = jwtService.extractEmail(jwt); // ← вот тут главное исправление
//
//        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userEmail);
//
//        UsernamePasswordAuthenticationToken authToken =
//                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//
//        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//        SecurityContext context = SecurityContextHolder.createEmptyContext();
//        context.setAuthentication(authToken);
//        SecurityContextHolder.setContext(context);
//    } catch (ExpiredJwtException e) {
//        SecurityContextHolder.clearContext();
//        jwtService.removeTokenFromCookie(response);
//        logger.error("Expired JWT token: {}", e.getMessage());
//    } catch (Exception e) {
//        SecurityContextHolder.clearContext();
//        jwtService.removeTokenFromCookie(response);
//        logger.error("Invalid JWT token: {}", e.getMessage());
//    }
//
//    filterChain.doFilter(request, response);
//}
//
//
//}
