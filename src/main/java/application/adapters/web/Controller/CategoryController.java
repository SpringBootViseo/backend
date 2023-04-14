package application.adapters.web.Controller;

import application.adapters.mapper.mapperImpl.CategoryMapperImpl;
import application.adapters.web.presenter.CategoryDTO;
import application.domain.Category;
import application.port.in.CategoryUseCase;
import jakarta.validation.UnexpectedTypeException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryController {

    private CategoryUseCase categoryUseCase;
    private CategoryMapperImpl categoryMapper;
    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@Validated @RequestBody CategoryDTO category){
        try{
            Category categoryResponse=categoryUseCase.createCategory(categoryMapper.categoryToCategoryDto(category));
            return new ResponseEntity<CategoryDTO>(categoryMapper.categoryDtoToCategory(categoryResponse),HttpStatus.OK);
        }catch (UnexpectedTypeException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        }

        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }

    }
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategory(@Validated @PathVariable(name = "id") UUID id){
        try{
            Category category=categoryUseCase.getCategory(id);
            return new ResponseEntity<CategoryDTO>(categoryMapper.categoryDtoToCategory(category), HttpStatus.OK);
        }catch (UnexpectedTypeException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        }

        catch (NoSuchElementException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not found",e);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }

    }
    @GetMapping("")
    public ResponseEntity<List<CategoryDTO>> listCategories(){
        try{
            List<Category> listCategories= categoryUseCase.listCategories();
            return new ResponseEntity<List<CategoryDTO>>(categoryMapper.listCategoryToListCategoryDto(listCategories),HttpStatus.OK);
        }

        catch (Exception e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);

        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@Validated @RequestBody CategoryDTO category, @Validated @PathVariable(name = "id") UUID id){
        try{
            Category updaterCategory=categoryUseCase.updateCategory(categoryMapper.categoryToCategoryDto(category),id);
            return new ResponseEntity<CategoryDTO>(categoryMapper.categoryDtoToCategory(updaterCategory),HttpStatus.OK);
        }
        catch (UnexpectedTypeException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        }

        catch (NoSuchElementException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not found",e);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }

    }
}
