package application.adapters.persistence.adapter;

import application.adapters.persistence.entity.ManufacturerEntity;
import application.adapters.persistence.repository.ManufacturerRepository;
import application.domain.Manufacturer;
import application.port.out.CreateManufacturerPort;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ManufacturerDBAdapter implements CreateManufacturerPort {

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    public ManufacturerDBAdapter() {

    }

    @Override
    public Manufacturer createManufacurer(Manufacturer manufacturer) {
        ManufacturerEntity manufacturerEntity = new ManufacturerEntity(manufacturer.getId(),manufacturer.getName(),manufacturer.getPhone(),manufacturer.getHomePage());
        ManufacturerEntity manufacturerEntitySaved = manufacturerRepository.save(manufacturerEntity);
        return new Manufacturer(manufacturerEntitySaved.getId(),manufacturerEntitySaved.getName(),manufacturerEntitySaved.getPhone(),manufacturerEntitySaved.getHomePage());
    }
}
