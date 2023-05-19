package application.adapters.web.presenter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    @Getter
    @Setter
    private UUID id;
    @Getter
    @Setter
    private Long totalPrice;
    @Getter
    @Setter
    private UserDTO user;
}
