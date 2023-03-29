package application.adapters.web.presenter;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
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
    @JsonProperty("image")
    @NotBlank
    private String linkImg;

    @Getter
    @Setter
    @JsonProperty("bannerImage")

    @NotBlank
    private String linkImgBanner;
}
