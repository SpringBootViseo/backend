package application.port.in;

import application.domain.Manufacturer;

public interface CreateManufacturerUseCase {
    Manufacturer createManufacurer(Manufacturer manufacturer);
}
