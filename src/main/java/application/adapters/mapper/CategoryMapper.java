package application.adapters.mapper;

import application.adapters.persistence.entity.CategoryEntity;
import application.adapters.web.presenter.CategoryDTO;
import application.domain.Category;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.List;

public interface CategoryMapper {
    Category categoryToCategoryEntity(CategoryEntity categoryEntity);
    CategoryEntity categoryEntityToCategory(Category category);
    List<Category> categoryToDocument(MongoCollection<Document> collection);
    List<Category> listCategoryEntityToListCategory(List<CategoryEntity> categoryEntityList);
    List <CategoryEntity> listCategoryToListCategoryEntity(List<Category> categoryList);
    Category categoryToCategoryDto(CategoryDTO categoryDTO);
    CategoryDTO categoryDtoToCategory(Category category);
    List <CategoryDTO> listCategoryToListCategoryDto(List<Category> categoryList);


}
