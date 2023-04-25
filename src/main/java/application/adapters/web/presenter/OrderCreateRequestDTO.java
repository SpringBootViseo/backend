package application.adapters.web.presenter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

public class OrderCreateRequestDTO {
    @Getter
    @Setter
    @JsonProperty(value = "id")
    private UUID id = UUID.randomUUID();

    @Getter
    @Setter
    @Valid
    @JsonProperty(value = "user")
    private String idUser;

    @Getter
    @Setter
    @Valid
    @JsonProperty(value = "state")
    private String idState;

    @Getter
    @Setter
    @Valid
    @JsonProperty(value = "items")
    private List<OrderItemCreateRequestDTO> orderItems;
    @Getter
    @Setter
    @Valid
    @JsonProperty(value = "totalPrice")
    private Long totalPrice;
    @Getter
    @Setter
    @Valid
    @JsonProperty(value = "dateCommande")
    private String dateCommande;

}
