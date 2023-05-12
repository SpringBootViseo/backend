package application.adapters.web.presenter;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class PreferenceCreateRequestDTO {
    @Getter
    @Setter
    @NotBlank
    private String id;
}
