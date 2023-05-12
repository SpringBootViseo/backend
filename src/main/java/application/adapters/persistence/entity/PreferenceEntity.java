package application.adapters.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="PrferenceProducts")
public class PreferenceEntity {
    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private List<ProductEntity> preferenceProductEntityList ;


}
