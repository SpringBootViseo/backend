package application.port;

import application.adapters.exception.ProductNotAvailableException;
import application.domain.Category;
import application.domain.Product;

import application.port.out.ProductPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

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

        product = new Product(id,"test","test","test","test", 20,10, List.of(new String[]{"test", "test"}),"test",0.0,0.0,0.0,category);

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
        Product product1 =new Product(id,"test","test","test","test", 20,10, List.of(new String[]{"test", "test"}),"test",0.0,0.0,0.0,category);
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
        Product productarg =new Product(id1,"test","test","test","test", 20,10, List.of(new String[]{"test", "test"}),"test",0.0,0.0,0.0,category);
        Product productexp=new Product(id,"test","test","test","test", 20,10, List.of(new String[]{"test", "test"}),"test",0.0,0.0,0.0,category);
        given(productPort.updateProduct(productarg,id)).willReturn(productexp);
        Product result=productService.updateProduct(productarg,id);
        verify(productPort,times(1)).updateProduct(any(),any());
        assertEquals(result,productexp);
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
        Product  productAfterOrder = new Product(id,"test","test","test","test", 20,10, List.of(new String[]{"test", "test"}),"test",0.0,0.0,0.0,category);
        given(productPort.orderProduct(id,2)).willReturn(productAfterOrder);
        assertEquals(productAfterOrder,productPort.orderProduct(id,2));
    }
    @Test
    void shouldThrowProductNotAvailableExceptionWhenOrderProductWithInvalidIdOrQuantity(){
        given(productPort.isAvailableToOrder(id,10)).willReturn(false);
        given(productPort.orderProduct(id,10)).willThrow(ProductNotAvailableException.class);
        assertThrows(ProductNotAvailableException.class,()->productPort.orderProduct(id,10));
    }
    @DisplayName("should call delete from Port once when delete Product")
    @Test
    void test4(){
        doNothing().when(productPort).deleteProduct(id);
        productService.deleteProduct(id);
        verify(productPort,times(1)).deleteProduct(id);
    }
    @DisplayName("should make reduction to product when setReductionToProduct")
    @Test
    void test5(){
        Product result=  new Product(id,"test","test","test","test", 20,10, List.of(new String[]{"test", "test"}),"test",10.0,100.0,90.0,category);
        given(productPort.setReductionToProduct(id,10.0)).willReturn(result);
        Product assertionresult= productService.setReductionToProduct(id,10.0);
        assertEquals(result,assertionresult);


    }
    @DisplayName("should return list of products that their names contains element of incomplete string in a given String to listProducts(string)  ")
    @Test
    void test10(){
        Product product1=  new Product(id,"Tomate rouge","test","test","test", 20,10, List.of(new String[]{"test", "test"}),"test",10.0,100.0,90.0,category);
        Product product2=  new Product(id,"Tomate cerise rouge","test","test","test", 20,10, List.of(new String[]{"test", "test"}),"test",10.0,100.0,90.0,category);
        Product product3=  new Product(id,"Tomate cerise orange","test","test","test", 20,10, List.of(new String[]{"test", "test"}),"test",10.0,100.0,90.0,category);
        given(productPort.listProducts()).willReturn(List.of(product1,product2,product3));
        List<Product> result1=productService.listProducts("omat rise");
        List<Product> result2=productService.listProducts("tomat roug");
        List<Product> result3=productService.listProducts("tomate");
        assertEquals(result1,List.of(product2,product3));
        assertEquals(result2,List.of(product1,product2));
        assertEquals(result3,List.of(product1,product2,product3));

    }
    @DisplayName("should return Empty list of products  when given String to listProducts(string) that isn't contained in any productName ")
    @Test
    void test11(){
        Product product1=  new Product(id,"Tomate rouge","test","test","test", 20,10, List.of(new String[]{"test", "test"}),"test",10.0,100.0,90.0,category);
        Product product2=  new Product(id,"Tomate cerise rouge","test","test","test", 20,10, List.of(new String[]{"test", "test"}),"test",10.0,100.0,90.0,category);
        Product product3=  new Product(id,"Tomate cerise orange","test","test","test", 20,10, List.of(new String[]{"test", "test"}),"test",10.0,100.0,90.0,category);
        given(productPort.listProducts()).willReturn(List.of(product1,product2,product3));
        List<Product> result1=productService.listProducts("maro");
        assertEquals(result1, Collections.emptyList());

    }
    @DisplayName("should return List of product with the same category id when listProduct(int id) id of the specific category")
    @Test
    void test12(){
        Category  category1=new Category(UUID.randomUUID(),"test","test","test");

        Product product1=  new Product(id,"Tomate rouge","test","test","test", 20,10, List.of(new String[]{"test", "test"}),"test",10.0,100.0,90.0,category);
        Product product2=  new Product(UUID.randomUUID(),"Tomate cerise rouge","test","test","test", 20,10, List.of(new String[]{"test", "test"}),"test",10.0,100.0,90.0,category);
        Product product3=  new Product(UUID.randomUUID(),"Tomate cerise orange","test","test","test", 20,10, List.of(new String[]{"test", "test"}),"test",10.0,100.0,90.0,category1);
        given(productPort.listProducts()).willReturn(List.of(product1,product2,product3));
        List<Product> productList=productService.listProducts(id);
        assertEquals(productList,List.of(product1,product2));
    }
    @DisplayName("should return empty list if no product with such id category exist in DB")
    @Test
    void test13(){
        UUID uuid=UUID.randomUUID();
        Product product1=  new Product(id,"Tomate rouge","test","test","test", 20,10, List.of(new String[]{"test", "test"}),"test",10.0,100.0,90.0,category);
        Product product2=  new Product(UUID.randomUUID(),"Tomate cerise rouge","test","test","test", 20,10, List.of(new String[]{"test", "test"}),"test",10.0,100.0,90.0,category);
        Product product3=  new Product(UUID.randomUUID(),"Tomate cerise orange","test","test","test", 20,10, List.of(new String[]{"test", "test"}),"test",10.0,100.0,90.0,category);
        if(uuid==id){
            uuid=UUID.randomUUID();
        }
        given(productPort.listProducts()).willReturn(List.of(product1,product2,product3));
        List<Product> productList=productService.listProducts(UUID.randomUUID());
        assertEquals(productList,Collections.emptyList());

    }

    @DisplayName("should not make reduction to product when setReductionToProduct with negative reductionpercentage")
    @Test
    void test6(){

        assertThrows(NoSuchElementException.class,()->productService.setReductionToProduct(id,-10.0)) ;
    }
    @DisplayName("should not make reduction to product when setReductionToProduct with  reductionpercentage superior or equals than 100")
    @Test
    void test7(){

        assertThrows(NoSuchElementException.class,()->productService.setReductionToProduct(id,100.0)) ;
    }

}


