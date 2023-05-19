package application.adapters.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Payments")
public class PaymentEntity {
    @Getter
    @Setter
    @Id
    private UUID id;
    @Getter
    @Setter
    private Long totalPrice;
    @Getter
    @Setter
    private UserEntity user;
}
