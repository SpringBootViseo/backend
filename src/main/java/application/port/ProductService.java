package application.port;

import application.domain.Product;
import application.port.in.ProductUseCase;
import application.port.out.ProductPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
@AllArgsConstructor
public class ProductService implements ProductUseCase{
    private final ProductPort productPort;
    @Override
    public Product createProduct(Product product) {
        return productPort.createProduct(product);
    }

    @Override
    public Product getProduct(UUID id) {
        return productPort.getProduct(id);
    }

    @Override
    public List<Product> listProducts() {
        return productPort.listProducts();
    }

    @Override
    public List<Product> listProducts(UUID id) {
        return productPort.listProducts(id);
    }

    @Override
    public List<Product> listProducts(String subStringName) {
        return productPort.listProducts(subStringName);
    }

    @Override
    public Product updateProduct(Product product, UUID id) {
        return productPort.updateProduct(product,id);
    }

    @Override
    public Boolean validQuantity(UUID id, int quantity) {
        return productPort.validQuantity(id,quantity);
    }
}
