package application.adapters.mapper.mapperImpl;

import application.adapters.mapper.LivreurMapper;
import application.adapters.persistence.entity.LivreurEntity;
import application.adapters.web.presenter.LivreurDTO;
import application.domain.Livreur;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LivreurMapperImpl implements LivreurMapper {

    @Override
    public Livreur livreurEntityToLivreur(LivreurEntity livreurEntity) {
        return new Livreur(livreurEntity.getFirstname(), livreurEntity.getLastname(), livreurEntity.getEmail());
    }

    @Override
    public LivreurEntity livreurToLivreurEntity(Livreur livreur) {
        return new LivreurEntity(livreur.getFirstname(), livreur.getLastname(), livreur.getEmail());
    }

    @Override
    public LivreurDTO livreurToLivreurDTO(Livreur livreur) {
        return new LivreurDTO(livreur.getFirstname(), livreur.getLastname(), livreur.getEmail());
    }

    @Override
    public Livreur livreurDTOToLivreur(LivreurDTO livreurDTO) {
        return new Livreur(livreurDTO.getFirstname(), livreurDTO.getLastname(), livreurDTO.getEmail());
    }
}
