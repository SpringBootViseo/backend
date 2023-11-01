package application.port;

import application.domain.Product;
import application.port.in.ProductUseCase;
import application.port.out.ProductPort;
import lombok.AllArgsConstructor;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService implements ProductUseCase {
    private final ProductPort productPort;
    private static final Logger logger = LogManager.getLogger(ProductService.class);

    @Override
    public Product createProduct(Product product) {
        logger.info("Creating product: {}", product.toString());
        return productPort.createProduct(product);
    }

    @Override
    public Product getProduct(UUID id) {
        logger.info("Getting product with ID: {}", id);
        return productPort.getProduct(id);
    }

    @Override
    public List<Product> listProducts() {
        logger.info("Listing all products");
        return productPort.listProducts();
    }

    @Override
    public List<Product> listProducts(UUID id) {
        logger.debug("Get All products");
        List<Product> productList = productPort.listProducts();
        logger.debug("Filter list of products that have category id "+id);
        List<Product> productsHasCategory = productList.stream()
                .filter(product -> product.getCategory().getId().equals(id))
                .collect(Collectors.toList());
        logger.info("Listing products with category ID: {}", id);

        return productsHasCategory;
    }

    @Override
    public List<Product> listProducts(String subStringName) {
        logger.debug("Transform subString of search into Regex");
        String[] words = subStringName.split("\\s+");
        int wordCount = words.length;
        logger.debug("List all products");
        List<Product> productList = productPort.listProducts();
        logger.debug("Filter product whicj name match the regex");
        List<Product> productsHasSubStringNameInName = productList.stream()
                .filter(product -> {
                    int matchCount = 0;
                    for (String word : words) {
                        if (product.getName().toLowerCase().contains(word.toLowerCase())) {
                            matchCount++;
                        }
                    }

                    return matchCount == wordCount;
                })
                .collect(Collectors.toList());
        logger.info("Listing products with name containing: {}", subStringName);

        return productsHasSubStringNameInName;
    }

    @Override
    public Product updateProduct(Product product, UUID id) {
        logger.info("Updating product with ID: {}", id);
        return productPort.updateProduct(product, id);
    }

    @Override
    public Boolean isAvailableToOrder(UUID id, int quantity) {
        logger.debug("Checking product availability for order - ID: {}, Quantity: {}", id, quantity);
        return productPort.isAvailableToOrder(id, quantity);
    }

    @Override
    public Product orderProduct(UUID id, int quantity) {
        logger.info("Ordering product - ID: {}, Quantity: {}", id, quantity);
        return productPort.orderProduct(id, quantity);
    }

    @Override
    public void deleteProduct(UUID id) {
        logger.info("Deleting product with ID: {}", id);
        productPort.deleteProduct(id);
    }

    @Override
    public Product setReductionToProduct(UUID id, Double reduction) {
        logger.debug("Check if reduction "+reduction+" is a valid percentage");
        if (reduction > 0.0 && reduction < 100.0) {
            logger.info("Setting reduction to product - ID: {}, Reduction: {}", id, reduction);

            return productPort.setReductionToProduct(id, reduction);
        } else {
            logger.error("Invalid reduction: {}", reduction);
            throw new NoSuchElementException("Invalid reduction");
        }
    }
}