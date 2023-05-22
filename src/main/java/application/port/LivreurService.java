package application.port;

import application.adapters.exception.UserAlreadyExistsException;
import application.domain.Livreur;
import application.port.in.LivreurUseCase;
import application.port.out.LivreurPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@AllArgsConstructor
@Service
public class LivreurService implements LivreurUseCase {
    private final LivreurPort livreurPort;
    @Override
    public Livreur addLivreur(Livreur livreur) {
        if(livreurPort.isLivreur(livreur.getEmail()))
            throw new UserAlreadyExistsException("Livreur Already exist!");
        return livreurPort.addLivreur(livreur);
    }

    @Override
    public Livreur getLivreur(String email) {
        if(livreurPort.isLivreur(email))
        return livreurPort.getLivreur(email);
        else throw new NoSuchElementException("Livreur with such mail doesn't exist!");
    }

    @Override
    public Boolean isLivreur(String email) {
        return livreurPort.isLivreur(email);
    }
}
