package application.port;

import application.domain.Product;
import application.port.in.ProductUseCase;
import application.port.out.ProductPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

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
        List<Product> productList=productPort.listProducts();
        List<Product> productsHasCategory = productList.stream()
                .filter(product -> product.getCategory().getId().equals(id))
                .collect(Collectors.toList());
        return productsHasCategory;
    }

    @Override
    public List<Product> listProducts(String subStringName) {
        String[] words = subStringName.split("\\s+");
        int wordCount = words.length;
        List<Product> productList=productPort.listProducts();
        List<Product> productsHasSubStringNameInName = productList.stream()
                .filter(product ->{
                    int matchCount = 0;
                    for (String word : words) {
                        if (product.getName().toLowerCase().contains(word.toLowerCase())) {
                            matchCount++;
                        }
                    }

                    return matchCount == wordCount;
                } )
                .collect(Collectors.toList());
        return productsHasSubStringNameInName;
    }


    @Override
    public Product updateProduct(Product product, UUID id) {
        return productPort.updateProduct(product,id);
    }

    @Override
    public Boolean isAvailableToOrder(UUID id, int quantity) {

        return productPort.isAvailableToOrder(id,quantity);


    }

    @Override
    public Product orderProduct(UUID id, int quantity) {

        return productPort.orderProduct(id,quantity);


    }

    @Override
    public void deleteProduct(UUID id) {
        productPort.deleteProduct(id);
    }

    @Override
    public Product setReductionToProduct(UUID id, Double reduction) {
        if(reduction>0.0 && reduction<100.0){
            return productPort.setReductionToProduct(id, reduction);
        }
        else{
            throw  new NoSuchElementException("Invalid reduction");
        }
    }
}