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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class PreferenceDBAdapter implements PreferencePort {
    private final PreferenceRepository preferenceRepository;
    private final PreferenceMapperImpl preferenceMapper;
    private static final Logger logger = LogManager.getLogger(PreferenceDBAdapter.class);

    @Override
    public Preference createPrefence(String idPreference) {
        logger.trace("Cheking if Preference with id : {} exist",idPreference);
        if (preferenceRepository.findById(idPreference).isPresent()) {
            logger.error("Preference already exists with ID: {}", idPreference);
            throw new PreferenceAlreadyExistsException("Preference already exists!");
        } else {
            logger.debug("Creating a new preference with ID: {}", idPreference);
            PreferenceEntity preferenceEntity = preferenceRepository.save(new PreferenceEntity(idPreference, new ArrayList<>()));
            logger.info("Created new preference with ID: {}", idPreference);
            return preferenceMapper.preferenceEntityToPreference(preferenceEntity);
        }
    }

    @Override
    public Preference addProduct(Preference preference, Product product) {
        List<Product> preferenceItemList = preference.getProductList();
        boolean containsProduct = preferenceItemList.stream().anyMatch(preferenceItem -> preferenceItem.getId().equals(product.getId()));

        if (!containsProduct) {
            preferenceItemList.add(product);
            preference.setProductList(preferenceItemList);

            logger.debug("Added product with ID {} to preference with ID: {}", product.getId(), preference.getId());

            PreferenceEntity savedPreference = preferenceRepository.save(preferenceMapper.preferenceToPreferenceEntity(preference));

            logger.info("Preference with ID {} has been updated after adding product with ID: {}", preference.getId(), product.getId());

            return preferenceMapper.preferenceEntityToPreference(savedPreference);
        }

        logger.debug("Product with ID {} already exists in preference with ID: {}. No action taken.", product.getId(), preference.getId());

        return preference;
    }


    @Override
    public Preference deleteProductFromPreference(Preference preference, Product product) {
        List<Product> preferenceItemEntityList = preference.getProductList().stream()
                .filter(product1 -> !product1.getId().equals(product.getId()))
                .collect(Collectors.toList());

        logger.debug("Removing product with ID {} from preference with ID: {}", product.getId(), preference.getId());

        preference.setProductList(preferenceItemEntityList);

        PreferenceEntity savedPreference = preferenceRepository.save(preferenceMapper.preferenceToPreferenceEntity(preference));

        logger.info("Preference with ID {} has been updated after removing product with ID: {}", preference.getId(), product.getId());

        return preferenceMapper.preferenceEntityToPreference(savedPreference);
    }


    @Override
    public Preference getPreference(String idPreference) {
        logger.trace("Checking if Preference with ID: {} exists", idPreference);

        if (preferenceRepository.findById(idPreference).isPresent()) {
            PreferenceEntity preference = preferenceRepository.findById(idPreference).get();
            logger.info("Retrieved preference with ID: {}", idPreference);
            return preferenceMapper.preferenceEntityToPreference(preference);
        } else {
            logger.error("Preference not found with ID: {}", idPreference);
            throw new PreferenceNotFoundException(idPreference);
        }
    }


    @Override
    public Boolean availablePreference(String idPreference) {
        boolean available = preferenceRepository.findById(idPreference).isPresent();
        logger.trace("Cheking if Preference with id : {} exist",idPreference);
        if (available) {
            logger.info("Preference with ID {} is available", idPreference);
        } else {
            logger.info("Preference with ID {} is not available", idPreference);
        }
        return available;
    }
}