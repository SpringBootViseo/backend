package application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Getter
    @Setter
    private UUID id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String linkImg;

    @Getter
    @Setter
    private String linkImgBanner;
}
