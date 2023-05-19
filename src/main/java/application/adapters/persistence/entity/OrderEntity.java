package application.adapters.persistence.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.List;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Orders")
public class OrderEntity {
    @Getter
    @Setter
    private UUID id;

    @Getter
    @Setter
    private UserEntity user;

    @Getter
    @Setter
    private OrderStateEntity orderState;

    @Getter
    @Setter
    private List<OrderItemEntity> orderItems;
    @Getter
    @Setter
    private Long totalPrice;
    @Getter
    @Setter
    private String dateCommande;
    @Getter
    @Setter
    private PreparateurEntity preparateur;
}
