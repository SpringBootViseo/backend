package application.port;

import application.adapters.exception.ProductNotAvailableException;
import application.domain.Category;
import application.domain.Product;

import application.port.out.ProductPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ProductServiceTest {
    @InjectMocks
    ProductService productService;
    @Mock
    ProductPort productPort;
    Product product;
    Category category;
    UUID id;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        id= UUID.randomUUID();
        category=new Category(id,"test","test","test");

        product = new Product(id,"test","test","test","test", 20,10, List.of(new String[]{"test", "test"}),"test",0,0,0,category);

    }

    @AfterEach
    void tearDown() {
        id=null;
        product=null;
    }

    @Test
    void shouldCallCreateProductOnceWhenCreateProduct() {
        given(productPort.createProduct(product)).willReturn(product);
        Product result=productService.createProduct(product);
        verify(productPort,times(1)).createProduct(any());
        assertEquals(result,product);
    }
    @Test
    void shouldCallGetProductOnceWhenGetProduct() {
        given(productPort.getProduct(id)).willReturn(product);
        Product result=productService.getProduct(id);
        verify(productPort,times(1)).getProduct(any());
        assertEquals(result,product);
    }

    @Test
    void shouldCallListCategoriesOnceAndReturnListCategoriesWhenlistCategories() {
        Product product1 =new Product(id,"test1","test1","test1","test1", 20,10, List.of(new String[]{"test1", "test1"}),"test1",0,0,0,category);
        List<Product> productList=new ArrayList<>();
        productList.add(product);
        productList.add(product1);
        given(productPort.listProducts()).willReturn(productList);
        List<Product> result=productService.listProducts();
        verify(productPort,times(1)).listProducts();
        assertEquals(result,productList);
    }

    @Test
    void shouldCallupdateProductOnceAndUpdateProductWhenUpdateProduct() {
        UUID id1=UUID.randomUUID();
        Product productarg =new Product(id1,"test1","test1","test1","test1", 20,10, List.of(new String[]{"test1", "test1"}),"test1",0,0,0,category);
        Product productexp=new Product(id,"test1","test1","test1","test1", 20,10, List.of(new String[]{"test1", "test1"}),"test1",0,0,0,category);
        given(productPort.updateProduct(productarg,id)).willReturn(productexp);
        Product result=productService.updateProduct(productarg,id);
        verify(productPort,times(1)).updateProduct(any(),any());
        assertEquals(result,productexp);
    }

    @Test
    void ShouldCallListProductsOnceAndListProductsWithTheCategoryIdWhenListProductWithId() {
        given(productPort.listProducts(id)).willReturn(List.of(product));
        List<Product> productList=productService.listProducts(id);
        assertEquals(productList,List.of(product));
        verify(productPort,times(1)).listProducts((UUID) any());

    }

    @Test
    void ShouldCallListProductsOnceAndListProductsWithNameLikeSubstringWhenListProductWithSubString() {
        given(productPort.listProducts("t")).willReturn(List.of(product));
        List<Product> productList=productService.listProducts("t");
        assertEquals(productList,List.of(product));
        verify(productPort,times(1)).listProducts((String) any());

    }
    @Test
    void shouldReturnTrueWhenIsAvailableWithValidIdAndQuanity(){
        given(productPort.isAvailableToOrder(id,2)).willReturn(true);
        assertEquals(true,productService.isAvailableToOrder(id,2));
    }
    @Test
    void shouldReturnFalseWhenIsAvailableWithIvalidIdOrQuantity(){
        given(productPort.isAvailableToOrder(id,30)).willReturn(false);
        assertEquals(false,productService.isAvailableToOrder(id,30));
    }
    @Test
    void shouldReturnFalseWhenIsAvailableWithNegativeQuantity(){
        given(productPort.isAvailableToOrder(id,-1)).willReturn(false);
        assertEquals(false,productService.isAvailableToOrder(id,-1));
    }
    @Test
    void shouldReturnFalseWhenIsAvailableWithZeroQuantity(){
        given(productPort.isAvailableToOrder(id,0)).willReturn(false);
        assertEquals(false,productService.isAvailableToOrder(id,0));
    }

    @Test
    void shouldOrderProductWhenOrderProductWithValidIdAndQuantity(){
        Product  productAfterOrder = new Product(id,"test","test","test","test", 20,12, List.of(new String[]{"test", "test"}),"test",0,0,0,category);
        given(productPort.orderProduct(id,2)).willReturn(productAfterOrder);
        assertEquals(productAfterOrder,productPort.orderProduct(id,2));
    }
    @Test
    void shouldThrowProductNotAvailableExceptionWhenOrderProductWithInvalidIdOrQuantity(){
        given(productPort.isAvailableToOrder(id,10)).willReturn(false);
        given(productPort.orderProduct(id,10)).willThrow(ProductNotAvailableException.class);
        assertThrows(ProductNotAvailableException.class,()->productPort.orderProduct(id,10));
    }

    }


