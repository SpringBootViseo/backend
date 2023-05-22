package application.adapters.persistence.adapter;

import application.adapters.exception.UserAlreadyExistsException;
import application.adapters.mapper.mapperImpl.LivreurMapperImpl;
import application.adapters.persistence.entity.LivreurEntity;
import application.adapters.persistence.repository.LivreurRepository;
import application.domain.Livreur;
import application.port.out.LivreurPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@AllArgsConstructor
public class LivreurDBAdapter implements LivreurPort {
    private LivreurRepository livreurRepository;
    private LivreurMapperImpl livreurMapper;
    @Override
    public Livreur addLivreur(Livreur livreur) {
        if(this.isLivreur(livreur.getEmail())){
            throw new UserAlreadyExistsException("Livreur already Exist!");

        }
        LivreurEntity livreurEntity=livreurMapper.livreurToLivreurEntity(livreur);
        return livreurMapper.livreurEntityToLivreur(livreurRepository.save(livreurEntity));
    }

    @Override
    public Livreur getLivreur(String email) {
        if(livreurRepository.findById(email).isPresent()){
            return livreurMapper.livreurEntityToLivreur(livreurRepository.findById(email).get());
        }
        else throw new NoSuchElementException("Preparateur doesn't exist");
    }

    @Override
    public Boolean isLivreur(String email) {
        return livreurRepository.findById(email).isPresent();
    }
}
