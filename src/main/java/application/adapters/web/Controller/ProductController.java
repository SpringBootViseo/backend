package application.adapters.web.Controller;

import application.adapters.mapper.mapperImpl.ProductMapperImpl;
import application.adapters.web.presenter.ProductDTO;
import application.domain.Product;
import application.port.in.ProductUseCase;
import jakarta.validation.UnexpectedTypeException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;


import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private ProductUseCase productUseCase;
    private ProductMapperImpl productMapper;

    @PostMapping
    ResponseEntity<ProductDTO> createProduct(@Validated @RequestBody ProductDTO product){
        try{
            Product productResponse=productUseCase.createProduct(productMapper.productToProductDto(product));
            return new ResponseEntity<ProductDTO>(productMapper.productDtoToProduct(productResponse), HttpStatus.OK);
        }catch (UnexpectedTypeException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        }

        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }
    @GetMapping("/{id}")
    ResponseEntity<ProductDTO> getProduct(@Validated @PathVariable(value = "id") UUID id){
        try{
            Product productResponse=productUseCase.getProduct(id);
            return new ResponseEntity<ProductDTO>(productMapper.productDtoToProduct(productResponse),HttpStatus.OK);
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
    @GetMapping("")
    ResponseEntity<List<ProductDTO>> listProducts(){
        try {
            List<Product> productList=productUseCase.listProducts();
            return new ResponseEntity<List<ProductDTO>>(productMapper.listProductToListProductDto(productList),HttpStatus.OK);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);

        }

    }
    @PutMapping("/{id}")
    ResponseEntity<ProductDTO> updateProduct(@Validated @RequestBody ProductDTO product,@PathVariable(name = "id") UUID id){
        try {
            Product productResponse=productUseCase.updateProduct(productMapper.productToProductDto(product),id);
            return new ResponseEntity<ProductDTO>(productMapper.productDtoToProduct(productResponse),HttpStatus.OK);
        }
        catch (UnexpectedTypeException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        }

        catch (NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not found",e);
        }

        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }
}
