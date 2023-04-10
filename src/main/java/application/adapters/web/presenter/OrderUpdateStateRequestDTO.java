package application.adapters.web.presenter;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class OrderUpdateStateRequestDTO {
    @Getter
    @Setter
    @NotBlank
    @JsonProperty(value = "state")
    private String idState;
}
