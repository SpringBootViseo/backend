package application.adapters.web.presenter;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor

public class CartUpdateRequestDTO {
    @Getter
    @Setter
    @NotBlank
    @JsonProperty(value = "id")
    String idCart;
    @Getter
    @Setter
    @Valid
    @JsonProperty(value = "idProduct")
    UUID idProduct;

}
