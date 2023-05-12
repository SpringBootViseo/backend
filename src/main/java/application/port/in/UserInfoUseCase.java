package application.port.in;

import application.domain.UserInfo;
public interface UserInfoUseCase {
    UserInfo getUserByEmail(String email);
    UserInfo addUserInfo(UserInfo userInfo);
    boolean isUser(String email);
}
