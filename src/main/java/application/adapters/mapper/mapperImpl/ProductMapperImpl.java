package application.adapters.mapper.mapperImpl;

import application.adapters.mapper.ProductMapper;
import application.adapters.persistence.entity.CategoryEntity;
import application.adapters.persistence.entity.ProductEntity;
import application.adapters.web.presenter.CategoryDTO;
import application.adapters.web.presenter.ProductDTO;
import application.domain.Category;
import application.domain.Product;
import com.mongodb.client.MongoCollection;
import lombok.AllArgsConstructor;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Component
public class ProductMapperImpl implements ProductMapper {
    CategoryMapperImpl categoryMapper;
    @Override
    public Product productToProductEntity(ProductEntity productEntity) {
        Category category= categoryMapper.categoryToCategoryEntity(productEntity.getCategory());
        return  new Product(productEntity.getId(),productEntity.getName(),productEntity.getMarque(),productEntity.getLinkImg(),productEntity.getDescription(),productEntity.getQuantity(),productEntity.getImages(),productEntity.getUnitQuantity(),productEntity.getReductionPercentage(), productEntity.getPreviousPrice(), productEntity.getCurrentPrice(), category);
    }

    @Override
    public ProductEntity productEntityToProduct(Product product) {
        CategoryEntity categoryEntity= categoryMapper.categoryEntityToCategory(product.getCategory());
        return new ProductEntity(product.getId(),product.getName(),product.getMarque(),product.getLinkImg(),product.getDescription(),product.getQuantity(),product.getImages(),product.getUnitQuantity(),product.getReductionPercentage(), product.getPreviousPrice(), product.getCurrentPrice(), categoryEntity);
    }

    @Override
    public List<Product> listProductEntityToListProduct(List<ProductEntity> productEntityList) {
        List<Product> productList=new ArrayList<>();
        for (ProductEntity productEntity:productEntityList) {
            productList.add(this.productToProductEntity(productEntity));
        }
        return productList;
    }

    @Override
    public List<Product> categoryToDocument(MongoCollection<Document> collection) {
        List<ProductEntity> productEntityList=new ArrayList<>();
        for(Document doc:collection.find()){
            Document document=doc.get("category", Document.class);
            CategoryEntity categoryEntity =new CategoryEntity(document.get("_id", UUID.class),document.getString("name"),document.getString("linkImg"),document.getString("linkImgBanner"));

            productEntityList.add(new ProductEntity(doc.get("_id", UUID.class),doc.getString("name"),doc.getString("marque"),doc.getString("linkImg"),doc.getString("description"),doc.getInteger("quantity"),doc.getList("Images", String.class),null,doc.getLong("reductionPercentage"),doc.getLong("previousPrice") , doc.getLong("currentPrice"),categoryEntity ));
        }
        return this.listProductEntityToListProduct(productEntityList);
    }

    @Override
    public List<ProductEntity> listProductToListProductEntity(List<Product> productList) {
        List<ProductEntity> productEntityList=new ArrayList<>();
        for (Product product:productList) {
            productEntityList.add(this.productEntityToProduct(product));
        }
        return productEntityList;
    }

    @Override
    public Product productToProductDto(ProductDTO productDTO) {
        Category category= categoryMapper.categoryToCategoryDto(productDTO.getCategory());
        return  new Product(productDTO.getId(),productDTO.getName(),productDTO.getMarque(),productDTO.getLinkImg(),productDTO.getDescription(), productDTO.getQuantity(), productDTO.getImages(),productDTO.getUnitQuantity(),productDTO.getReductionPercentage(), productDTO.getPreviousPrice(), productDTO.getCurrentPrice(), category);

    }

    @Override
    public ProductDTO productDtoToProduct(Product product) {
        CategoryDTO category= categoryMapper.categoryDtoToCategory(product.getCategory());
        return  new ProductDTO(product.getId(),product.getName(),product.getMarque(),product.getLinkImg(),product.getDescription(), product.getQuantity(), product.getImages(),product.getUnitQuantity(),product.getReductionPercentage(), product.getPreviousPrice(), product.getCurrentPrice(), category);

    }

    @Override
    public List<ProductDTO> listProductToListProductDto(List<Product> productList) {
        List<ProductDTO> productDtoList=new ArrayList<>();
        for (Product product:productList) {
            productDtoList.add(this.productDtoToProduct(product));
        }
        return productDtoList;
    }
}
