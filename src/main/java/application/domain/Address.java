package application.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Getter
    @Setter
    private UUID id;
    @Getter
    @Setter
    @JsonProperty("quartier")
    private String street;
    @Getter
    @Setter
    @JsonProperty("ville")
    private String city;
    @Getter
    @Setter
    @JsonProperty("province")
    private String state;

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Address otherAddress = (Address) obj;
        return Objects.equals(this.street, otherAddress.street) &&
                Objects.equals(this.city, otherAddress.city) &&
                Objects.equals(this.state, otherAddress.state);
    }

}
