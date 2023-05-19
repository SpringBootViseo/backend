package application.adapters.persistence.adapter;

import application.adapters.exception.UserAlreadyExistsException;
import application.adapters.mapper.mapperImpl.PreparateurMapperImpl;
import application.adapters.persistence.entity.PreparateurEntity;
import application.adapters.persistence.repository.PreparateurRepository;
import application.domain.Preparateur;
import application.port.out.PreparateurPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@AllArgsConstructor
public class PreparateurDBAdapter implements PreparateurPort {
    private PreparateurRepository preparateurRepository;
    private PreparateurMapperImpl preparateurMapper;
    @Override
    public Preparateur addPreparateur(Preparateur preparateur) {
        if(this.isPreparateur(preparateur.getEmail())){
            throw  new UserAlreadyExistsException("Preparateur already exist!");
        }
        PreparateurEntity preparateurEntity=preparateurMapper.preparateurToPreparateurEntity(preparateur);
        return preparateurMapper.preparateurEntityToPreparateur(preparateurRepository.save(preparateurEntity));
    }

    @Override
    public Preparateur getPreparateur(String email) {
        if(preparateurRepository.findById(email).isPresent()){
            return preparateurMapper.preparateurEntityToPreparateur(preparateurRepository.findById(email).get());
        }
        else throw new NoSuchElementException("Preparateur doesn't exist");

    }

    @Override
    public Boolean isPreparateur(String email) {
        return preparateurRepository.findById(email).isPresent();
    }
}
