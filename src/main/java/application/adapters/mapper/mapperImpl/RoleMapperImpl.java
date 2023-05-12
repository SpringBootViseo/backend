package application.adapters.mapper.mapperImpl;

import application.adapters.mapper.RoleMapper;
import application.adapters.persistence.entity.RoleEntity;
import application.adapters.security.entity.RoleSec;
import application.adapters.web.presenter.RoleDTO;
import application.domain.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@Component
public class RoleMapperImpl implements RoleMapper {

    @Override
    public Role roleDTOToRole(RoleDTO roleDTO) {
        Role role;
        if(roleDTO==RoleDTO.ADMIN)
            role=Role.ADMIN;
        else if (roleDTO==RoleDTO.DELIVERY)
            role=Role.DELIVERY;
        else if (roleDTO==RoleDTO.PREPARATOR)
            role=Role.PREPARATOR;
        else if (roleDTO==RoleDTO.CLIENT) {
            role=Role.CLIENT;
        }
        else
            role=Role.USER;
        return role;
    }

    @Override
    public Role roleEntityToRole(RoleEntity roleEntity) {
        Role role;
        if(roleEntity==RoleEntity.ADMIN)
            role=Role.ADMIN;
        else if (roleEntity==RoleEntity.DELIVERY)
            role=Role.DELIVERY;
        else if (roleEntity==RoleEntity.PREPARATOR)
            role=Role.PREPARATOR;
        else if (roleEntity==RoleEntity.CLIENT) {
            role=Role.CLIENT;
        }
        else
            role=Role.USER;
        return role;
    }

    @Override
    public Role roleSecToRole(RoleSec roleSec) {
        Role role;
        if(roleSec==RoleSec.ADMIN)
            role=Role.ADMIN;
        else if (roleSec==RoleSec.DELIVERY)
            role=Role.DELIVERY;
        else if (roleSec==RoleSec.PREPARATOR)
            role=Role.PREPARATOR;
        else if (roleSec==RoleSec.CLIENT) {
            role=Role.CLIENT;
        }
        else
            role=Role.USER;
        return role;
    }

    @Override
    public RoleSec roleToRoleSec(Role role) {
        RoleSec roleSec;
        if(role==Role.ADMIN)
            roleSec=RoleSec.ADMIN;
        else if (role==Role.DELIVERY)
            roleSec=RoleSec.DELIVERY;
        else if (role==Role.PREPARATOR)
            roleSec=RoleSec.PREPARATOR;
        else if (role==Role.CLIENT) {
            roleSec=RoleSec.CLIENT;
        }
        else
            roleSec=RoleSec.USER;
        return roleSec;
    }

    @Override
    public RoleEntity roleToRoleEntity(Role role) {
        RoleEntity roleEntity;
        if(role==Role.ADMIN)
            roleEntity=RoleEntity.ADMIN;
        else if (role==Role.DELIVERY)
            roleEntity=RoleEntity.DELIVERY;
        else if (role==Role.PREPARATOR)
            roleEntity=RoleEntity.PREPARATOR;
        else if (role==Role.CLIENT) {
            roleEntity=RoleEntity.CLIENT;
        }
        else
            roleEntity=RoleEntity.USER;
        return roleEntity;
    }

    @Override
    public RoleDTO roleToRoleDTO(Role role) {
        RoleDTO roleDTO;
        if(role==Role.ADMIN)
            roleDTO=RoleDTO.ADMIN;
        else if (role==Role.DELIVERY)
            roleDTO=RoleDTO.DELIVERY;
        else if (role==Role.PREPARATOR)
            roleDTO=RoleDTO.PREPARATOR;
        else if (role==Role.CLIENT) {
            roleDTO=RoleDTO.CLIENT;
        }
        else
            roleDTO=RoleDTO.USER;
        return roleDTO;
    }

    @Override
    public RoleSec roleDTOToRoleSec(RoleDTO roleDTO) {
        RoleSec roleSec;
        if(roleDTO==RoleDTO.ADMIN)
            roleSec=RoleSec.ADMIN;
        else if (roleDTO==RoleDTO.DELIVERY)
            roleSec=RoleSec.DELIVERY;
        else if (roleDTO==RoleDTO.PREPARATOR)
            roleSec=RoleSec.PREPARATOR;
        else if (roleDTO==RoleDTO.CLIENT) {
            roleSec=RoleSec.CLIENT;
        }
        else
            roleSec=RoleSec.USER;
        return roleSec;
    }

    @Override
    public RoleEntity roleSecToRoleEntity(RoleSec roleSec) {
        RoleEntity roleEntity;
        if(roleSec==RoleSec.ADMIN)
            roleEntity=RoleEntity.ADMIN;
        else if (roleSec==RoleSec.DELIVERY)
            roleEntity=RoleEntity.DELIVERY;
        else if (roleSec==RoleSec.PREPARATOR)
            roleEntity=RoleEntity.PREPARATOR;
        else if (roleSec==RoleSec.CLIENT) {
            roleEntity=RoleEntity.CLIENT;
        }
        else
            roleEntity=RoleEntity.USER;

        return roleEntity;
    }

    @Override
    public RoleSec roleEntityToRoleSec(RoleEntity roleEntity) {
        RoleSec roleSec;
        if(roleEntity==RoleEntity.ADMIN)
            roleSec=RoleSec.ADMIN;
        else if (roleEntity==RoleEntity.DELIVERY)
            roleSec=RoleSec.DELIVERY;
        else if (roleEntity==RoleEntity.PREPARATOR)
            roleSec=RoleSec.PREPARATOR;
        else if (roleEntity==RoleEntity.CLIENT) {
            roleSec=RoleSec.CLIENT;
        }
        else
            roleSec=RoleSec.USER;
        return null;
    }
}
