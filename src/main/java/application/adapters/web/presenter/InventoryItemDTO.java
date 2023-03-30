package application.adapters.web.presenter;

import java.net.URI;
import java.util.Objects;
import application.adapters.web.presenter.ManufacturerDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Date;
import java.util.UUID;
import org.springframework.format.annotation.DateTimeFormat;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * InventoryItemDTO
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-03-29T19:36:58.928259300Z[Africa/Casablanca]")
public class InventoryItemDTO {

  @JsonProperty("id")
  private UUID id;

  @JsonProperty("name")
  private String name;

  @JsonProperty("releaseDate")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private Date releaseDate;

  @JsonProperty("manufacturer")
  private ManufacturerDTO manufacturer;

  public InventoryItemDTO id(UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  @NotNull @Valid 
  @Schema(name = "id", example = "d290f1ee-6c54-4b01-90e6-d701748f0851", required = true)
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public InventoryItemDTO name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  */
  @NotNull 
  @Schema(name = "name", example = "Widget Adapter", required = true)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public InventoryItemDTO releaseDate(Date releaseDate) {
    this.releaseDate = releaseDate;
    return this;
  }

  /**
   * Get releaseDate
   * @return releaseDate
  */
  @NotNull @Valid 
  @Schema(name = "releaseDate", example = "2016-08-29T09:12:33.001Z", required = true)
  public Date getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(Date releaseDate) {
    this.releaseDate = releaseDate;
  }

  public InventoryItemDTO manufacturer(ManufacturerDTO manufacturer) {
    this.manufacturer = manufacturer;
    return this;
  }

  /**
   * Get manufacturer
   * @return manufacturer
  */
  @NotNull @Valid 
  @Schema(name = "manufacturer", required = true)
  public ManufacturerDTO getManufacturer() {
    return manufacturer;
  }

  public void setManufacturer(ManufacturerDTO manufacturer) {
    this.manufacturer = manufacturer;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InventoryItemDTO inventoryItemDTO = (InventoryItemDTO) o;
    return Objects.equals(this.id, inventoryItemDTO.id) &&
        Objects.equals(this.name, inventoryItemDTO.name) &&
        Objects.equals(this.releaseDate, inventoryItemDTO.releaseDate) &&
        Objects.equals(this.manufacturer, inventoryItemDTO.manufacturer);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, releaseDate, manufacturer);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InventoryItemDTO {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    releaseDate: ").append(toIndentedString(releaseDate)).append("\n");
    sb.append("    manufacturer: ").append(toIndentedString(manufacturer)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

