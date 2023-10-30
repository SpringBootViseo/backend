package application.adapters.security.config;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import static application.adapters.security.entity.RoleSec.ADMIN;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalAuthentication
@CrossOrigin(origins = "*")
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;
    private final static Logger logger= LogManager.getLogger(SecurityConfiguration.class);

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        logger.debug("Instantiate the security filter chain");

        http
                .csrf()

                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/createCustomer","/preference/**","/auth/**","/users/**","/payments","/payments/**","/categories/**","/products/**","/categoryProducts/**","/carts/**","/orders/**","/orderStates","/orderStates/**","/orders","/users","/categories","/products","/categoryProducts","/carts","/preference")
                .permitAll()
                .and()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST,"/categories").hasAnyRole(ADMIN.name())
                .requestMatchers(HttpMethod.POST,"/products").hasAnyRole(ADMIN.name())
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/auth/**","auth/register","/users/**")
                .permitAll()

                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

                .logout()

                .logoutUrl("/auth/logout")
                .permitAll()


                .addLogoutHandler(logoutHandler)

                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                .permitAll();



        return http.build();
    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        logger.debug("Instantiate the CorsConfig");

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}