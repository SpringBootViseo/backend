package application.adapters.mapper;

import application.adapters.persistence.entity.UserInfoEntity;
import application.adapters.security.entity.UserInfoSec;
import application.domain.UserInfo;

public interface UserInfoMapper {
    UserInfoEntity userInfoToUserInfo(UserInfo userInfo);
    UserInfo userInfoEntityToUserInfo(UserInfoEntity userInfoEntity);
    UserInfoSec userInfoToUserInfoSec(UserInfo userInfo);
    UserInfoSec userInfoEntityToUserInfoSec(UserInfoEntity userInfoEntity);
    UserInfo userInfoSecToUserInfo(UserInfoSec userInfoSec);
    UserInfoEntity userInfoSecToUserInfoEntity(UserInfoSec userInfoSec);
}