package application.adapters.persistence.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor

@Document(collection = "Manufacturer")
public class ManufacturerEntity {
    @Getter
    @Setter
    @Id
    private UUID id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String homePage;

    @Getter
    @Setter
    private String phone;

}
