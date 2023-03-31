package application.adapters.persistence.adapter;

import application.adapters.persistence.MongoConfig;
import application.adapters.persistence.entity.ProductEntity;
import application.adapters.mapper.mapperImpl.ProductMapperImpl;
import application.adapters.persistence.repository.ProductRepository;
import application.domain.Product;
import application.port.out.ProductPort;
import com.mongodb.client.MongoCollection;
import lombok.AllArgsConstructor;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ProductDBAdapter implements ProductPort {
    private final ProductRepository productRepository;
    private final ProductMapperImpl productMapper;
    private final MongoConfig mongoConfig;
    @Override
    public Product createProduct(Product product) {

        ProductEntity productEntity= productMapper.productEntityToProduct(product);
        ProductEntity savedProduct=productRepository.save(productEntity);
        return productMapper.productToProductEntity(savedProduct);
    }

    @Override
    public Product getProduct(UUID id) {
        if(productRepository.findById(id).isPresent()){
            ProductEntity productEntity=productRepository.findById(id).get();
            return productMapper.productToProductEntity(productEntity);

        }

        else{
            throw new NoSuchElementException("No such element");
        }

    }

    @Override
    public List<Product> listProducts() {


        MongoCollection<Document> collection = mongoConfig.getAllDocuments("Products");


        return productMapper.categoryToDocument(collection);

    }


    @Override
    public Product updateProduct(Product product, UUID id) {
        if(productRepository.findById(id).isPresent()){
            product.setId(id);
            ProductEntity productEntity =productMapper.productEntityToProduct(product);
            ProductEntity savedProducted=productRepository.save(productEntity);
            return productMapper.productToProductEntity(savedProducted);
        }
        else{
            throw new NoSuchElementException("No such element");
        }


    }

    @Override
    public List<Product> listProducts(UUID id) {
        MongoCollection<Document> collection = mongoConfig.getAllDocuments("Products");
        List<Product> productList=productMapper.categoryToDocument(collection);
        List<Product> filteredList = productList.stream()
                .filter(product -> product.getCategory().getId().equals(id))
                .collect(Collectors.toList());
        return filteredList;
    }

    @Override
    public List<Product> listProducts(String subStringName) {
        MongoCollection<Document> collection = mongoConfig.getAllDocuments("Products");
        List<Product> productList=productMapper.categoryToDocument(collection);
        List<Product> filteredList = productList.stream()
                .filter(product -> product.getName().contains(subStringName))
                .collect(Collectors.toList());
        return filteredList;
    }

}
