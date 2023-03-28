package application.adapters.persistence.inventory.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Categories")
public class CategoryEntity {
    @Getter
    @Setter
    @Id
    private UUID id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String linkImg;

    @Getter
    @Setter
    private String linkImgBanner;
}
