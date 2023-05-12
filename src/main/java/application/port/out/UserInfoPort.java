package application.port.out;

import application.domain.UserInfo;

public interface UserInfoPort {
    UserInfo getUserByEmail(String email);
    UserInfo addUserInfo(UserInfo userInfo);
    boolean isUser(String email);
}