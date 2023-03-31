package application.adapters.web.Controller;

import application.adapters.mapper.mapperImpl.CategoryMapperImpl;
import application.adapters.mapper.mapperImpl.ProductMapperImpl;
import application.adapters.web.presenter.CategoryDTO;
import application.adapters.web.presenter.CategoryProductDTO;
import application.adapters.web.presenter.ProductDTO;
import application.domain.Category;
import application.domain.Product;
import application.port.in.CategoryUseCase;
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

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CategoryProductControllerTest {
    @InjectMocks
    private CategoryProductController controller;
    @Mock
    private ProductUseCase productUseCase;
    @Mock
    private ProductMapperImpl productMapper;

    @Mock
    private CategoryUseCase categoryUseCase;
    @Mock
    private CategoryMapperImpl categoryMapper;

    private Product product;
    private Product product1;
    private ProductDTO productDTO;
    private ProductDTO productDTO1;
    private UUID id;
    private Category category;
    private CategoryDTO categoryDTO;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        id=UUID.randomUUID();
        categoryDTO=new CategoryDTO(id,"test","test","test");
        category=new Category(id,"test","test","test");
        product = new Product(id,"test","test","test","test", 20,List.of(new String[]{"test", "test"}),"test",0,0,0,category);
        productDTO = new ProductDTO(id,"test","test","test","test",20, List.of(new String[]{"test", "test"}),"test",0,0,0,categoryDTO);
        product1 = new Product(id,"test1","test1","test1","test1",20, List.of(new String[]{"test1", "test1"}),"test1",0,0,0,category);
        productDTO1 = new ProductDTO(id,"test1","test1","test1","test1",20, List.of(new String[]{"test1", "test1"}),"test",0,0,0,categoryDTO);

    }

    @AfterEach
    void tearDown() {
        id=null;
        categoryDTO=null;
        category=null;
        product=null;
        productDTO=null;
    }
    @DisplayName("Unit Test for getCategoryProduct with valid product and category")
    @Test
    void shouldReturnCategoryProductWhenGetCategoryProductWithValidId(){
        List<Product> productList = Arrays.asList(product, product1);
        List<ProductDTO> productDTOList = Arrays.asList(productDTO, productDTO1);
        when(categoryUseCase.getCategory(id)).thenReturn(category);
        when(productUseCase.listProducts(id)).thenReturn(productList);
        when(categoryMapper.categoryDtoToCategory(category)).thenReturn(categoryDTO);
        when(productMapper.listProductToListProductDto(productList)).thenReturn(productDTOList);
        ResponseEntity<CategoryProductDTO> response = controller.getCategoryProducts(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("test", response.getBody().getCategoryDTO().getName());
        assertEquals(2, response.getBody().getProductDTOList().size());
        assertEquals("test", response.getBody().getProductDTOList().get(0).getName());
        assertEquals("test1", response.getBody().getProductDTOList().get(1).getName());

    }
    @DisplayName("Unit Test for getCategoryProduct with not found category")

    @Test
    void testGetCategoryProducts_CategoryNotFound() {

        when(categoryUseCase.getCategory(id)).thenThrow(new NoSuchElementException("Category not found"));
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> controller.getCategoryProducts(id));
        assertEquals(404, exception.getBody().getStatus());
    }
    @DisplayName("Unit Test for getCategoryProduct will Throw UnExcepeted Exception")
    @Test
    void testGetCategoryProducts_ServerError() {
        when(categoryUseCase.getCategory(id)).thenThrow(new RuntimeException("Unexpected exception"));
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> controller.getCategoryProducts(id));
        assertEquals(500, exception.getBody().getStatus());
    }
    @DisplayName("Unit Test for listCategoryProducts will contain 2 categories with 2 products for each")
    @Test
    void shouldReturnCategoryProductWhenlistCategoryProductsWillReturnListOfCategoriesWithTheirProducts(){
        UUID id1=UUID.randomUUID();
        UUID id2=UUID.randomUUID();
        UUID id3=UUID.randomUUID();
        Category category1=new Category(id1,"test1","test1","test1");
        Product product2 = new Product(id2,"test2","test2","test2","test2",20, List.of(new String[]{"test2", "test2"}),"test2",0,0,0,category1);
        product1.setId(id1);
        Product product3 = new Product(id3,"test3","test3","test3","test3",20, List.of(new String[]{"test3", "test3"}),"test3",0,0,0,category1);
        List<Category> categories = new ArrayList<>();
        categories.add(category);
        categories.add(category1);
        List<Product> products = new ArrayList<>();
        products.add(product);
        products.add(product1);
        List<Product> products1=new ArrayList<>();
        products1.add(product2);
        products1.add(product3);
        CategoryDTO categoryDTO1=new CategoryDTO(id1,"test1","test1","test1");
        ProductDTO productDTO2 = new ProductDTO(id2,"test2","test2","test2","test2",20, List.of(new String[]{"test2", "test2"}),"test2",0,0,0,categoryDTO1);
        productDTO1.setId(id1);
        ProductDTO productDTO3 = new ProductDTO(id3,"test3","test3","test3","test3",20, List.of(new String[]{"test3", "test3"}),"test3",0,0,0,categoryDTO1);
        List<CategoryDTO> categoriesDTO = new ArrayList<>();
        categoriesDTO.add(categoryDTO);
        categoriesDTO.add(categoryDTO1);
        List<ProductDTO> productsDTO = new ArrayList<>();
        productsDTO.add(productDTO);
        productsDTO.add(productDTO1);
        List<ProductDTO> productsDTO1=new ArrayList<>();
        productsDTO1.add(productDTO2);
        productsDTO1.add(productDTO3);
        when(categoryUseCase.listCategories()).thenReturn(categories);
        when(productUseCase.listProducts(id)).thenReturn(products);
        when(productUseCase.listProducts(id1)).thenReturn(products1);
        when(categoryMapper.categoryDtoToCategory(category)).thenReturn(categoryDTO);
        when(categoryMapper.categoryDtoToCategory(category1)).thenReturn(categoryDTO1);
        when(productMapper.listProductToListProductDto(products)).thenReturn(productsDTO);
        when(productMapper.listProductToListProductDto(products1)).thenReturn(productsDTO1);
        ResponseEntity<List<CategoryProductDTO>> response=controller.listCategoryProducts();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        CategoryProductDTO categoryProductDTO1 = response.getBody().get(0);
        CategoryProductDTO categoryProductDTO2 = response.getBody().get(1);

        assertEquals(categoryDTO, categoryProductDTO1.getCategoryDTO());
        assertEquals(productsDTO, categoryProductDTO1.getProductDTOList());

        assertEquals(categoryDTO1, categoryProductDTO2.getCategoryDTO());
        assertEquals(productsDTO1, categoryProductDTO2.getProductDTOList());
    }

    @DisplayName("Unit Test for listCategoryProducts will Throw NotFound Error")
    @Test
    void shouldThrowNotFoundWhenlistCategoryProducts(){
        when(categoryUseCase.listCategories()).thenThrow(new NoSuchElementException("Error"));
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> controller.listCategoryProducts());
        assertEquals(404, exception.getBody().getStatus());
        assertEquals("Category not found", exception.getReason());

    }
    @DisplayName("Unit Test for listCategoryProducts will Throw UnexpectedTypeException")
    @Test
    public void shouldThrowUnexpectedTypeExceptionWhenlistCategoryProducts() {
        // Setup
        when(categoryUseCase.listCategories()).thenThrow(new UnexpectedTypeException("Error"));

        // Execute and assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> controller.listCategoryProducts());
        assertEquals(400, exception.getBody().getStatus());
        assertEquals("Bad argument", exception.getReason());
    }
    @DisplayName("Unit Test for listCategoryProducts will Throw UnExcepted Error")
    @Test
    public void shouldThrowExceptionWhenlistCategoryProducts() {
        // Setup
        when(categoryUseCase.listCategories()).thenThrow(new RuntimeException("Error"));

        // Execute and assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> controller.listCategoryProducts());
        assertEquals(500, exception.getBody().getStatus());
        assertEquals("An error occurred", exception.getReason());
    }



}