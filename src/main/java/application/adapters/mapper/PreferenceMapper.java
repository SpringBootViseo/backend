package application.adapters.mapper;

import application.adapters.persistence.entity.PreferenceEntity;
import application.adapters.web.presenter.PreferenceResponseDTO;
import application.domain.Preference;

import java.util.List;

public interface PreferenceMapper {
    Preference preferenceEntityToPreference(PreferenceEntity preferenceEntity);
    PreferenceEntity preferenceToPreferenceEntity(Preference preference);
    List<Preference> ListPreferenceEntitytoListPreference(List<PreferenceEntity> preferenceEntityList);
    List<PreferenceEntity> listPreferenceToListPreferenceEntity(List<Preference> preferenceList);
    PreferenceResponseDTO preferenceToPresferenceResponseDTO(Preference preference);

    Preference presferenceResponseDTOToPreference(PreferenceResponseDTO presferenceResponseDTO);
    List<Preference> ListPreferenceResponseSTOToListPreference(List<PreferenceResponseDTO> preferenceResponseDTOList);
    List<PreferenceResponseDTO> ListPreferenceToPreferenceResponseDTO(List<Preference> preferenceList);

}
