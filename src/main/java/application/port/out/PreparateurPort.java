package application.port.out;

import application.domain.Preparateur;

public interface PreparateurPort {
    Preparateur addPreparateur(Preparateur preparateur);
    Preparateur getPreparateur(String email);
    Boolean isPreparateur(String email);
}
