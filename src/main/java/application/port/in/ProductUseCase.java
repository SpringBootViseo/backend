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
    Boolean isAvailableToOrder(UUID id, int quantity);
    Product orderProduct(UUID id,int quantity);
    void deleteProduct(UUID id);
    Product setReductionToProduct(UUID id,Double reduction);

}
