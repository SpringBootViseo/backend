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
import java.util.NoSuchElementException;
import java.util.Optional;
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
        product=new Product(id,"test","test","test","test",20,10, List.of(new String[]{"test", "test"}),"test",0,0,0,null);
        productEntity=new ProductEntity(id,"test","test","test","test",20,10, List.of(new String[]{"test", "test"}),"test",0,0,0,null);
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
    @Test
    void shouldGetProductWhenGetProductWithValidId(){
        given(productRepository.findById(id)).willReturn(Optional.of(productEntity));
        given(productMapper.productToProductEntity(productEntity)).willReturn(product);
        assertEquals(product,productDBAdapter.getProduct(id));
    }
    @Test
    void shouldThrowNoSuchElementWhenGetProductWithInvalidId(){
        given(productRepository.findById(id)).willReturn(Optional.empty());
        assertThrows(NoSuchElementException.class,()->
            productDBAdapter.getProduct(id));
    }
    @Test
    void shouldUpdateProductWhenUpdateProductWithValidId(){
        UUID id1=UUID.randomUUID();
        Product product1=new Product(id1,"test1","test1","test1","test1",20,10, List.of(new String[]{"test1", "test1"}),"test1",0,0,0,null);
        Product product2=new Product(id,"test1","test1","test1","test1",20,10, List.of(new String[]{"test1", "test1"}),"test1",0,0,0,null);
        ProductEntity productEntity2=new ProductEntity(id,"test1","test1","test1","test1",20,10, List.of(new String[]{"test1", "test1"}),"test1",0,0,0,null);

        given(productRepository.findById(id)).willReturn(Optional.of(productEntity));

        given(productMapper.productEntityToProduct(product2)).willReturn(productEntity2);
        given(productRepository.save(productEntity2)).willReturn(productEntity2);
        given(productMapper.productToProductEntity(productEntity2)).willReturn(product2);
        System.out.println(productDBAdapter.updateProduct(product1,id));
        assertEquals(productDBAdapter.updateProduct(product2,id),product2);

    }
    @Test
    void shouldThrowsNoSuchElementWhenUpdateProductWithInvaliId(){
        given(productRepository.findById(id)).willReturn(Optional.empty());
        assertThrows(NoSuchElementException.class,()->
            productDBAdapter.updateProduct(product,id)
        );
    }

}