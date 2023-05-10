package application.adapters.web.Controller;

import application.adapters.mapper.mapperImpl.ProductMapperImpl;

import application.adapters.web.presenter.CategoryDTO;
import application.adapters.web.presenter.ProductDTO;
import application.domain.Category;
import application.domain.Product;

import application.port.in.ProductUseCase;

import jakarta.validation.UnexpectedTypeException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class ProductControllerTest {
    @InjectMocks
    private ProductController productController;
    @Mock
    private ProductUseCase productUseCase;
    @Mock
    private ProductMapperImpl productMapper;
    private Product product;
    private ProductDTO productDTO;
    private UUID id;
    private Category category;
    private CategoryDTO categoryDTO;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        id=UUID.randomUUID();
        categoryDTO=new CategoryDTO(id,"test","test","test");
        category=new Category(id,"test","test","test");
        product = new Product(id,"test","test","test","test", 20,10,List.of(new String[]{"test", "test"}),"test",0.0,0.0,0.0,category);
        productDTO = new ProductDTO(id,"test","test","test","test", 20,10,List.of(new String[]{"test", "test"}),"test",0.0,0.0,0.0,categoryDTO);

    }

    @AfterEach
    void tearDown() {
        id=null;
        categoryDTO=null;
        category=null;
        product=null;
        productDTO=null;
    }
    @DisplayName("Unit Test for create Product with valid product")
    @Test
    void shouldReturnProductDtoWhenCreateProductWithValidProduct(){

        given(productMapper.productDtoToProduct(product)).willReturn(productDTO);
        given(productUseCase.createProduct(product)).willReturn(product);
        given(productMapper.productToProductDto(productDTO)).willReturn(product);
        ResponseEntity<ProductDTO> expectation=new ResponseEntity<>(productDTO, HttpStatus.OK);
        ResponseEntity<ProductDTO> result=productController.createProduct(productDTO);
        assertEquals(expectation,result);
        verify(productMapper,times(1)).productToProductDto(productDTO);
        verify(productMapper,times(1)).productDtoToProduct(product);
        verify(productUseCase,times(1)).createProduct(product);
    }
    @DisplayName("Unit test for create Product with invalid product")
    @Test
    void shoulThrowUnexpectedTypeExceptionWhenCreateProductWithInvalidProduct(){

        productDTO.setName(null);
        product.setName(null);
        given(productMapper.productToProductDto(productDTO)).willReturn(product);
        given(productMapper.productDtoToProduct(product)).willReturn(productDTO);
        when(productUseCase.createProduct(product)).thenThrow(new UnexpectedTypeException("test exception"));
        try {
            productController.createProduct(productDTO);
        } catch (ResponseStatusException e) {
            assertEquals(400, e.getBody().getStatus());
            assertEquals("Bad argument", e.getReason());
            assertTrue(e.getCause() instanceof UnexpectedTypeException);
        }

    }
    @DisplayName("Unit Test for create Product with unexcept Exception")
    @Test
    void shouldThrowUnexpectedExceptionWhenCreateProductWithProduct(){

        given(productMapper.productToProductDto(productDTO)).willReturn(product);
        given(productMapper.productDtoToProduct(product)).willReturn(productDTO);
        when(productUseCase.createProduct(product)).thenThrow(new RuntimeException("Something went wrong"));
        try {
            productController.createProduct(productDTO);
        } catch (ResponseStatusException e) {
            assertEquals(500, e.getBody().getStatus());
            assertEquals("An error occurred", e.getReason());
            assertTrue(e.getCause() instanceof RuntimeException);
        }
    }
    @DisplayName("Unit Test for getProduct with valid id")
    @Test
    void shouldReturnProductDtoWhenGivenValidIdToGetProduct(){
        given(productUseCase.getProduct(id)).willReturn(product);
        given(productMapper.productDtoToProduct(product)).willReturn(productDTO);
        ResponseEntity<ProductDTO> expectation=new ResponseEntity<>(productDTO, HttpStatus.OK);
        ResponseEntity<ProductDTO> result=productController.getProduct(id);
        assertEquals(expectation,result);
    }
    @DisplayName("Unit Test for getProduct with invalid id")
    @Test
    void shouldThrowBadRequestWhenGivenInvalidIdToGetProduct(){
        UUID productId = UUID.randomUUID();
        when(productUseCase.getProduct(productId)).thenThrow(new UnexpectedTypeException("Bad argument"));
        assertThrows(ResponseStatusException.class, () -> productController.getProduct(productId));
    }
    @DisplayName("Unit Test for getProduct with infounded id")
    @Test
    void shouldThrowNoSuchElementWhenGivenInExistantIdToGetProduct(){
        UUID productId = UUID.randomUUID();
        when(productUseCase.getProduct(productId)).thenThrow(new NoSuchElementException("Product not found"));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> productController.getProduct(productId));
        assertEquals(404, exception.getBody().getStatus());
    }
    @DisplayName("Unit Test for getProduct with unExcepted exception")
    @Test
    void shouldThrowExceptionWhenGivenIdToGetProduct(){
        UUID productId = UUID.randomUUID();
        when(productUseCase.getProduct(productId)).thenThrow(new RuntimeException("Unexpected exception"));

        // Act and Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> productController.getProduct(productId));
        assertEquals(500, exception.getBody().getStatus());
    }
    @DisplayName("Unit Test for listProducts with categories")
    @Test
    void shouldReturnListofProductsWhenListProducts(){
        List<Product> products=new ArrayList<>();
        List<ProductDTO> productsdto=new ArrayList<>();
        UUID id1=UUID.randomUUID();
        Product product1=new Product(id1,"test1","test1","test1","test1", 20,10,List.of(new String[]{"test1", "test1"}),"test1",0.0,0.0,0.0,category);
        ProductDTO productdto1=new ProductDTO(id1,"test1","test1","test1","test1", 20,10,List.of(new String[]{"test1", "test1"}),"test1",0.0,0.0,0.0,categoryDTO);
        products.add(product);
        products.add(product1);
        productsdto.add(productDTO);
        productsdto.add(productdto1);
        when(productUseCase.listProducts()).thenReturn(products);
        when(productMapper.listProductToListProductDto(products)).thenReturn(productsdto);
        ResponseEntity<List<ProductDTO>> response = productController.listProducts();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals(product1.getId(), response.getBody().get(1).getId());
        assertEquals(product1.getName(), response.getBody().get(1).getName());
        assertEquals(id, response.getBody().get(0).getId());
        assertEquals("test", response.getBody().get(0).getName());
    }
    @DisplayName("Unit Test for listProducts with empty categories")
    @Test
    void shouldReturnEmptyListWhenListProducts() {

        List<Product> products = new ArrayList<>();
        List<ProductDTO> productDto=new ArrayList<>();
        when(productUseCase.listProducts()).thenReturn(products);
        when(productMapper.listProductToListProductDto(products)).thenReturn(productDto);
        ResponseEntity<List<ProductDTO>> response = productController.listProducts();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }
    @DisplayName("Unit Test for listProducts with unexcepted categories")
    @Test
    void shouldThrowExceptionWhenListProducts(){
        when(productUseCase.listProducts()).thenThrow(new RuntimeException("Unexpected exception"));
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> productController.listProducts());
        assertEquals(500, exception.getBody().getStatus());
    }
    @DisplayName("Unit Test for updateProduct with valid id and product")
    @Test
    void shouldUpdateProductWhenUpdateProductWithValidProduct(){
        Product product1=new Product(id,"test1","test1","test1","test1", 20,10,List.of(new String[]{"test1", "test1"}),"test1",0.0,0.0,0.0,category);
        ProductDTO productDTO1=new ProductDTO(id,"test1","test1","test1","test1", 20,10,List.of(new String[]{"test1", "test1"}),"test1",0.0,0.0,0.0,categoryDTO);

        when(productUseCase.updateProduct(product1,id)).thenReturn(product1);
        when(productMapper.productDtoToProduct(product1)).thenReturn(productDTO1);
        when(productMapper.productToProductDto(productDTO1)).thenReturn(product1);
        ResponseEntity<ProductDTO> response = productController.updateProduct(productDTO1, id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(id, response.getBody().getId());
        assertEquals("test1", response.getBody().getName());

    }
    @DisplayName("Unit Test for updateProduct with invalid Product")
    @Test
    void shouldThrowBadRequestWhenUpdateProductWithInvalidProduct(){
        Product product1=new Product(id,"test1","test1","test1","test1", 20,10,List.of(new String[]{"test1", "test1"}),"test1",0.0,0.0,0.0,category);
        ProductDTO productDTO1=new ProductDTO(id,"test1","test1","test1","test1", 20,10,List.of(new String[]{"test1", "test1"}),"test1",0.0,0.0,0.0,categoryDTO);
        when(productUseCase.updateProduct(product1,id)).thenThrow(new UnexpectedTypeException("Bad argument"));
        when(productMapper.productDtoToProduct(product1)).thenReturn(productDTO1);
        when(productMapper.productToProductDto(productDTO1)).thenReturn(product1);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> productController.updateProduct(productDTO1, id));
        assertEquals(400, exception.getBody().getStatus());

    }
    @DisplayName("Unit Test for updateProduct with not found Product")
    @Test
    void shouldThrowNotFoundRequestWhenUpdateProductWithNotFoundId(){
        Product product1=new Product(id,"test1","test1","test1","test1", 20,10,List.of(new String[]{"test1", "test1"}),"test1",0.0,0.0,0.0,category);
        ProductDTO productDTO1=new ProductDTO(id,"test1","test1","test1","test1", 20,10,List.of(new String[]{"test1", "test1"}),"test1",0.0,0.0,0.0,categoryDTO);
        when(productUseCase.updateProduct(product1,id)).thenThrow(new NoSuchElementException("Product not found"));
        when(productMapper.productDtoToProduct(product1)).thenReturn(productDTO1);
        when(productMapper.productToProductDto(productDTO1)).thenReturn(product1);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> productController.updateProduct(productDTO1, id));
        assertEquals(404, exception.getBody().getStatus());
    }
    @DisplayName("Unit Test for updateProduct with unexcepted Exception")
    @Test
    void shouldThrowUnexceptedExceptionWhenUpdateProduct(){
        Product product1=new Product(id,"test1","test1","test1","test1", 20,10,List.of(new String[]{"test1", "test1"}),"test1",0.0,0.0,0.0,category);
        ProductDTO productDTO1=new ProductDTO(id,"test1","test1","test1","test1", 20,10,List.of(new String[]{"test1", "test1"}),"test1",0.0,0.0,0.0,categoryDTO);
        when(productUseCase.updateProduct(product1,id)).thenThrow(new RuntimeException("Unexpected exception"));
        when(productMapper.productDtoToProduct(product1)).thenReturn(productDTO1);
        when(productMapper.productToProductDto(productDTO1)).thenReturn(product1);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> productController.updateProduct(productDTO1, id));
        assertEquals(500, exception.getBody().getStatus());

    }
    @DisplayName("Unit test for listProducts with name")
    @Test
    void shouldReturnListOfProductWhenListProductsWithSubName(){
        Product product1=new Product(id,"test1","test1","test1","test1", 20,10,List.of(new String[]{"test1", "test1"}),"test1",0.0,0.0,0.0,category);
        ProductDTO productDTO1=new ProductDTO(id,"test1","test1","test1","test1", 20,10,List.of(new String[]{"test1", "test1"}),"test1",0.0,0.0,0.0,categoryDTO);
        List<Product> productList= new ArrayList<>();
        productList.add(product);
        productList.add(product1);
        List<ProductDTO> productDTOList=new ArrayList<>();
        productDTOList.add(productDTO);
        productDTOList.add(productDTO1);
        when(productUseCase.listProducts("tes")).thenReturn(productList);
        when(productMapper.listProductToListProductDto(productList)).thenReturn(productDTOList);
        ResponseEntity<List<ProductDTO>> response = productController.listProducts("tes");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("test", response.getBody().get(0).getName());
        assertEquals("test1", response.getBody().get(1).getName());

    }
    @DisplayName("Unit test for listProducts with name")
    @Test
    void shouldReturnEmptyListWhenListProductsWithSubName(){

        List<Product> productList = new ArrayList<>();
        List<ProductDTO> productDTOList=new ArrayList<>();
        when(productUseCase.listProducts("tes")).thenReturn(productList);
        when(productMapper.listProductToListProductDto(productList)).thenReturn(productDTOList);

        ResponseEntity<List<ProductDTO>> response = productController.listProducts("test");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }
    @DisplayName("Unit test for listProducts with name")
    @Test
    void sholdThrowErrorWhenListProductsWithSubName(){
        String subStringName = "book";
        when(productUseCase.listProducts(eq(subStringName))).thenThrow(new RuntimeException("Unexpected exception"));

        // Act and Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> productController.listProducts(subStringName));
        assertEquals(500, exception.getBody().getStatus());
    }

}