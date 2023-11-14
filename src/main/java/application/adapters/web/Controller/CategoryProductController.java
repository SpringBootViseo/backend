package application.adapters.web.Controller;

import application.adapters.mapper.mapperImpl.CategoryMapperImpl;
import application.adapters.mapper.mapperImpl.ProductMapperImpl;
import application.adapters.web.presenter.CategoryProductDTO;
import application.adapters.web.presenter.ProductDTO;
import application.domain.Category;
import application.domain.Product;
import application.port.in.CategoryUseCase;
import application.port.in.ProductUseCase;
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

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")

@RequestMapping("/categoryProducts")
@AllArgsConstructor
public class CategoryProductController {
    private CategoryUseCase categoryUseCase;
    private CategoryMapperImpl categoryMapper;
    private ProductUseCase productUseCase;
    private ProductMapperImpl productMapper;
    private final Logger logger= LoggerFactory.getLogger(CategoryProductController.class);
    @GetMapping("/{id}")
    ResponseEntity<CategoryProductDTO> getCategoryProducts(@Validated @PathVariable(name = "id") UUID id){
        try{
            logger.trace("Call the get category from category use case of id "+ id);
            Category category=categoryUseCase.getCategory(id);
            logger.trace("Call the listProducts from product use case of category id "+ id);
            List <Product> listProduct=productUseCase.listProducts(id);
            logger.trace("Construct categoryProduct DTO");
            CategoryProductDTO categoryProductDTO= new CategoryProductDTO(categoryMapper.categoryDtoToCategory(category),productMapper.listProductToListProductDto(listProduct));
            logger.debug("Map the get category Products function from use case to gett mapper /categoryProducts/"+id);

            return new ResponseEntity<CategoryProductDTO>(categoryProductDTO, HttpStatus.OK);
        }
        catch (UnexpectedTypeException e){
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
    @GetMapping
    ResponseEntity<List<CategoryProductDTO>> listCategoryProducts() {
        try {
            logger.trace("Instantiate CategoryProductDTO list");
            List<CategoryProductDTO> response=new ArrayList<>();
            logger.trace("list all categories");
            List<Category> categoryList = categoryUseCase.listCategories();
            for(Category category:categoryList){
                List<ProductDTO> productDTOList=productMapper.listProductToListProductDto(productUseCase.listProducts(category.getId()));
                response.add(new CategoryProductDTO(categoryMapper.categoryDtoToCategory(category),productDTOList));
            }
            logger.debug("Map the get category Products function from use case to get mapper /categoryProducts/");


            return new ResponseEntity<List<CategoryProductDTO>>(response, HttpStatus.OK);
        } catch (UnexpectedTypeException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad argument", e);
        } catch (NoSuchElementException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found", e);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }




}