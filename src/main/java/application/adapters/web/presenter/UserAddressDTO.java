package application.adapters.web.presenter;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import java.util.UUID;

public class UserAddressDTO {
    @Getter
    @Setter
    @JsonProperty("id")
    @Valid
    private UUID id;
    @Getter
    @Setter
    @JsonProperty("quartier")
    @NotBlank
    private String street;
    @Getter
    @Setter
    @JsonProperty("ville")
    @NotBlank
    private String city;
    @Getter
    @Setter
    @JsonProperty("province")
    @NotBlank
    private String state;

}
