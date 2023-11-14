package application.adapters.web.Controller;

import application.adapters.exception.PreferenceAlreadyExistsException;
import application.adapters.exception.PreferenceNotFoundException;
import application.adapters.mapper.mapperImpl.PreferenceMapperImpl;
import application.adapters.web.presenter.*;
import application.domain.Preference;
import application.port.in.PreferenceUseCase;
import jakarta.validation.UnexpectedTypeException;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/preference")
public class PreferenceController {
    private PreferenceUseCase preferenceUseCase;
    private PreferenceMapperImpl preferenceMapper;
    private final Logger logger= LoggerFactory.getLogger(PreferenceController.class);
    @PostMapping
    public ResponseEntity<PreferenceResponseDTO> createPreference(@Validated @RequestBody PreferenceCreateRequestDTO preferenceCreateRequestDTO){
        try{
            logger.trace("Call createPreference from Use case with id "+preferenceCreateRequestDTO.getId());
            Preference preferenceResponse=preferenceUseCase.createPrefence(preferenceCreateRequestDTO.getId());
            logger.trace("Map preference "+preferenceResponse+"to preferenceDTO");
            logger.debug("Map createpreference from Use case with Get Mapper with /preference body: "+preferenceCreateRequestDTO.toString());
            return new ResponseEntity<>(preferenceMapper.preferenceToPresferenceResponseDTO(preferenceResponse), HttpStatus.OK);
        }catch (UnexpectedTypeException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST,"Bad argument",e);
        }
        catch(PreferenceAlreadyExistsException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Preference already exists",e);
        }

        catch (Exception e) {

            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }

    @PutMapping
    public ResponseEntity<PreferenceResponseDTO> addProductToPreference(@Validated @RequestBody PreferenceUpdateRequestDTO preferenceUpdateRequestDTO){
        try{
            logger.trace("Call addProduct from Preference Use case with id Preference"+preferenceUpdateRequestDTO.getIdPreference()+" and idProduct "+preferenceUpdateRequestDTO.getIdProduct());
            Preference preferenceResponse=preferenceUseCase.addProduct(preferenceUpdateRequestDTO.getIdPreference(),preferenceUpdateRequestDTO.getIdProduct());
            logger.trace("Map preference "+preferenceResponse+"to preferenceDTO");
            logger.debug("Map addProduct from Preference Use case with Put Mapper with /preference body: "+preferenceUpdateRequestDTO.toString());

            return new ResponseEntity<>(preferenceMapper.preferenceToPresferenceResponseDTO(preferenceResponse),HttpStatus.OK);
        }
        catch (UnexpectedTypeException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        }
        catch(PreferenceNotFoundException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Preference not found",e);
        }

        catch (NoSuchElementException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found",e);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }

    @DeleteMapping
    public ResponseEntity<PreferenceResponseDTO> deleteProductFromPreference(@Validated @RequestBody PreferenceUpdateRequestDTO preferenceUpdateRequestDTO){
        try{
            logger.trace("Call deleteProductPreference from Preference Use case with id Preference"+preferenceUpdateRequestDTO.getIdPreference()+" and idProduct "+preferenceUpdateRequestDTO.getIdProduct());
            Preference preferenceResponse=preferenceUseCase.deleteProductFromPreference(preferenceUpdateRequestDTO.getIdPreference(),preferenceUpdateRequestDTO.getIdProduct());
            logger.trace("Map preference "+preferenceResponse+"to preferenceDTO");
            logger.debug("Map deleteProductPreference from Preference Use case with Delete Mapper with /preference body: "+preferenceUpdateRequestDTO.toString());

            return new ResponseEntity<>(preferenceMapper.preferenceToPresferenceResponseDTO(preferenceResponse),HttpStatus.OK);
        }
        catch (UnexpectedTypeException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        }
        catch(PreferenceNotFoundException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Preference not found",e);
        }

        catch (NoSuchElementException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found",e);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PreferenceResponseDTO> getCart(@Validated @PathVariable(name = "id") String id){
        try{
            logger.trace("Call getPreference from Preference Use case with id Preference "+id);

            Preference preferenceResponse=preferenceUseCase.getPreference(id);
            logger.trace("Map preference "+preferenceResponse+"to preferenceDTO");
            logger.debug("Map getPreference from Preference Use case with Get Mapper with /preference/ "+id);
            return new ResponseEntity<>(preferenceMapper.preferenceToPresferenceResponseDTO(preferenceResponse),HttpStatus.OK);
        }
        catch (UnexpectedTypeException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        }
        catch(PreferenceNotFoundException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"preference not found",e);
        }

        catch (NoSuchElementException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found",e);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }

}