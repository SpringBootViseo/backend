package application.adapters.web;

import application.domain.Manufacturer;
import application.port.in.CreateManufacturerUseCase;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manufacturers")
@AllArgsConstructor
public class ManufacturerController {
    @Autowired
    private final CreateManufacturerUseCase createManufacturerUseCase;
    @PostMapping("")
    public Manufacturer createManufacturer(@RequestBody Manufacturer manufacturer){
        System.out.println("test test");
        return createManufacturerUseCase.createManufacurer(manufacturer);
    }

}
