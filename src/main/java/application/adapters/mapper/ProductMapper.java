package application.adapters.mapper;

import application.adapters.persistence.entity.ProductEntity;

import application.adapters.web.presenter.CategoryDTO;
import application.adapters.web.presenter.ProductDTO;
import application.domain.Category;
import application.domain.Product;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper
public interface ProductMapper {
    Product productToProductEntity(ProductEntity productEntity);
    ProductEntity productEntityToProduct(Product product);
    List<Product> listProductEntityToListProduct(List<ProductEntity> productEntityList);
    List<Product> categoryToDocument(MongoCollection<Document> collection);
    List <ProductEntity> listProductToListProductEntity(List<Product> productList);
    Product productToProductDto(ProductDTO productDTO);
    ProductDTO productDtoToProduct(Product product);
    List <ProductDTO> listProductToListProductDto(List<Product> productList);

}
