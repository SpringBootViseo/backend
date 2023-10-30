
package application.adapters.persistence.adapter;

import application.adapters.exception.UserAlreadyExistsException;
import application.adapters.mapper.mapperImpl.UserInfoMapperImpl;
import application.adapters.persistence.entity.UserInfoEntity;
import application.adapters.persistence.repository.UserInfoRepository;
import application.domain.User;
import application.domain.UserInfo;
import application.port.out.UserInfoPort;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserInfoDBAdapter implements UserInfoPort {
    private final UserInfoRepository userInfoRepository;
    private final UserInfoMapperImpl userInfoMapper;
    private  static final Logger logger = LoggerFactory.getLogger(UserInfoDBAdapter.class);

    @Override
    public UserInfo getUserByEmail(String email) {
        logger.info("Getting user by email: {}", email);
        logger.trace("Checking if user with email: {} exists", email);

        if (userInfoRepository.findByEmail(email).isPresent()) {
            UserInfoEntity userInfoEntity = userInfoRepository.findByEmail(email).get();
            UserInfo userInfo = userInfoMapper.userInfoEntityToUserInfo(userInfoEntity);
            logger.debug("Converting userInfoEntity to UserInfo");
            logger.debug("Retrieved user with email: {}", email);
            return userInfo;
        } else {
            logger.error("User with email: {} not found", email);
            throw new UserAlreadyExistsException("User doesn't exist");
        }
    }

    @Override
    public UserInfo addUserInfo(UserInfo userInfo) {
        logger.info("Adding user information");

        UserInfoEntity userInfoEntity = userInfoMapper.userInfoToUserInfo(userInfo);
        logger.debug("Converting UserInfo to UserInfoEntity");
        UserInfoEntity savedUserInfoEntity = userInfoRepository.save(userInfoEntity);
        logger.debug("Saving UserInfoEntity");
        UserInfo savedUserInfo = userInfoMapper.userInfoEntityToUserInfo(savedUserInfoEntity);
        logger.debug("Converting UserInfoEntity to UserInfo");

        logger.info("User information added successfully");

        return savedUserInfo;
    }

    @Override
    public boolean isUser(String email) {
        logger.info("Checking if user with email: {} exists", email);

        return userInfoRepository.findByEmail(email).isPresent();
    }

}
