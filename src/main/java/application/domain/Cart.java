package application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @Getter
    @Setter
    private String id;
    @Getter
    @Setter
    private List<Product> productList;



}
