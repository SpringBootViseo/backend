package application.adapters.persistence.adapter;

import application.adapters.mapper.mapperImpl.ProductMapperImpl;
import application.adapters.persistence.entity.ProductEntity;
import application.adapters.persistence.repository.ProductRepository;
import application.domain.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class ProductDBAdapterTest {
    @InjectMocks
    private ProductDBAdapter productDBAdapter;
    @Mock
    ProductMapperImpl productMapper;
    @Mock
    ProductRepository productRepository;
    UUID id;
    Product product;
    ProductEntity productEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        id=UUID.randomUUID();
        product=new Product(id,"test","test","test","test", List.of(new String[]{"test", "test"}),"test",0,0,0,null);
        productEntity=new ProductEntity(id,"test","test","test","test", List.of(new String[]{"test", "test"}),"test",0,0,0,null);
    }

    @AfterEach
    void tearDown() {
        id=null;
        productEntity=null;
        product=null;
    }
    @Test
    void shouldCreateProductWhenCreateProductWithProduct(){
        given(productMapper.productEntityToProduct(product)).willReturn(productEntity);
        given(productRepository.save(productEntity)).willReturn(productEntity);
        given(productMapper.productToProductEntity(productEntity)).willReturn(product);
        assertEquals(product,productDBAdapter.createProduct(product));
    }

}