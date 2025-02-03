package com.btl.filters;

import com.btl.pojo.NguoiDung;
import com.btl.components.JwtService;
import com.btl.service.NguoiDungService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final static String TOKEN_HEADER = "authorization";
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtService jwtService;

    @Autowired
    private NguoiDungService nguoiDungService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authToken = httpRequest.getHeader(TOKEN_HEADER);

        if (authToken != null && authToken.startsWith("Bearer ")) {
            authToken = authToken.substring(7); // Remove "Bearer " prefix

            if (jwtService.validateTokenLogin(authToken)) {
                String username = jwtService.getUsernameFromToken(authToken);
                if (username != null) {
                    logger.info("Token is valid. Username from token: {}", username);

                    NguoiDung user = nguoiDungService.getUserByUsername(username);

                    if (user != null) {
                        UserDetails userDetails = user;
                        UsernamePasswordAuthenticationToken authentication
                                = new UsernamePasswordAuthenticationToken(userDetails,
                                        null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        logger.info("User authenticated: {}", username);
                    } else {
                        logger.warn("User not found: {}", username);
                    }
                } else {
                    logger.warn("Username could not be extracted from token");
                }
            } else {
                logger.warn("Invalid or expired token");
            }
        } else {
            logger.warn("No token found in request header");
        }

        chain.doFilter(request, response);
    }

}
