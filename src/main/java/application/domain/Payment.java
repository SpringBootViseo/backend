package application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Getter
    @Setter
    private UUID id;
    @Getter
    @Setter
    private Double totalPrice;
    @Getter
    @Setter
    private User user;
    @Getter
    @Setter
    private Livreur livreur;



}
