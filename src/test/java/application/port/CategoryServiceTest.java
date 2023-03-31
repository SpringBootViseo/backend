package application.port;

import application.domain.Category;
import application.port.out.CategoryPort;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class CategoryServiceTest {
    @InjectMocks
    CategoryService categoryService;
    @Mock
    CategoryPort categoryPort;
    Category category;
    UUID id;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        id= UUID.randomUUID();
        category=new Category(id,"test","test","test");

    }

    @AfterEach
    void tearDown() {
        id=null;
        category=null;
    }

    @Test
    void shouldCallCreateCategoryOnceWhenCreateCategory() {
        given(categoryPort.createCategory(category)).willReturn(category);
        Category result=categoryService.createCategory(category);
        verify(categoryPort,times(1)).createCategory(any());
        assertEquals(result,category);
    }

    @Test
    void shouldCallGetCategoryOnceWhenGetCategory() {
        given(categoryPort.getCategory(id)).willReturn(category);
        Category result=categoryService.getCategory(id);
        verify(categoryPort,times(1)).getCategory(any());
        assertEquals(result,category);
    }

    @Test
    void shouldCallListCategoriesOnceAndReturnListCategoriesWhenlistCategories() {
        Category category1 =new Category(id,"test1","test1","test1");
        List<Category> categoryList=new ArrayList<>();
        categoryList.add(category);
        categoryList.add(category1);
        given(categoryPort.listCategories()).willReturn(categoryList);
        List<Category> result=categoryService.listCategories();
        verify(categoryPort,times(1)).listCategories();
        assertEquals(result,categoryList);
    }

    @Test
    void shouldCallupdateCategoryOnceAndUpdateCategoryWhenUpdateCategory() {
        UUID id1=UUID.randomUUID();
        Category categoryarg =new Category(id1,"test1","test1","test1");
        Category categoryexp=new Category(id,"test","test","test");
        given(categoryPort.updateCategory(categoryarg,id)).willReturn(categoryexp);
        Category result=categoryService.updateCategory(categoryarg,id);
        verify(categoryPort,times(1)).updateCategory(any(),any());
        assertEquals(result,categoryexp);
    }
}