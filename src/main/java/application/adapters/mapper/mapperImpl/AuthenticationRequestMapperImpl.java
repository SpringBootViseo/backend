package application.adapters.mapper.mapperImpl;

import application.adapters.mapper.AuthenticationRequestMapper;
import application.adapters.security.entity.AuthenticationRequestSec;
import application.adapters.web.presenter.AuthenticationRequestDTO;
import application.domain.AuthenticationRequest;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationRequestMapperImpl implements AuthenticationRequestMapper {
    @Override
    public AuthenticationRequest authenticationRequestDTOToAuthenticationRequest(AuthenticationRequestDTO authenticationRequestDTO) {
        return new AuthenticationRequest(authenticationRequestDTO.getEmail(), authenticationRequestDTO.getPassword());
    }

    @Override
    public AuthenticationRequest authenticationRequestSecToAuthenticationRequest(AuthenticationRequestSec authenticationRequestSec) {
        return new AuthenticationRequest(authenticationRequestSec.getEmail(),authenticationRequestSec.getPassword());
    }

    @Override
    public AuthenticationRequestSec authenticationRequestToAuthenticationRequestSec(AuthenticationRequest authenticationRequest) {
        return new AuthenticationRequestSec(authenticationRequest.getEmail(), authenticationRequest.getPassword());
    }

    @Override
    public AuthenticationRequestSec authenticationRequestDTOToAuthenticationRequestSec(AuthenticationRequestDTO authenticationRequestDTO) {
        return new AuthenticationRequestSec(authenticationRequestDTO.getEmail(), authenticationRequestDTO.getPassword());
    }

    @Override
    public AuthenticationRequestDTO authenticationRequestToAuthenticationRequestDTO(AuthenticationRequest authenticationRequest) {
        return new AuthenticationRequestDTO(authenticationRequest.getEmail(), authenticationRequest.getPassword());
    }
}