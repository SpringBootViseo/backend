package application.port.out;

import application.domain.Manufacturer;

public interface CreateManufacturerPort {
    Manufacturer createManufacurer(Manufacturer manufacturer);
}
