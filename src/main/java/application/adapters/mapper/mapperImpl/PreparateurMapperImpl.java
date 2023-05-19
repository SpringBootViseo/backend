package application.adapters.mapper.mapperImpl;

import application.adapters.mapper.PreparateurMapper;
import application.adapters.persistence.entity.PreparateurEntity;
import application.adapters.web.presenter.PreparateurDTO;
import application.domain.Preparateur;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PreparateurMapperImpl implements PreparateurMapper {
    @Override
    public Preparateur preparateurEntityToPreparateur(PreparateurEntity preparateurEntity) {
        return new Preparateur( preparateurEntity.getFirstname(), preparateurEntity.getLastname(), preparateurEntity.getEmail());
    }

    @Override
    public PreparateurEntity preparateurToPreparateurEntity(Preparateur preparateur) {
        return new PreparateurEntity( preparateur.getFirstname(), preparateur.getLastname(), preparateur.getEmail());
    }

    @Override
    public PreparateurDTO preparateurToPreparateurDTO(Preparateur preparateur) {
        return new PreparateurDTO( preparateur.getFirstname(), preparateur.getLastname(), preparateur.getEmail());
    }

    @Override
    public Preparateur preparateurDTOToPreparateur(PreparateurDTO preparateurDTO) {
        return new Preparateur( preparateurDTO.getFirstname(), preparateurDTO.getLastname(), preparateurDTO.getEmail());
    }


}
