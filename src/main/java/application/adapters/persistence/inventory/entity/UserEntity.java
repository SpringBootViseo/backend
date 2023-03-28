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
@Document(collection = "Users")
public class UserEntity {
    @Getter
    @Setter
    @Id
    private String id;
    @Getter
    @Setter
    private String fullname;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String numberPhone;

}