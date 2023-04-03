package application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    @Getter
    @Setter
    Product product;
    @Getter
    @Setter
    int quatity;

}
