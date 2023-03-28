package application.adapters.persistence.inventory;

import application.adapters.persistence.inventory.entity.CategoryEntity;
import application.adapters.persistence.inventory.entity.ProductEntity;
import application.domain.Category;
import application.domain.Product;
import application.port.out.ProductPort;
import lombok.AllArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class ProductDBAdapter implements ProductPort {
    private final ProductRepository productRepository;
    @Override
    public Product createProduct(Product product) {
        Category category= product.getCategory();
        CategoryEntity categoryEntity= new CategoryEntity(category.getId(), category.getName(), category.getLinkImg(), category.getLinkImgBanner());
        ProductEntity productEntity= new ProductEntity(product.getId(),product.getName(),product.getMarque(),product.getLinkImg(),product.getDescription(),product.getImages(),product.getUnitQuantity(),product.getReductionPercentage(), product.getPreviousPrice(), product.getCurrentPrice(), categoryEntity);
        ProductEntity savedProduct=productRepository.save(productEntity);
        Product resultedProduct=new Product(savedProduct.getId(),savedProduct.getName(),savedProduct.getMarque(),savedProduct.getLinkImg(),savedProduct.getDescription(),savedProduct.getImages(),savedProduct.getUnitQuantity(),savedProduct.getReductionPercentage(), savedProduct.getPreviousPrice(), savedProduct.getCurrentPrice(), category);
        return resultedProduct;
    }

    @Override
    public Product getProduct(UUID id) {
        ProductEntity productEntity=productRepository.findById(id).get();
        CategoryEntity categoryEntity=productEntity.getCategory();
        Category category=new Category(categoryEntity.getId(), categoryEntity.getName(), categoryEntity.getLinkImg(), categoryEntity.getLinkImgBanner());
        Product resultedProduct=new Product(productEntity.getId(),productEntity.getName(),productEntity.getMarque(),productEntity.getLinkImg(),productEntity.getDescription(),productEntity.getImages(),productEntity.getUnitQuantity(),productEntity.getReductionPercentage(), productEntity.getPreviousPrice(), productEntity.getCurrentPrice(), category);

        return resultedProduct;
    }

    @Override
    public List<Product> listProducts() {
        List<ProductEntity> productEntityList=productRepository.findAll();
        List<Product> productList=new ArrayList<>();
        for (ProductEntity productEntity:productEntityList) {
            CategoryEntity categoryEntity=productEntity.getCategory();
            Category category=new Category(categoryEntity.getId(), categoryEntity.getName(), categoryEntity.getLinkImg(), categoryEntity.getLinkImgBanner());
            productList.add(new Product(productEntity.getId(),productEntity.getName(),productEntity.getMarque(),productEntity.getLinkImg(),productEntity.getDescription(),productEntity.getImages(),productEntity.getUnitQuantity(),productEntity.getReductionPercentage(), productEntity.getPreviousPrice(), productEntity.getCurrentPrice(), category));

        }
        return productList;
    }

    @Override
    public Product updateProduct(Product product, UUID id) {
        Category category=product.getCategory();
        CategoryEntity categoryEntity= new CategoryEntity(category.getId(), category.getName(), category.getLinkImg(), category.getLinkImgBanner());
        ProductEntity productEntity =new ProductEntity(id, product.getName(), product.getMarque(),product.getLinkImg(), product.getDescription(), product.getImages(),product.getUnitQuantity(),product.getReductionPercentage(), product.getPreviousPrice(), product.getCurrentPrice(), categoryEntity);
        ProductEntity savedProducted=productRepository.save(productEntity);
        Product resultedProduct= new Product(savedProducted.getId(), savedProducted.getName(), savedProducted.getMarque(),savedProducted.getLinkImg(), savedProducted.getDescription(), savedProducted.getImages(),savedProducted.getUnitQuantity(),savedProducted.getReductionPercentage(), savedProducted.getPreviousPrice(), savedProducted.getCurrentPrice(), category);
        return resultedProduct;
    }
}
