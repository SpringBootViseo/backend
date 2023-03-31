package application.adapters.web.presenter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class CategoryProductDTO {
    @Getter
    @Setter
    @JsonProperty("category")
    @Valid
    private CategoryDTO categoryDTO;
    @Getter
    @Setter
    @JsonProperty("products")
    @Valid
    private List<ProductDTO> productDTOList;

}
