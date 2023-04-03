package application.adapters.persistence.entity;

import application.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
public class CartItemEntity {
    @Getter
    @Setter
    Product product;
    @Getter
    @Setter
    int quatity;
}
