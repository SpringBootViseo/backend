package application.adapters.persistence.adapter;

import application.adapters.mapper.mapperImpl.CategoryMapperImpl;
import application.adapters.persistence.entity.CategoryEntity;
import application.adapters.persistence.repository.CategoryRepository;
import application.domain.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;


class CategoryDBAdapterTest {
    @InjectMocks
    private CategoryDBAdapter categoryDBAdapter;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapperImpl categoryMapper;

    private Category category;
    private CategoryEntity categoryEntity;
    private UUID id;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        id=UUID.randomUUID();
        category=new Category(id,"test","test","test");
        categoryEntity=new CategoryEntity(id,"test","test","test");
    }

    @AfterEach
    void tearDown() {
        id=null;
        category=null;
        categoryEntity=null;
    }
    @DisplayName("Unit Test for create category")
    @Test
    void shouldReturnCategoryWhenCreateCategoryWithCategory(){
        given(categoryMapper.categoryEntityToCategory(category)).willReturn(categoryEntity);
        given(categoryMapper.categoryToCategoryEntity(categoryEntity)).willReturn(category);
        given(categoryRepository.save(categoryEntity)).willReturn(categoryEntity);
        assertEquals(categoryDBAdapter.createCategory(category),category);
    }
    @DisplayName("Unit Test for getCategory with a valid id")
    @Test
    void shouldReturnCategoryWhenGetCategoryWithValidId(){
        given(categoryRepository.findById(id)).willReturn(Optional.of(categoryEntity));
        given(categoryMapper.categoryToCategoryEntity(categoryEntity)).willReturn(category);
        assertEquals(categoryDBAdapter.getCategory(id),category);
    }
    @DisplayName("Unit Test for getCategory with invalid id")
    @Test
    void shouldThrowNoSuchElementExceptionWhenGetCategoryWithInvalidId(){
        given(categoryRepository.findById(id)).willReturn(Optional.empty());
        assertThrows(NoSuchElementException.class,()->{
            categoryDBAdapter.getCategory(id);
        });
    }
    @DisplayName("Unit Test for updateCategory with valid id")
    @Test
    void shouldUpdateCategoryWhenUpdateCategoryWithValidId(){
        UUID id1=UUID.randomUUID();
        Category category2= new Category(id1,"test1","test1","test1");

        Category category1= new Category(id,"test1","test1","test1");
        CategoryEntity categoryEntity1= new CategoryEntity(id,"test1","test1","test1");
        given(categoryRepository.findById(id)).willReturn(Optional.of(categoryEntity));
        given(categoryMapper.categoryEntityToCategory(category1)).willReturn(categoryEntity1);
        given(categoryRepository.save(categoryEntity1)).willReturn(categoryEntity1);
        given(categoryMapper.categoryToCategoryEntity(categoryEntity1)).willReturn(category1);
        assertEquals(categoryDBAdapter.updateCategory(category1,id),category1);

    }



}