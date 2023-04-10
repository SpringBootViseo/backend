package application.adapters.web.presenter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {
    @Getter
    @Setter
    @Valid
    @JsonProperty(value = "product")
    ProductDTO product;
    @Getter
    @Setter
    @Valid
    @Min(value = 1,message = "greater than 1")

    @JsonProperty(value = "quantity")
    int quantity;
}
