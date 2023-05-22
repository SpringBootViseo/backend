package application.port.in;

import application.domain.Livreur;

public interface LivreurUseCase {
    Livreur addLivreur(Livreur livreur);
    Livreur getLivreur(String email);
    Boolean isLivreur(String email);

}
