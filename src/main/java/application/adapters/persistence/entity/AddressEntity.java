package application.adapters.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class AddressEntity {
    @Getter
    @Setter
    private UUID id;
    @Getter
    @Setter
    private String street;
    @Getter
    @Setter
    private String city;
    @Getter
    @Setter
    private String state;
}
