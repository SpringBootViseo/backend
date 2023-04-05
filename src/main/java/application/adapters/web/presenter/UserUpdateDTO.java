package application.adapters.web.presenter;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

public class UserUpdateDTO {
    @Getter
    @Setter
    @JsonProperty("phone")
    @NotBlank
    private String phone;
    @Getter
    @Setter
    @JsonProperty("address")
    @NotBlank
    private String address;
}
