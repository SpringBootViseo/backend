package application.adapters.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Document(collection = "Products")
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {
    @Getter
    @Setter
    @Id
    private UUID id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String marque;
    @Getter
    @Setter
    private String linkImg;
    @Getter
    @Setter
    private String description;
    @Getter
    @Setter
    private int quantity;
    @Getter
    @Setter
    private List<String> Images;
    @Getter
    @Setter
    private String unitQuantity;
    @Getter

    @Setter
    private long reductionPercentage;
    @Getter
    @Setter
    private long previousPrice;
    @Getter
    @Setter
    private long currentPrice;
    @Getter
    @Setter
    private CategoryEntity category;
}
