package application.adapters.mapper.mapperImpl;

import application.adapters.mapper.RegisterRequestMapper;
import application.adapters.security.entity.RegisterRequestSec;
import application.adapters.security.entity.RoleSec;
import application.adapters.web.presenter.RegisterRequestDTO;
import application.adapters.web.presenter.RoleDTO;
import application.domain.RegisterRequest;
import application.domain.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class RegisterRequestMapperImpl implements RegisterRequestMapper {
    RoleMapperImpl roleMapper;
    @Override
    public RegisterRequest registerRequestDTOToRegisterRequest(RegisterRequestDTO registerRequestDTO) {
        Role role=roleMapper.roleDTOToRole(registerRequestDTO.getRole());


        return new RegisterRequest(registerRequestDTO.getFirstname(), registerRequestDTO.getLastname(),registerRequestDTO.getEmail(),registerRequestDTO.getPassword(),role);
    }

    @Override
    public RegisterRequest registerRequestSecToRegisterRequest(RegisterRequestSec registerRequestSec) {
        Role role=roleMapper.roleSecToRole(registerRequestSec.getRole());

        return new RegisterRequest(registerRequestSec.getFirstname(),registerRequestSec.getLastname(),registerRequestSec.getEmail(),registerRequestSec.getPassword(),role);
    }

    @Override
    public RegisterRequestSec registerRequestToRegisterRequestSec(RegisterRequest registerRequest) {
       RoleSec roleSec=roleMapper.roleToRoleSec(registerRequest.getRole());
        return new RegisterRequestSec(registerRequest.getFirstname(), registerRequest.getLastname(),registerRequest.getEmail(),registerRequest.getPassword(),roleSec);
    }

    @Override
    public RegisterRequestSec registerRequestDTOToRegisterRequestSec(RegisterRequestDTO registerRequestDTO) {
        RoleSec roleSec=roleMapper.roleDTOToRoleSec(registerRequestDTO.getRole());

        return new RegisterRequestSec(registerRequestDTO.getFirstname(), registerRequestDTO.getLastname(),registerRequestDTO.getEmail(),registerRequestDTO.getPassword(),roleSec);
    }

    @Override
    public RegisterRequestDTO registerRequestToRegisterRequestDTO(RegisterRequest registerRequest) {
        RoleDTO roleDTO=roleMapper.roleToRoleDTO(registerRequest.getRole());

        return new RegisterRequestDTO(registerRequest.getFirstname(),registerRequest.getLastname(),registerRequest.getEmail(),registerRequest.getPassword(),roleDTO);
    }
}