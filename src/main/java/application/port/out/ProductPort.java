package application.port.out;

import application.domain.Product;

import java.util.List;
import java.util.UUID;

public interface ProductPort {
    Product createProduct(Product product);
    Product getProduct(UUID id);
    List<Product> listProducts();
    Product updateProduct(Product product,UUID id);
    List<Product> listProducts(UUID id);
    List<Product> listProducts(String subStringName);

}
