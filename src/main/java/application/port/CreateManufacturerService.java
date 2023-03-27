package application.port;

import application.domain.Manufacturer;
import application.port.in.CreateManufacturerUseCase;
import application.port.out.CreateManufacturerPort;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateManufacturerService implements CreateManufacturerUseCase {
    @Autowired
    private final CreateManufacturerPort createmanufacturerPort;

    @Override
    public Manufacturer createManufacurer(Manufacturer manufacturer) {
        return createmanufacturerPort.createManufacurer(manufacturer);
    }
}
