package application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class OrderState {
    @Getter
    @Setter
    private String id;
    @Getter
    @Setter
    private String state;
}
