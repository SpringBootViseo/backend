package application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Getter
    @Setter
    private UUID id;

    @Getter
    @Setter
    private User user;

    @Getter
    @Setter
    private OrderState orderState;

    @Getter
    @Setter
    private List<OrderItem> orderItems;

    @Getter
    @Setter
    private Double totalPrice;
    @Getter
    @Setter
    private String  dateCommande;
    @Getter
    @Setter
    private Preparateur preparateur;

}
