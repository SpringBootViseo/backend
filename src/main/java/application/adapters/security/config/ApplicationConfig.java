package application.adapters.security.config;

import application.adapters.mapper.mapperImpl.UserInfoMapperImpl;
import application.adapters.persistence.entity.UserInfoEntity;
import application.adapters.persistence.repository.UserInfoRepository;
import application.port.out.UserInfoPort;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    // private final UserInfoRepository repository;
    private final static Logger logger= LogManager.getLogger(ApplicationConfig.class);
    private final UserInfoPort userInfoPort;
    private final UserInfoMapperImpl userInfoMapper;

    @Bean
    public UserDetailsService userDetailsService() {
        logger.debug("Instantiate a bean that map user info in DB to user info in Spring Security ");
        return username -> userInfoMapper.userInfoToUserInfoSec( userInfoPort.getUserByEmail(username));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        logger.debug("Instantiate a bean of authentication using DAO and password encoder");
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        logger.debug("Instantiate a bean of authenticationManager with our configuration");

        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        logger.debug("Instantiate a bean of  password encoder");

        return new BCryptPasswordEncoder();
    }
}