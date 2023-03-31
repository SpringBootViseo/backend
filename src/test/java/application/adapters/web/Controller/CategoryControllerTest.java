package application.adapters.web.Controller;

import application.adapters.mapper.mapperImpl.CategoryMapperImpl;
import application.adapters.web.Controller.CategoryController;
import application.adapters.web.presenter.CategoryDTO;
import application.domain.Category;
import application.port.in.CategoryUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.UnexpectedTypeException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

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
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.*;

class CategoryControllerTest {
    @InjectMocks
    private CategoryController categoryController;
    @Mock
    private CategoryUseCase categoryUseCase;
    @Mock
    private CategoryMapperImpl categoryMapper;

    private Category category;
    private CategoryDTO categoryDTO;
    private UUID id;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        id=UUID.randomUUID();
        categoryDTO=new CategoryDTO(id,"test","test","test");
        category=new Category(id,"test","test","test");
    }

    @AfterEach
    void tearDown() {
        id=null;
        category=null;
        categoryDTO=null;
    }
    @DisplayName("Unit Test for create Category with valid category")
    @Test
    void shouldReturnCategoryDtoWhenCreateCategoryWithValidCategory(){

        given(categoryMapper.categoryToCategoryDto(categoryDTO)).willReturn(category);
        given(categoryUseCase.createCategory(category)).willReturn(category);
        given(categoryMapper.categoryDtoToCategory(category)).willReturn(categoryDTO);
        ResponseEntity<CategoryDTO> expectation=new ResponseEntity<>(categoryDTO, HttpStatus.OK);
        ResponseEntity<CategoryDTO> result=categoryController.createCategory(categoryDTO);
        assertEquals(expectation,result);
        verify(categoryMapper,times(1)).categoryToCategoryDto(categoryDTO);
        verify(categoryMapper,times(1)).categoryDtoToCategory(category);
        verify(categoryUseCase,times(1)).createCategory(category);
    }
    @DisplayName("Unit test for create Category with invalid category")
    @Test
    void shoulThrowUnexpectedTypeExceptionWhenCreateCategoryWithInvalidCategory(){

        categoryDTO.setName(null);
        category.setName(null);
        given(categoryMapper.categoryToCategoryDto(categoryDTO)).willReturn(category);
        given(categoryMapper.categoryDtoToCategory(category)).willReturn(categoryDTO);
        when(categoryUseCase.createCategory(category)).thenThrow(new UnexpectedTypeException("test exception"));
        try {
            categoryController.createCategory(categoryDTO);
        } catch (ResponseStatusException e) {
            assertEquals(400, e.getBody().getStatus());
            assertEquals("Bad argument", e.getReason());
            assertTrue(e.getCause() instanceof UnexpectedTypeException);
        }

    }
    @DisplayName("Unit Test for create Category with unexcept Exception")
    @Test
    void shouldThrowUnexpectedExceptionWhenCreateCategoryWithCategory(){

        given(categoryMapper.categoryToCategoryDto(categoryDTO)).willReturn(category);
        given(categoryMapper.categoryDtoToCategory(category)).willReturn(categoryDTO);
        when(categoryUseCase.createCategory(category)).thenThrow(new RuntimeException("Something went wrong"));
        try {
            categoryController.createCategory(categoryDTO);
        } catch (ResponseStatusException e) {
            assertEquals(500, e.getBody().getStatus());
            assertEquals("An error occurred", e.getReason());
            assertTrue(e.getCause() instanceof RuntimeException);
        }
    }
    @DisplayName("Unit Test for getCategory with valid id")
    @Test
    void shouldReturnCategoryDtoWhenGivenValidIdToGetCategory(){
        given(categoryUseCase.getCategory(id)).willReturn(category);
        given(categoryMapper.categoryDtoToCategory(category)).willReturn(categoryDTO);
        ResponseEntity<CategoryDTO> expectation=new ResponseEntity<>(categoryDTO, HttpStatus.OK);
        ResponseEntity<CategoryDTO> result=categoryController.getCategory(id);
        assertEquals(expectation,result);
    }
    @DisplayName("Unit Test for getCategory with invalid id")
    @Test
    void shouldThrowBadRequestWhenGivenInvalidIdToGetCategory(){
        UUID categoryId = UUID.randomUUID();
        when(categoryUseCase.getCategory(categoryId)).thenThrow(new UnexpectedTypeException("Bad argument"));
        assertThrows(ResponseStatusException.class, () -> categoryController.getCategory(categoryId));
    }
    @DisplayName("Unit Test for getCategory with infounded id")
    @Test
    void shouldThrowNoSuchElementWhenGivenInExistantIdToGetCategory(){
        UUID categoryId = UUID.randomUUID();
        when(categoryUseCase.getCategory(categoryId)).thenThrow(new NoSuchElementException("Category not found"));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> categoryController.getCategory(categoryId));
        assertEquals(404, exception.getBody().getStatus());
    }
    @DisplayName("Unit Test for getCategory with unExcepted exception")
    @Test
    void shouldThrowExceptionWhenGivenIdToGetCategory(){
        UUID categoryId = UUID.randomUUID();
        when(categoryUseCase.getCategory(categoryId)).thenThrow(new RuntimeException("Unexpected exception"));

        // Act and Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> categoryController.getCategory(categoryId));
        assertEquals(500, exception.getBody().getStatus());
    }
    @DisplayName("Unit Test for listCategories with categories")
    @Test
    void shouldReturnListofCategoriesWhenListCategories(){
        List<Category> categories=new ArrayList<>();
        List<CategoryDTO> categoriesdto=new ArrayList<>();
        UUID id1=UUID.randomUUID();
        Category category1=new Category(id1,"test1","test1","test1");
        CategoryDTO categorydto1=new CategoryDTO(id1,"test1","test1","test1");
        categories.add(category);
        categories.add(category1);
        categoriesdto.add(categoryDTO);
        categoriesdto.add(categorydto1);
        when(categoryUseCase.listCategories()).thenReturn(categories);
        when(categoryMapper.listCategoryToListCategoryDto(categories)).thenReturn(categoriesdto);
        ResponseEntity<List<CategoryDTO>> response = categoryController.listCategories();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals(category1.getId(), response.getBody().get(1).getId());
        assertEquals(category1.getName(), response.getBody().get(1).getName());
        assertEquals(id, response.getBody().get(0).getId());
        assertEquals("test", response.getBody().get(0).getName());
    }
    @DisplayName("Unit Test for listCategories with empty categories")
    @Test
    void shouldReturnEmptyListWhenListCategories() {

        List<Category> categories = new ArrayList<>();
        List<CategoryDTO> categoriesdto=new ArrayList<>();
        when(categoryUseCase.listCategories()).thenReturn(categories);
        when(categoryMapper.listCategoryToListCategoryDto(categories)).thenReturn(categoriesdto);
        ResponseEntity<List<CategoryDTO>> response = categoryController.listCategories();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }
    @DisplayName("Unit Test for listCategories with unexcepted categories")
    @Test
    void shouldThrowExceptionWhenListCategories(){
        when(categoryUseCase.listCategories()).thenThrow(new RuntimeException("Unexpected exception"));
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> categoryController.listCategories());
        assertEquals(500, exception.getBody().getStatus());
    }
    @DisplayName("Unit Test for updateCategory with valid id and category")
    @Test
    void shouldUpdateCategoryWhenUpdateCategoryWithValidCategory(){
        CategoryDTO categoryDTO1= new CategoryDTO(id,"test1","test1","test1");
        Category category1=new Category(id,"test1","test1","test1");
        when(categoryUseCase.updateCategory(category1,id)).thenReturn(category1);
        when(categoryMapper.categoryDtoToCategory(category1)).thenReturn(categoryDTO1);
        when(categoryMapper.categoryToCategoryDto(categoryDTO1)).thenReturn(category1);
        ResponseEntity<CategoryDTO> response = categoryController.updateCategory(categoryDTO1, id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(id, response.getBody().getId());
        assertEquals("test1", response.getBody().getName());

    }
    @DisplayName("Unit Test for updateCategory with invalid Category")
    @Test
    void shouldThrowBadRequestWhenUpdateCategoryWithInvalidCategory(){
        CategoryDTO categoryDTO1= new CategoryDTO(id,null,"test1","test1");
        Category category1=new Category(id,null,"test1","test1");
        when(categoryUseCase.updateCategory(category1,id)).thenThrow(new UnexpectedTypeException("Bad argument"));
        when(categoryMapper.categoryDtoToCategory(category1)).thenReturn(categoryDTO1);
        when(categoryMapper.categoryToCategoryDto(categoryDTO1)).thenReturn(category1);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> categoryController.updateCategory(categoryDTO1, id));
        assertEquals(400, exception.getBody().getStatus());

    }
    @DisplayName("Unit Test for updateCategory with not found Category")
     @Test
    void shouldThrowNotFoundRequestWhenUpdateCategoryWithNotFoundId(){
        CategoryDTO categoryDTO1= new CategoryDTO(id,null,"test1","test1");
        Category category1=new Category(id,null,"test1","test1");
        when(categoryUseCase.updateCategory(category1,id)).thenThrow(new NoSuchElementException("Category not found"));
        when(categoryMapper.categoryDtoToCategory(category1)).thenReturn(categoryDTO1);
        when(categoryMapper.categoryToCategoryDto(categoryDTO1)).thenReturn(category1);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> categoryController.updateCategory(categoryDTO1, id));
        assertEquals(404, exception.getBody().getStatus());
    }
    @DisplayName("Unit Test for updateCategory with unexcepted Exception")
    @Test
    void shouldThrowUnexceptedExceptionWhenUpdateCategory(){
        CategoryDTO categoryDTO1= new CategoryDTO(id,null,"test1","test1");
        Category category1=new Category(id,null,"test1","test1");
        when(categoryUseCase.updateCategory(category1,id)).thenThrow(new RuntimeException("Unexpected exception"));
        when(categoryMapper.categoryDtoToCategory(category1)).thenReturn(categoryDTO1);
        when(categoryMapper.categoryToCategoryDto(categoryDTO1)).thenReturn(category1);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> categoryController.updateCategory(categoryDTO1, id));
        assertEquals(500, exception.getBody().getStatus());

    }


}



