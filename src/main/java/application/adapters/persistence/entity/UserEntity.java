package application.adapters.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

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
    @Getter
    @Setter
    private String picture;
    @Getter
    @Setter
    private List<AddressEntity> address;
    @Getter
    @Setter
    private int avertissement;
    @Getter
    @Setter
    private boolean blackListed;

}