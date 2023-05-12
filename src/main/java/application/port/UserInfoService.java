package application.port;

import application.domain.UserInfo;
import application.port.in.UserInfoUseCase;
import application.port.out.UserInfoPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserInfoService implements UserInfoUseCase {
    UserInfoPort userInfoPort;
    @Override
    public UserInfo getUserByEmail(String email) {
        return userInfoPort.getUserByEmail(email);
    }

    @Override
    public UserInfo addUserInfo(UserInfo userInfo) {
        return userInfoPort.addUserInfo(userInfo);
    }

    @Override
    public boolean isUser(String email) {
        return userInfoPort.isUser(email);
    }
}