package application.adapters.web.presenter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class ReductionProductRequest {
    @Getter
    @Setter
    @Valid
    @JsonProperty(value = "product")
    UUID id;
    @Getter
    @Setter
    @Valid
    @JsonProperty(value = "reduction")
    Double reduction;

}