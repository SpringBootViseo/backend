package application.adapters.web.presenter;


import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.Valid;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import java.util.UUID;

public class OrderItemCreateRequestDTO {
    @Getter
    @Setter
    @Valid
    @JsonProperty(value = "product")
    UUID id;
    @Getter
    @Setter
    @Min(value = 1,message = "greater than or equal 1")
    @Valid
    @JsonProperty(value = "quantity")

    int quantity;

}
