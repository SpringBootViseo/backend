package application.adapters.web.presenter;

import application.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;

@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {
    @Getter
    @Setter
    @Valid
    ProductDTO product;
    @Getter
    @Setter
    @Valid
    int quatity;
}
