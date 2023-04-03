package application.adapters.persistence.entity;

import application.domain.CartItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Carts")
public class CartEntity {
    @Getter
    @Setter
    @Id
    private String id;
    @Getter
    @Setter
    private List<ProductEntity> productEntityList;
}
