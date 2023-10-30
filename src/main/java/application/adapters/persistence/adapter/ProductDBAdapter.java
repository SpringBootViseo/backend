package application.adapters.persistence.adapter;

import application.adapters.exception.ProductNotAvailableException;
import application.adapters.persistence.MongoConfig;
import application.adapters.persistence.entity.ProductEntity;
import application.adapters.mapper.mapperImpl.ProductMapperImpl;
import application.adapters.persistence.repository.ProductRepository;
import application.domain.Product;
import application.port.out.ProductPort;
import com.mongodb.client.MongoCollection;
import lombok.AllArgsConstructor;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;


@Component
@AllArgsConstructor
public class ProductDBAdapter implements ProductPort {
    private final ProductRepository productRepository;
    private final ProductMapperImpl productMapper;
    private final MongoConfig mongoConfig;
    private static final Logger logger = LoggerFactory.getLogger(ProductDBAdapter.class);
    @Override
    public Product createProduct(Product product) {
        logger.info("Creating product with ID: {}", product.getId());
        ProductEntity productEntity= productMapper.productEntityToProduct(product);
        logger.debug("Converting from productEntity to product");
        ProductEntity savedProduct=productRepository.save(productEntity);
        logger.info("Product with ID: {} created successfully", product.getId());
        return productMapper.productToProductEntity(savedProduct);
    }

    @Override
    public Product getProduct(UUID id) {
        logger.trace("checking if  product with ID: {} exist", id);

        if (productRepository.findById(id).isPresent()) {
            ProductEntity productEntity = productRepository.findById(id).get();
            logger.info("Retrieved product with ID: {}", id);
            return productMapper.productToProductEntity(productEntity);
        } else {
            logger.error("Product with ID {} not found", id);
            throw new NoSuchElementException("No such element");
        }
    }

    @Override
    public Product updateProduct(Product product, UUID id) {
        logger.info("Updating product with ID: {}", id);
        logger.trace("Checking if product with ID: {} exists", id);

        if (productRepository.findById(id).isPresent()) {
            product.setId(id);
            logger.debug("Adding ID : {}  to the product being updated",id);
            ProductEntity productEntity = productMapper.productEntityToProduct(product);
            logger.debug("Converting productEntity to Product");
            ProductEntity savedProduct = productRepository.save(productEntity);
            logger.info("Product with ID: {} updated successfully", id);
            return productMapper.productToProductEntity(savedProduct);
        } else {
            logger.error("Product with ID {} not found", id);
            throw new NoSuchElementException("No such element");
        }
    }



    @Override
    public List<Product> listProducts() {
        logger.info("Listing all products");

        MongoCollection<Document> collection = mongoConfig.getAllDocuments("Products");

        logger.debug("Converting mongo documents to products");

        List<Product> products = productMapper.categoryToDocument(collection);

        logger.info("Listed all products successfully");

        return products;
    }







    @Override
    public Boolean isAvailableToOrder(UUID id, int quantity) {
        logger.info("Checking availability for product with ID: {} and quantity: {}", id, quantity);
        if(quantity<1){
            logger.debug("Quantity is less than 1, product not available to order");
            return false;
        }
        Product product = this.getProduct(id);
        int availableQuantity = product.getStoredQuantity() - product.getOrderedQuantity();

        logger.info("Product with ID: {} has available quantity: {}", id, availableQuantity);

        return quantity <= availableQuantity;
    }


    @Override
    public Product orderProduct(UUID id, int quantity) {
        logger.info("Ordering product with ID: {} and quantity: {}", id, quantity);
        logger.trace("Checking product availability");
        if(this.isAvailableToOrder(id,quantity)){
            Product product=this.getProduct(id);
            product.setOrderedQuantity(product.getOrderedQuantity()+quantity);
            logger.debug("Product ordered successfully. Updating the product.");
            Product updatedProduct = this.updateProduct(product, id);
            logger.info("Product with ID: {} ordered successfully", id);

            return updatedProduct;
        }

        else {
            logger.error("Product with ID: {} not available for ordering", id);
            throw new ProductNotAvailableException();
        }
    }



    @Override
    public void deleteProduct(UUID id) {
        logger.info("Deleting product with ID: {}", id);
        productRepository.deleteById(id);
        logger.info("Product with ID: {} deleted successfully", id);
    }



    @Override
    public Product setReductionToProduct(UUID id, Double reduction) {
        logger.info("Setting reduction to product with ID: {} - Reduction: {}%", id, reduction);

        Product product = this.getProduct(id);
        product.setReductionPercentage(reduction);

        Double newPrice = product.getCurrentPrice() * (1 - reduction / 100);
        product.setPreviousPrice(product.getCurrentPrice());
        logger.debug("Setting new price after reduction to: {}", newPrice);
        product.setCurrentPrice(newPrice);

        logger.debug("Reduction percentage set successfully. Updating product details.");

        Product updatedProduct = this.updateProduct(product, id);

        logger.info("Reduction set for product with ID: {} - New Price: {}", id, newPrice);

        return updatedProduct;
    }

}
