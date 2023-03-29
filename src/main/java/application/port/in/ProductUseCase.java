package application.port.in;

import application.domain.Product;

import java.util.List;
import java.util.UUID;

public interface ProductUseCase {
    Product createProduct(Product product);
    Product getProduct(UUID id);
    List<Product> listProducts();
    List<Product> listProducts(UUID id);
    List<Product> listProducts(String subStringName);
    Product updateProduct(Product product,UUID id);

}
