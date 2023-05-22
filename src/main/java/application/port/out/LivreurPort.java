package application.port.out;

import application.domain.Livreur;

public interface LivreurPort {
    Livreur addLivreur(Livreur livreur);
    Livreur getLivreur(String email);
    Boolean isLivreur(String email);
}
