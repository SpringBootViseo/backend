package application.adapters.mapper;

import application.adapters.security.entity.AuthenticationRequestSec;
import application.adapters.security.entity.AuthenticationResponseSec;
import application.adapters.web.presenter.AuthenticationRequestDTO;
import application.adapters.web.presenter.AuthenticationResponseDTO;
import application.domain.AuthenticationRequest;
import application.domain.AuthenticationResponse;

public interface AuthenticationResponseMapper {
    AuthenticationResponse authenticationResponseDTOToAuthenticationResponse(AuthenticationResponseDTO authenticationResponseDTO);
    AuthenticationResponse authenticationResponseSecToAuthenticationResponse(AuthenticationResponseSec authenticationResponseSec);
    AuthenticationResponseSec authenticationResponseToAuthenticationResponseSec(AuthenticationResponse authenticationResponse);
    AuthenticationResponseSec authenticationResponseDTOToAuthenticationResponseSec(AuthenticationResponseDTO registerResponseDTO);
    AuthenticationResponseDTO authenticationResponseToAuthenticationResposneDTO(AuthenticationResponse authenticationResponse);
}