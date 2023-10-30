package application.port;

import application.domain.UserInfo;
import application.port.in.UserInfoUseCase;
import application.port.out.UserInfoPort;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserInfoService implements UserInfoUseCase {
    private final UserInfoPort userInfoPort;
    private static final Logger logger = LoggerFactory.getLogger(UserInfoService.class);

    @Override
    public UserInfo getUserByEmail(String email) {
        // INFO level for a significant operation
        logger.info("Getting user info for email: {}", email);
        return userInfoPort.getUserByEmail(email);
    }

    @Override
    public UserInfo addUserInfo(UserInfo userInfo) {
        // INFO level for a significant operation
        logger.info("Adding user info: {}", userInfo);
        return userInfoPort.addUserInfo(userInfo);
    }

    @Override
    public boolean isUser(String email) {
        // DEBUG level for a routine operation
        logger.debug("Checking if user with email {} exists", email);
        return userInfoPort.isUser(email);
    }
}
