package application.adapters.web.Controller;

import application.adapters.mapper.mapperImpl.ProductMapperImpl;
import application.adapters.persistence.repository.ProductRepository;
import application.adapters.web.presenter.ProductDTO;
import application.adapters.web.presenter.ReductionProductRequest;
import application.domain.Product;
import application.port.in.ProductUseCase;
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

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")

@RequestMapping("/products")
public class ProductController {
    private final static Logger logger= LogManager.getLogger(ProductController.class);
    private ProductUseCase productUseCase;
    private ProductMapperImpl productMapper;

    @PostMapping
    ResponseEntity<ProductDTO> createProduct(@Validated @RequestBody ProductDTO product){
        try{
            logger.trace("Map productDTO "+product.toString()+" to product");
            logger.trace("Call createProduct from ProductUseCase ");
            Product productResponse=productUseCase.createProduct(productMapper.productToProductDto(product));
            logger.trace("Map product"+ productResponse.toString()+" to product DTO");
            logger.debug("Map create Product from ProductUseCase to Post Mapper /products with body : "+product.toString());
            return new ResponseEntity<>(productMapper.productDtoToProduct(productResponse), HttpStatus.OK);
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
    ResponseEntity<ProductDTO> getProduct(@Validated @PathVariable(value = "id") UUID id){
        try{
            logger.trace("Call getProduct from ProductUseCase with id :"+id);

            Product productResponse=productUseCase.getProduct(id);
            logger.trace("Map product"+ productResponse.toString()+" to product DTO");
            logger.debug("Map getProduct from ProductUseCase to Get Mapper /products/"+id);

            return new ResponseEntity<>(productMapper.productDtoToProduct(productResponse),HttpStatus.OK);
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
    @GetMapping("")
    ResponseEntity<List<ProductDTO>> listProducts(){
        try {
            logger.trace("Call listProduct from ProductUseCase ");
            List<Product> productList=productUseCase.listProducts();
            logger.trace("Map product list"+ productList.toString()+" to product DTO list");
            logger.debug("Map listProduct from ProductUseCase to Get Mapper /products ");
            return new ResponseEntity<>(productMapper.listProductToListProductDto(productList),HttpStatus.OK);
        }
        catch (Exception e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);

        }

    }
    @GetMapping("/name")
    ResponseEntity<List<ProductDTO>> listProducts(@RequestParam(name="name") String subStringName){
        try{
            logger.trace("Call listProduct from ProductUseCase with subname :"+subStringName);

            List<Product> productList=productUseCase.listProducts(subStringName);
            logger.trace("Map product list"+ productList.toString()+" to product DTO list");
            logger.debug("Map listProduct with subname from ProductUseCase to Get Mapper /products/name?name="+subStringName);

            return new ResponseEntity<>(productMapper.listProductToListProductDto(productList),HttpStatus.OK);
        }
        catch (Exception e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);

        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<UUID> deleteProduct(@Validated @PathVariable(name = "id")UUID id){
        try{
            logger.trace("Call deleteProduct from ProductUseCase with id: "+id);

            productUseCase.deleteProduct(id);
            logger.debug("Map deleteProduct from ProductUseCase to Delete Mapper /products ");

            return new ResponseEntity<>(id,HttpStatus.OK);
        } catch (Exception e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);

        }

    }
    @PostMapping("/reduction")
    ResponseEntity<ProductDTO> productReduction(@Validated @RequestBody ReductionProductRequest reductionProductRequest){
        try{
            logger.trace("Call setReductionToProduct from ProductUseCase with id of product : "+reductionProductRequest.getId()+" and percentage : "+reductionProductRequest.getReduction()+"%");

            Product product=productUseCase.setReductionToProduct(reductionProductRequest.getId(), reductionProductRequest.getReduction());
            logger.trace("Map product"+ product.toString()+" to product DTO");
            logger.debug("Map setReductionToProduct from ProductUseCase to Post Mapper /products/reduction with body "+reductionProductRequest.toString());

            return new ResponseEntity<>(productMapper.productDtoToProduct(product),HttpStatus.OK);
        }
        catch (Exception e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);

        }
    }
    @PutMapping("/{id}")
    ResponseEntity<ProductDTO> updateProduct(@Validated @RequestBody ProductDTO product,@Validated @PathVariable(name = "id") UUID id){
        try {
            logger.trace("Call updateProductfrom ProductUseCase with idProduct : "+id+" and product "+product.toString());
            Product productResponse=productUseCase.updateProduct(productMapper.productToProductDto(product),id);
            logger.trace("Map product"+ productResponse.toString()+" to product DTO");
            logger.debug("Map updateProduct from ProductUseCase to Put Mapper /products/"+id+"");

            return new ResponseEntity<>(productMapper.productDtoToProduct(productResponse),HttpStatus.OK);
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