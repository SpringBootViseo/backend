package application.adapters.web.Controller;

import application.adapters.mapper.mapperImpl.CategoryMapperImpl;
import application.adapters.web.presenter.CategoryDTO;
import application.domain.Category;
import application.port.in.CategoryUseCase;
import jakarta.validation.UnexpectedTypeException;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private final static Logger logger= LogManager.getLogger(CategoryController.class);
    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@Validated @RequestBody CategoryDTO category){
        try{
            logger.trace("Map the category DTO "+category.toString()+"to category");
            logger.trace("Call the create category function from use case");
            Category categoryResponse=categoryUseCase.createCategory(categoryMapper.categoryToCategoryDto(category));
            logger.debug("Map the create category function from use case to Post mapper /categories");
            return new ResponseEntity<CategoryDTO>(categoryMapper.categoryDtoToCategory(categoryResponse),HttpStatus.OK);
        }catch (UnexpectedTypeException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        }

        catch (Exception e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }

    }
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategory(@Validated @PathVariable(name = "id") UUID id){
        try{
            logger.trace("Call the get category function from use case with "+id);

            Category category=categoryUseCase.getCategory(id);
            logger.debug("Map the get category function from use case to get mapper /categories/"+id);

            return new ResponseEntity<CategoryDTO>(categoryMapper.categoryDtoToCategory(category), HttpStatus.OK);
        }catch (UnexpectedTypeException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        }

        catch (NoSuchElementException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not found",e);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }

    }
    @GetMapping("")
    public ResponseEntity<List<CategoryDTO>> listCategories(){
        try{
            logger.trace("Call the list categories function from use case");

            List<Category> listCategories= categoryUseCase.listCategories();
            logger.debug("Map the list categories function from use case to Gett mapper /categories");

            return new ResponseEntity<List<CategoryDTO>>(categoryMapper.listCategoryToListCategoryDto(listCategories),HttpStatus.OK);
        }

        catch (Exception e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);

        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<UUID> deleteCategory(@Validated @PathVariable(name="id") UUID id){
        try{
            logger.trace("Call the delete category function from use case with "+id);

            categoryUseCase.deleteCategory(id);
            logger.debug("Map the delete category function from use case to Delete mapper /categories/"+id);

            return new ResponseEntity(id,HttpStatus.OK);
        }
        catch (Exception e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);

        }

    }
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@Validated @RequestBody CategoryDTO category, @Validated @PathVariable(name = "id") UUID id){
        try{
            logger.trace("Map the category DTO "+category.toString()+"to category");
            logger.trace("Call the update category function from use case with Category :"+category.toString()+" and id "+id);

            Category updaterCategory=categoryUseCase.updateCategory(categoryMapper.categoryToCategoryDto(category),id);
            logger.debug("Map the update category function from use case to Put mapper /categories/"+id+" and category body "+category.toString());

            return new ResponseEntity<CategoryDTO>(categoryMapper.categoryDtoToCategory(updaterCategory),HttpStatus.OK);
        }
        catch (UnexpectedTypeException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        }

        catch (NoSuchElementException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not found",e);
        }
        catch (Exception e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }

    }
}
