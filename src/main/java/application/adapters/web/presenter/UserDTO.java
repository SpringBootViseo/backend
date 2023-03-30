package application.adapters.web.presenter;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @Getter
    @Setter
    @JsonProperty("id")
    @NotBlank
    @Valid
    @Schema(name = "id", example = "7dogOFiqeWMDCXpbgINjz0iiMSr1", required = true)
    private String id;
    @Getter
    @Setter
    @JsonProperty("name")
    @NotBlank
    @Schema(name = "name", example = "Otman Otman", required = true)
    private String name;
    @Getter
    @Setter
    @JsonProperty("email")
    @NotBlank
    @Email
    private String email;
    @Getter
    @Setter
    @JsonProperty("phone")
    @Valid
    @Schema(name = "name", example = "066666666666")
    private String phone;
}
