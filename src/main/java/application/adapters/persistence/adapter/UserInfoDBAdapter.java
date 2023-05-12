
package application.adapters.persistence.adapter;

import application.adapters.exception.UserAlreadyExistsException;
import application.adapters.mapper.mapperImpl.UserInfoMapperImpl;
import application.adapters.persistence.entity.UserInfoEntity;
import application.adapters.persistence.repository.UserInfoRepository;
import application.domain.User;
import application.domain.UserInfo;
import application.port.out.UserInfoPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserInfoDBAdapter implements UserInfoPort {
    private final UserInfoRepository userInfoRepository;
    private final UserInfoMapperImpl userInfoMapper;
    @Override
    public UserInfo getUserByEmail(String email) {
        if(userInfoRepository.findByEmail(email).isPresent()) {
            UserInfoEntity userInfoEntity = userInfoRepository.findByEmail(email).get();
            return userInfoMapper.userInfoEntityToUserInfo(userInfoEntity);
        }
        else throw new UserAlreadyExistsException("User doesn't exist");
    }

    @Override
    public UserInfo addUserInfo(UserInfo userInfo) {
        UserInfoEntity userInfoEntity=userInfoMapper.userInfoToUserInfo(userInfo);
        return userInfoMapper.userInfoEntityToUserInfo(userInfoRepository.save(userInfoEntity));
    }

    @Override
    public boolean isUser(String email) {
        return userInfoRepository.findByEmail(email).isPresent();
    }
}
