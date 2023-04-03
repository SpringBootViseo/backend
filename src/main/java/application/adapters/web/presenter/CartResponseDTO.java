package application.adapters.web.presenter;

import application.domain.CartItem;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class CartResponseDTO {
    @Getter
    @Setter
    @NotBlank
    private String id;
    @Getter
    @Setter
    @Valid
    private List<ProductDTO> cartItems;
}
