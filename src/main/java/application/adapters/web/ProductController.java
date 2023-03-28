package application.adapters.web;

import application.domain.Product;
import application.port.in.ProductUseCase;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {
    public ProductUseCase productUseCase;
    @PostMapping
    Product createProduct(@RequestBody Product product){
        return productUseCase.createProduct(product);
    }
    @GetMapping("/{id}")
    Product getProduct(@PathVariable(value = "id") UUID id){
        return productUseCase.getProduct(id);
    }
    @GetMapping("/")
    List<Product> listProducts(){
        return productUseCase.listProducts();
    }
    @PutMapping("/{id}")
    Product updateProduct(@RequestBody Product product,@PathVariable(name = "id") UUID id){
        return productUseCase.updateProduct(product,id);
    }
}
