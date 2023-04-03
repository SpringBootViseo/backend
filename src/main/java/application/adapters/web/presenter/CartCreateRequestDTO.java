package application.adapters.web.presenter;

import application.domain.CartItem;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
public class CartCreateRequestDTO {
    @Getter
    @Setter
    @NotBlank
    private String id;

}
