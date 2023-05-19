package application.port.in;

import application.domain.Preparateur;

public interface PreparateurUseCase {
    Preparateur addPreparateur(Preparateur preparateur);
    Preparateur getPreparateur(String email);
    Boolean isPreparateur(String email);
}

