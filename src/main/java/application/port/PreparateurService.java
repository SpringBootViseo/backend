package application.port;

import application.adapters.exception.UserAlreadyExistsException;
import application.domain.Preparateur;
import application.port.in.PreparateurUseCase;
import application.port.out.PreparateurPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@AllArgsConstructor
@Service
public class PreparateurService implements PreparateurUseCase {
    private final PreparateurPort preparateurPort;
    @Override
    public Preparateur addPreparateur(Preparateur preparateur) {
        if(preparateurPort.isPreparateur(preparateur.getEmail()))
            throw new UserAlreadyExistsException("preparateur alreadyExist!");


        return preparateurPort.addPreparateur(preparateur);
    }

    @Override
    public Preparateur getPreparateur(String email)
    {
        if(preparateurPort.isPreparateur(email))
        return preparateurPort.getPreparateur(email);
        else throw new NoSuchElementException("Preparateur with such mail doesn't exist!");
    }

    @Override
    public Boolean isPreparateur(String email) {
        return preparateurPort.isPreparateur(email);
    }

}
