package adapters.persistence.inventory.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

@Document(collection="items")
public class InventoryItemEntity {
    private UUID id;

    private String name;

    private Date releaseDate;

    private ManufacturerEntity manufacturer;

}
