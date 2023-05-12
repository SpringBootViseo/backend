package application.adapters.mapper.mapperImpl;

import application.adapters.mapper.AuthenticationResponseMapper;
import application.adapters.security.entity.AuthenticationResponseSec;
import application.adapters.web.presenter.AuthenticationResponseDTO;
import application.domain.AuthenticationResponse;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationResponseMapperImpl implements AuthenticationResponseMapper {
    @Override
    public AuthenticationResponse authenticationResponseDTOToAuthenticationResponse(AuthenticationResponseDTO authenticationResponseDTO) {
        return new AuthenticationResponse(authenticationResponseDTO.getAccessToken(),authenticationResponseDTO.getRefreshToken());
    }

    @Override
    public AuthenticationResponse authenticationResponseSecToAuthenticationResponse(AuthenticationResponseSec authenticationResponseSec) {
        return new AuthenticationResponse(authenticationResponseSec.getAccessToken(),authenticationResponseSec.getRefreshToken());
    }

    @Override
    public AuthenticationResponseSec authenticationResponseToAuthenticationResponseSec(AuthenticationResponse authenticationResponse) {
        return new AuthenticationResponseSec(authenticationResponse.getAccessToken(), authenticationResponse.getRefreshToken());
    }

    @Override
    public AuthenticationResponseSec authenticationResponseDTOToAuthenticationResponseSec(AuthenticationResponseDTO authenticationResponseDTO) {
        return new AuthenticationResponseSec(authenticationResponseDTO.getAccessToken(),authenticationResponseDTO.getRefreshToken());    }

    @Override
    public AuthenticationResponseDTO authenticationResponseToAuthenticationResposneDTO(AuthenticationResponse authenticationResponse) {
        return new AuthenticationResponseDTO(authenticationResponse.getAccessToken(),authenticationResponse.getRefreshToken());
    }
}