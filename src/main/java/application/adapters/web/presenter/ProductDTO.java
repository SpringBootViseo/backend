package application.adapters.web.presenter;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    @Getter
    @Setter
    @JsonProperty("id")
    @Valid
    private UUID id;
    @Getter
    @Setter
    @JsonProperty("name")
    @NotBlank
    private String name;
    @Getter
    @Setter
    @JsonProperty("marque")
    @NotBlank
    private String marque;
    @Getter
    @Setter
    @JsonProperty("image")
    @NotBlank
    private String linkImg;
    @Getter
    @Setter
    @JsonProperty("description")
    @NotBlank
    private String description;
    @Getter
    @Setter
    @JsonProperty("quantity")
    @Valid
    private int storedQuantity;

    @Getter
    @Setter
    @JsonProperty("Ordredquantity")
    @Valid
    private int orderedQuantity;
    @Getter
    @Setter
    @JsonProperty("images")
    @Valid
    private List<String> Images;
    @Getter
    @Setter
    @JsonProperty("unitQuantity")
    @Valid
    private String unitQuantity;
    @Getter
    @Setter
    @JsonProperty("reduction")
    private Double reductionPercentage;
    @Getter
    @Setter
    @JsonProperty("previousPrice")
    private Double previousPrice;
    @Getter
    @Setter
    @JsonProperty("price")
    @Valid
    private Double currentPrice;
    @Getter
    @Setter
    @JsonProperty("category")
    @Valid
    private CategoryDTO category;
}
