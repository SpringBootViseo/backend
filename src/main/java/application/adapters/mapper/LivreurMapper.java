package application.adapters.mapper;

import application.adapters.persistence.entity.LivreurEntity;
import application.adapters.web.presenter.LivreurDTO;
import application.domain.Livreur;

public interface LivreurMapper {
    Livreur livreurEntityToLivreur(LivreurEntity livreurEntity);
    LivreurEntity livreurToLivreurEntity(Livreur livreur);
    LivreurDTO livreurToLivreurDTO(Livreur livreur);
    Livreur livreurDTOToLivreur(LivreurDTO livreurDTO);

}
