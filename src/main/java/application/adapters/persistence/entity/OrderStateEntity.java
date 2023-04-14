package application.adapters.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Orderstates")
public class OrderStateEntity {
    @Getter
    @Setter
    @Id
    private String id;
    @Getter
    @Setter
    private String state;
}