package application.adapters.mapper;

import application.adapters.persistence.entity.PreparateurEntity;
import application.adapters.web.presenter.PreparateurDTO;

import application.domain.Preparateur;


public interface PreparateurMapper {
    Preparateur preparateurEntityToPreparateur(PreparateurEntity preparateurEntity);
    PreparateurEntity preparateurToPreparateurEntity(Preparateur preparateur);
    PreparateurDTO preparateurToPreparateurDTO(Preparateur preparateur);
    Preparateur preparateurDTOToPreparateur(PreparateurDTO preparateurDTO);

}
