package application.adapters.mapper;

import application.adapters.security.entity.RegisterRequestSec;
import application.adapters.web.presenter.RegisterRequestDTO;
import application.domain.RegisterRequest;

public interface RegisterRequestMapper {
    RegisterRequest registerRequestDTOToRegisterRequest(RegisterRequestDTO registerRequestDTO);
    RegisterRequest registerRequestSecToRegisterRequest(RegisterRequestSec registerRequestSec);
    RegisterRequestSec registerRequestToRegisterRequestSec(RegisterRequest registerRequest);
    RegisterRequestSec registerRequestDTOToRegisterRequestSec(RegisterRequestDTO registerRequestDTO);

    RegisterRequestDTO registerRequestToRegisterRequestDTO(RegisterRequest registerRequest);


}