package application.port;

import application.domain.Cart;
import application.domain.Category;
import application.domain.Product;
import application.port.out.CartPort;
import application.port.out.ProductPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;


class CartServiceTest {
    @InjectMocks
    CartService cartService;
    @Mock
    CartPort cartPort;
    @Mock
    ProductPort productPort;
    UUID id;
    Cart cart;
    Product product;
    Category category;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        id=UUID.randomUUID();
        cart=new Cart("test",new ArrayList<>());
        category=new Category(id,"test","test","test");

        product = new Product(id,"test","test","test","test", 20, List.of(new String[]{"test", "test"}),"test",0,0,0,category);


    }

    @AfterEach
    void tearDown() {
        id=null;
        cart=null;
        category=null;
        product=null;
    }
    @Test
    void shouldReturnTrueWhenAvailableCartWithAvailableId(){
        given(cartPort.availableCart("test")).willReturn(true);
        assertEquals(cartService.availableCart("test"),true);
    }
    @Test
    void shouldReturnFalseWhenAvailableCartWithUnavailableId(){
        given(cartPort.availableCart("test")).willReturn(false);
        assertEquals(cartService.availableCart("test"),false);
    }
    @Test
    void shouldReturnCartWhenGetCartWithAvailableId(){
        given(cartService.availableCart("test")).willReturn(true);
        given(cartPort.getCart("test")).willReturn(cart);
    }
    @Test
    void shouldThrowNoSuchElementExceptionWhenGetCartWithUnavailableId(){
        given(cartService.availableCart("test")).willReturn(false);
        assertThrows(NoSuchElementException.class,()->cartService.getCart("test"));
    }
    @Test
    void shouldReturnCartWhenCreateCartWithId(){
        given(cartPort.createCart("test")).willReturn(cart);
        assertEquals(cart,cartService.createCart("test"));
    }
    @Test
    void shouldReturnCartWithProductWhenAddProduct(){
        Cart result= new Cart("test",List.of(product));

        given(productPort.getProduct(id)).willReturn(product);
        given(cartService.availableCart("test")).willReturn(true);

        given(cartService.getCart("test")).willReturn(cart);
        given(cartPort.addProduct(cart,product)).willReturn(result);

        assertEquals(result,cartService.addProduct("test",id));


    }
    @Test
    void shouldThrowExceptionWhenAddProductWithUnavailableCartId(){

        assertThrows(NoSuchElementException.class, ()-> cartService.addProduct("test",id));
    }


    @Test
    void shouldReturnCartWithoutTheProductWhendeleteProduct(){
        Cart arg= new Cart("test",List.of(product));

        given(productPort.getProduct(id)).willReturn(product);
        given(cartService.availableCart("test")).willReturn(true);

        given(cartService.getCart("test")).willReturn(arg);
        given(cartPort.deleteProduct(arg,product)).willReturn(cart);

        assertEquals(cart,cartService.deleteProduct("test",id));


    }
    @Test
    void shouldThrowExceptionWhenDeleteProductWithUnavailableCartId(){

        assertThrows(NoSuchElementException.class, ()-> cartService.deleteProduct("test",id));
    }

}
