package application.adapters.persistence.adapter;

import application.adapters.exception.PreferenceAlreadyExistsException;
import application.adapters.exception.PreferenceNotFoundException;
import application.adapters.mapper.mapperImpl.PreferenceMapperImpl;
import application.adapters.persistence.entity.PreferenceEntity;
import application.adapters.persistence.repository.PreferenceRepository;
import application.domain.Preference;
import application.domain.Product;
import application.port.out.PreferencePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class PreferenceDBAdapter implements PreferencePort {
    private PreferenceRepository preferenceRepository;
    private PreferenceMapperImpl preferenceMapper;
    @Override
    public Preference createPrefence(String idPreference) {
        if(preferenceRepository.findById(idPreference).isPresent()){
            throw new PreferenceAlreadyExistsException("Preference already exists!");
        }
        else{
            PreferenceEntity preferenceEntity = preferenceRepository.save(new PreferenceEntity(idPreference,new ArrayList<>()));
            return preferenceMapper.preferenceEntityToPreference(preferenceEntity);
        }
    }

    @Override
    public Preference addProduct(Preference preference, Product product) {
        List<Product> preferenceItemList = preference.getProductList();
        boolean containsProduct = preferenceItemList.stream().anyMatch(preferenceItem ->preferenceItem.getId().equals(product.getId()));
        if(!containsProduct) {
            preferenceItemList.add(product);
        }
        preference.setProductList(preferenceItemList);

        PreferenceEntity savedPreference = preferenceRepository.save(preferenceMapper.preferenceToPreferenceEntity(preference));
        return preferenceMapper.preferenceEntityToPreference(savedPreference);
    }

    @Override
    public Preference deleteProductFromPreference(Preference preference, Product product) {
        List<Product> preferenceItemEntityList = preference.getProductList().stream().filter(product1 -> !product1.getId().equals(product.getId())).collect(Collectors.toList());
        preference.setProductList(preferenceItemEntityList);
        PreferenceEntity savedPreference = preferenceRepository.save(preferenceMapper.preferenceToPreferenceEntity(preference));
        return preferenceMapper.preferenceEntityToPreference(savedPreference);
    }

    @Override
    public Preference getPreference(String idPreference) {
        if(preferenceRepository.findById(idPreference).isPresent()){
            PreferenceEntity preference = preferenceRepository.findById(idPreference).get();
            return preferenceMapper.preferenceEntityToPreference(preference);
        }
        else throw new PreferenceNotFoundException(idPreference);
    }

    @Override
    public Boolean availablePreference(String idPreference) {
        return preferenceRepository.findById(idPreference).isPresent();
    }
}
