package application.adapters.mapper.mapperImpl;

import application.adapters.mapper.UserInfoMapper;
import application.adapters.persistence.entity.RoleEntity;
import application.adapters.persistence.entity.TokenEntity;
import application.adapters.persistence.entity.UserInfoEntity;
import application.adapters.security.entity.RoleSec;
import application.adapters.security.entity.UserInfoSec;
import application.domain.Role;
import application.domain.Token;
import application.domain.UserInfo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class UserInfoMapperImpl implements UserInfoMapper {
    RoleMapperImpl roleMapper;
    TokenMapperImpl tokenMapper;
    @Override
    public UserInfoEntity userInfoToUserInfo(UserInfo userInfo) {
        RoleEntity role=roleMapper.roleToRoleEntity(userInfo.getRole()) ;
        List<TokenEntity> tokenEntityList=tokenMapper.listTokenToListTokenEntity(userInfo.getTokens());

        return new UserInfoEntity(userInfo.getId(), userInfo.getFirstname(), userInfo.getLastname(), userInfo.getEmail(), userInfo.getPassword(), tokenEntityList, role);
    }

    @Override
    public UserInfo userInfoEntityToUserInfo(UserInfoEntity userInfoEntity) {
        Role role =roleMapper.roleEntityToRole(userInfoEntity.getRole());
      List<Token> tokenList=tokenMapper.listTokenEntityToListToken(userInfoEntity.getTokens());

        return new UserInfo(userInfoEntity.getId(), userInfoEntity.getFirstname(), userInfoEntity.getLastname(), userInfoEntity.getEmail(), userInfoEntity.getPassword(), tokenList, role);

    }

    @Override
    public UserInfoSec userInfoToUserInfoSec(UserInfo userInfo) {
        RoleSec roleSec=roleMapper.roleToRoleSec(userInfo.getRole());

        return new UserInfoSec(userInfo.getId(), userInfo.getFirstname(), userInfo.getLastname(), userInfo.getEmail(), userInfo.getPassword(), userInfo.getTokens(),roleSec);
    }

    @Override
    public UserInfoSec userInfoEntityToUserInfoSec(UserInfoEntity userInfoEntity) {
        RoleSec roleSec=roleMapper.roleEntityToRoleSec(userInfoEntity.getRole());

       List<Token> tokenEntityList=tokenMapper.listTokenEntityToListToken(userInfoEntity.getTokens());
        return new UserInfoSec(userInfoEntity.getId(), userInfoEntity.getFirstname(), userInfoEntity.getLastname(), userInfoEntity.getEmail(), userInfoEntity.getPassword(), tokenEntityList, roleSec);

    }

    @Override
    public UserInfo userInfoSecToUserInfo(UserInfoSec userInfoSec) {
        Role role=roleMapper.roleSecToRole(userInfoSec.getRole());

        return new UserInfo(userInfoSec.getId(), userInfoSec.getFirstname(), userInfoSec.getLastname(), userInfoSec.getUsername(), userInfoSec.getPassword(), userInfoSec.getTokens(), role);

    }

    @Override
    public UserInfoEntity userInfoSecToUserInfoEntity(UserInfoSec userInfoSec) {
        RoleEntity role=roleMapper.roleSecToRoleEntity(userInfoSec.getRole());
        List<TokenEntity> tokenEntityList=tokenMapper.listTokenToListTokenEntity(userInfoSec.getTokens());
        return new UserInfoEntity(userInfoSec.getId(), userInfoSec.getFirstname(), userInfoSec.getLastname(), userInfoSec.getUsername(), userInfoSec.getPassword(), tokenEntityList, role);
    }

}