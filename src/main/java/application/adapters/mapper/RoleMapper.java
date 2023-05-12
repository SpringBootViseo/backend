package application.adapters.mapper;

import application.adapters.persistence.entity.RoleEntity;
import application.adapters.security.entity.RoleSec;
import application.adapters.web.presenter.RoleDTO;
import application.domain.Role;

public interface RoleMapper {
    Role roleDTOToRole(RoleDTO roleDTO);
    Role roleEntityToRole(RoleEntity roleEntity);
    Role roleSecToRole(RoleSec roleSec);
    RoleSec roleToRoleSec(Role role);
    RoleEntity roleToRoleEntity(Role role);
    RoleDTO roleToRoleDTO(Role role);
    RoleSec roleDTOToRoleSec(RoleDTO roleDTO);
    RoleEntity roleSecToRoleEntity(RoleSec roleSec);
    RoleSec roleEntityToRoleSec(RoleEntity roleEntity);

}
