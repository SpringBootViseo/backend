package application.adapters.mapper.mapperImpl;

import application.adapters.mapper.PreferenceMapper;
import application.adapters.persistence.entity.PreferenceEntity;
import application.adapters.persistence.entity.ProductEntity;
import application.adapters.web.presenter.PreferenceResponseDTO;
import application.adapters.web.presenter.ProductDTO;
import application.domain.Preference;
import application.domain.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
@AllArgsConstructor
public class PreferenceMapperImpl implements PreferenceMapper {
    ProductMapperImpl productMapper;
    @Override
    public Preference preferenceEntityToPreference(PreferenceEntity preferenceEntity) {
        List<Product> preferenceList = productMapper.listProductEntityToListProduct(preferenceEntity.getPreferenceProductEntityList());
        return new Preference(preferenceEntity.getId(),preferenceList);
    }

    @Override
    public PreferenceEntity preferenceToPreferenceEntity(Preference preference) {
        List<ProductEntity> ppreferenceItemEntityList = productMapper.listProductToListProductEntity(preference.getProductList());
        return new PreferenceEntity(preference.getId(),ppreferenceItemEntityList);
    }

    @Override
    public List<Preference> ListPreferenceEntitytoListPreference(List<PreferenceEntity> preferenceEntityList) {
        List<Preference> preferenceList = new ArrayList<>();
        for(PreferenceEntity preferenceEntity:preferenceEntityList){
            preferenceList.add(this.preferenceEntityToPreference(preferenceEntity));
        }
        return preferenceList;
    }

    @Override
    public List<PreferenceEntity> listPreferenceToListPreferenceEntity(List<Preference> preferenceList) {
        List<PreferenceEntity> preferenceEntityList = new ArrayList<>();
        for(Preference preference:preferenceList){
            preferenceEntityList.add(this.preferenceToPreferenceEntity(preference));
        }
        return preferenceEntityList;
    }

    @Override
    public PreferenceResponseDTO preferenceToPresferenceResponseDTO(Preference preference) {
        List<ProductDTO> PreferenceItemtDTOList = productMapper.listProductToListProductDto(preference.getProductList());
        return new PreferenceResponseDTO(preference.getId(),PreferenceItemtDTOList);
    }

    @Override
    public Preference presferenceResponseDTOToPreference(PreferenceResponseDTO presferenceResponseDTO) {
        List<Product> preferenceItemList = productMapper.listProductDTOToListProduct(presferenceResponseDTO.getPreferenceItems());
        return new Preference(presferenceResponseDTO.getId(),preferenceItemList);
    }

    @Override
    public List<Preference> ListPreferenceResponseSTOToListPreference(List<PreferenceResponseDTO> preferenceResponseDTOList) {
        List<Preference> ListPreference = new ArrayList<>();
        for(PreferenceResponseDTO preferenceDto:preferenceResponseDTOList){
            ListPreference.add(this.presferenceResponseDTOToPreference(preferenceDto));
        }
        return ListPreference;
    }

    @Override
    public List<PreferenceResponseDTO> ListPreferenceToPreferenceResponseDTO(List<Preference> preferenceList) {
        List<PreferenceResponseDTO> preferenceResponseDTOList = new ArrayList<>();
        for(Preference preference:preferenceList) {
            preferenceResponseDTOList.add(this.preferenceToPresferenceResponseDTO(preference));
        }
        return preferenceResponseDTOList;
    }
}
