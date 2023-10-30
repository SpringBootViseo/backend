package application.adapters.security.config;

import application.adapters.persistence.repository.TokenRepository;
import application.port.out.TokenPort;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

@Service
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    private final TokenPort tokenPort;
    private final static Logger logger= LogManager.getLogger(LogoutService.class);
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        logger.debug("Retrieve the Authorization Header from requet");

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        logger.debug("Check if the Authorization Header starts with Bearer");

        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        logger.debug("Ommit \"Bearer\" from the Authorization Header and atribute it to Jwt");

        jwt = authHeader.substring(7);
        logger.debug("Revoke and put token expired");
        var storedToken = tokenPort.getToken(jwt);
        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenPort.addToken(storedToken);
            logger.debug("Do filter");

            SecurityContextHolder.clearContext();
        }
    }
}