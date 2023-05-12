package application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class Preference {
    @Getter
    @Setter
    private String id ;
    @Getter
    @Setter
    private List<Product> productList ;
}
