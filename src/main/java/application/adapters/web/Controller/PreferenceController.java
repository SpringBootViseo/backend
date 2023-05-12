package application.adapters.web.Controller;

import application.adapters.exception.PreferenceAlreadyExistsException;
import application.adapters.exception.PreferenceNotFoundException;
import application.adapters.mapper.mapperImpl.PreferenceMapperImpl;
import application.adapters.web.presenter.*;
import application.domain.Preference;
import application.port.in.PreferenceUseCase;
import jakarta.validation.UnexpectedTypeException;
import lombok.AllArgsConstructor;
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
    @PostMapping
    public ResponseEntity<PreferenceResponseDTO> createPreference(@Validated @RequestBody PreferenceCreateRequestDTO preferenceCreateRequestDTO){
        try{
            Preference preferenceResponse=preferenceUseCase.createPrefence(preferenceCreateRequestDTO.getId());
            return new ResponseEntity<>(preferenceMapper.preferenceToPresferenceResponseDTO(preferenceResponse), HttpStatus.OK);
        }catch (UnexpectedTypeException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST,"Bad argument",e);
        }
        catch(PreferenceAlreadyExistsException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Preference already exists",e);
        }

        catch (Exception e) {

            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }

    @PutMapping
    public ResponseEntity<PreferenceResponseDTO> addProductToPreference(@Validated @RequestBody PreferenceUpdateRequestDTO preferenceUpdateRequestDTO){
        try{
            Preference preferenceResponse=preferenceUseCase.addProduct(preferenceUpdateRequestDTO.getIdPreference(),preferenceUpdateRequestDTO.getIdProduct());
            return new ResponseEntity<>(preferenceMapper.preferenceToPresferenceResponseDTO(preferenceResponse),HttpStatus.OK);
        }
        catch (UnexpectedTypeException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        }
        catch(PreferenceNotFoundException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Preference not found",e);
        }

        catch (NoSuchElementException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found",e);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }

    @DeleteMapping
    public ResponseEntity<PreferenceResponseDTO> deleteProductFromPreference(@Validated @RequestBody PreferenceUpdateRequestDTO preferenceUpdateRequestDTO){
        try{
            Preference preferenceResponse=preferenceUseCase.deleteProductFromPreference(preferenceUpdateRequestDTO.getIdPreference(),preferenceUpdateRequestDTO.getIdProduct());
            return new ResponseEntity<>(preferenceMapper.preferenceToPresferenceResponseDTO(preferenceResponse),HttpStatus.OK);
        }
        catch (UnexpectedTypeException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        }
        catch(PreferenceNotFoundException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Preference not found",e);
        }

        catch (NoSuchElementException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found",e);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PreferenceResponseDTO> getCart(@Validated @PathVariable(name = "id") String id){
        try{
            Preference preferenceResponse=preferenceUseCase.getPreference(id);
            return new ResponseEntity<>(preferenceMapper.preferenceToPresferenceResponseDTO(preferenceResponse),HttpStatus.OK);
        }
        catch (UnexpectedTypeException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        }
        catch(PreferenceNotFoundException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"preference not found",e);
        }

        catch (NoSuchElementException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found",e);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }

}
