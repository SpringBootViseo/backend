package application.adapters.web.presenter;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    @Getter
    @Setter
    @Valid
    @JsonProperty(value = "id")
    private UUID id;

    @Getter
    @Setter
    @Valid
    @JsonProperty(value = "user")
    private UserDTO user;

    @Getter
    @Setter
    @Valid
    @JsonProperty(value = "state")
    private OrderStateDTO orderState;

    @Getter
    @Setter
    @Valid
    @JsonProperty(value = "items")
    private List<OrderItemDTO> orderItems;

    @Getter
    @Setter
    @Valid
    @JsonProperty(value = "totalPrice")
    private Long totalPrice;
}
