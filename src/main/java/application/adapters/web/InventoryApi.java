/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (6.2.1).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package application.adapters.web;

import application.adapters.web.presenter.InventoryItemDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-03-29T19:36:58.928259300Z[Africa/Casablanca]")
@Validated
@Tag(name = "inventory", description = "Secured Admin-only calls")
public interface InventoryApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /inventory : adds an inventory item
     * Adds an item to the system
     *
     * @param inventoryItemDTO Inventory item to add (optional)
     * @return item created (status code 201)
     *         or invalid input, object invalid (status code 400)
     *         or an existing item already exists (status code 409)
     */
    @Operation(
        operationId = "addInventory",
        summary = "adds an inventory item",
        tags = { "admins" },
        responses = {
            @ApiResponse(responseCode = "201", description = "item created"),
            @ApiResponse(responseCode = "400", description = "invalid input, object invalid"),
            @ApiResponse(responseCode = "409", description = "an existing item already exists")
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/inventory",
        consumes = { "application/json" }
    )
    default ResponseEntity<Void> addInventory(
        @Parameter(name = "InventoryItemDTO", description = "Inventory item to add") @Valid @RequestBody(required = false) InventoryItemDTO inventoryItemDTO
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /inventory : searches inventory
     * By passing in the appropriate options, you can search for available inventory in the system 
     *
     * @param searchString pass an optional search string for looking up inventory (optional)
     * @param skip number of records to skip for pagination (optional)
     * @param limit maximum number of records to return (optional)
     * @return search results matching criteria (status code 200)
     *         or bad input parameter (status code 400)
     */
    @Operation(
        operationId = "searchInventory",
        summary = "searches inventory",
        tags = { "developers" },
        responses = {
            @ApiResponse(responseCode = "200", description = "search results matching criteria", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = InventoryItemDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "bad input parameter")
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/inventory",
        produces = { "application/json" }
    )
    default ResponseEntity<List<InventoryItemDTO>> searchInventory(
        @Parameter(name = "searchString", description = "pass an optional search string for looking up inventory") @Valid @RequestParam(value = "searchString", required = false) String searchString,
        @Min(0) @Parameter(name = "skip", description = "number of records to skip for pagination") @Valid @RequestParam(value = "skip", required = false) Integer skip,
        @Min(0) @Max(50) @Parameter(name = "limit", description = "maximum number of records to return") @Valid @RequestParam(value = "limit", required = false) Integer limit
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"releaseDate\" : \"2016-08-29T09:12:33.001Z\", \"name\" : \"Widget Adapter\", \"id\" : \"d290f1ee-6c54-4b01-90e6-d701748f0851\", \"manufacturer\" : { \"phone\" : \"408-867-5309\", \"name\" : \"ACME Corporation\", \"id\" : \"d290f1ee-6c54-4b01-90e6-d701748f0851\", \"homePage\" : \"https://www.acme-corp.com\" } }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
