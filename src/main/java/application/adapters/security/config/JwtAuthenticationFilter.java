package application.adapters.security.config;

import application.adapters.mapper.mapperImpl.UserInfoMapperImpl;
import application.adapters.persistence.entity.UserEntity;
import application.adapters.persistence.entity.UserInfoEntity;
import application.adapters.persistence.repository.TokenRepository;
import application.adapters.persistence.repository.UserInfoRepository;
import application.domain.User;
import application.domain.UserInfo;
import application.port.out.TokenPort;
import application.port.out.UserInfoPort;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.beans.Transient;
import java.io.IOException;
import java.security.Security;


import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final static Logger logger= LogManager.getLogger(JwtAuthenticationFilter.class);
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenPort tokenPort;
    private final UserInfoPort userInfoPort;
    private final UserInfoMapperImpl userInfoMapper;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        logger.debug("Check if request contains /api/v1/auth");
        if (request.getServletPath().contains("/api/v1/auth")) {
            logger.debug("Do filter");
            filterChain.doFilter(request, response);
            return;
        }
        logger.debug("Retrieve the Authorization Header from requet");
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        logger.debug("Check if the Authorization Header starts with Bearer");
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            logger.debug("Do filter");

            filterChain.doFilter(request, response);
            return;
        }
        logger.debug("Ommit \"Bearer\" from the Authorization Header and atribute it to Jwt");

        jwt = authHeader.substring(7);
        logger.debug("Extract Email");

        userEmail = jwtService.extractUsername(jwt);
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            logger.debug("Get Info of User with email "+userEmail);

            UserInfo userInfo=userInfoPort.getUserByEmail(userEmail);


            UserDetails userDetails = userInfoMapper.userInfoToUserInfoSec(userInfo);

            var isTokenValid = tokenPort.isValid(jwt);
            logger.debug("check if token "+jwt+" is valid");

            if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                logger.debug("Put User on the context and give him his authorities");

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        logger.debug("Do filter");

        filterChain.doFilter(request, response);
    }
}