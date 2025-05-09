package com.shahrohit.chat.configs;

import com.shahrohit.chat.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            if(isProtectedRoute(request)){
                sendErrorResponse(response, "Unauthorized: Token is missing");
                return;
            }
            filterChain.doFilter(request,response);
            return;
        }

        String token = authHeader.substring(7);
        try{
            String username = jwtUtil.extractSubject(token);
            if(username == null){
                sendErrorResponse(response, "Invalid JWT token");
                return;
            }

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if(jwtUtil.isTokenValid(token, userDetails.getUsername())){
                UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

        } catch (Exception e){
            sendErrorResponse(response, "Unauthorized");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        String jsonStr = "{\"statusCode\": \"" + HttpServletResponse.SC_UNAUTHORIZED + "\", \"message\": \"" + message + "\", \"errorData\": null}";;
        response.getWriter().write(jsonStr);
    }
    private boolean isProtectedRoute(HttpServletRequest request) {
        String path = request.getRequestURI();
        return !(path.startsWith("/public") || path.startsWith("/api/auth"));
    }
}
