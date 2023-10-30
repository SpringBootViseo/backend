package application.port;

import application.domain.UserInfo;
import application.port.in.UserInfoUseCase;
import application.port.out.UserInfoPort;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserInfoService implements UserInfoUseCase {
    UserInfoPort userInfoPort;
    private final static Logger logger= LogManager.getLogger(TokenService.class);

    @Override
    public UserInfo getUserByEmail(String email) {

        logger.info("Get user with email :"+email);
        return userInfoPort.getUserByEmail(email);
    }

    @Override
    public UserInfo addUserInfo(UserInfo userInfo)
    {

        logger.info("Add user "+userInfo.toString());
        return userInfoPort.addUserInfo(userInfo);
    }

    @Override
    public boolean isUser(String email) {

        logger.info("check if a user have email: "+email);
        return userInfoPort.isUser(email);
    }
}