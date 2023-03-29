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
import org.checkerframework.checker.units.qual.A;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/categoryProducts")
@AllArgsConstructor
public class CategoryProductController {
    private CategoryUseCase categoryUseCase;
    private CategoryMapperImpl categoryMapper;
    private ProductUseCase productUseCase;
    private ProductMapperImpl productMapper;
    @GetMapping("/{id}")
    ResponseEntity<CategoryProductDTO> getCategoryProducts(@Validated @PathVariable(name = "id") UUID id){
        try{
            Category category=categoryUseCase.getCategory(id);
            List <Product> listProduct=productUseCase.listProducts(id);
            CategoryProductDTO categoryProductDTO= new CategoryProductDTO(categoryMapper.categoryDtoToCategory(category),productMapper.listProductToListProductDto(listProduct));
            return new ResponseEntity<CategoryProductDTO>(categoryProductDTO, HttpStatus.OK);
        }
        catch (UnexpectedTypeException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        }

        catch (NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not found",e);
        }


        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }

    }
    @GetMapping
    ResponseEntity<List<CategoryProductDTO>> listCategoryProducts() {
        try {
            List<CategoryProductDTO> response=new ArrayList<>();
            List<Category> categoryList = categoryUseCase.listCategories();
            for(Category category:categoryList){
                List<ProductDTO> productDTOList=productMapper.listProductToListProductDto(productUseCase.listProducts(category.getId()));
                response.add(new CategoryProductDTO(categoryMapper.categoryDtoToCategory(category),productDTOList));
            }

            return new ResponseEntity<List<CategoryProductDTO>>(response, HttpStatus.OK);
        } catch (UnexpectedTypeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad argument", e);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }




    }
