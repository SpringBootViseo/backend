package application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Getter
    @Setter
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
    private int storedQuantity;

    @Getter
    @Setter
    private int orderedQuantity;
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
    private Category category;
}
