package application.adapters.mapper.mapperImpl;

import application.adapters.persistence.entity.CategoryEntity;
import application.adapters.mapper.CategoryMapper;
import application.adapters.web.presenter.CategoryDTO;
import application.domain.Category;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class CategoryMapperImpl implements CategoryMapper {
    @Override
    public Category categoryToCategoryEntity(CategoryEntity categoryEntity) {
        return new Category(categoryEntity.getId(),categoryEntity.getName(),categoryEntity.getLinkImg(),categoryEntity.getLinkImgBanner());
    }

    @Override
    public CategoryEntity categoryEntityToCategory(Category category) {
        return new CategoryEntity(category.getId(),category.getName(), category.getLinkImg(), category.getLinkImgBanner());
    }

    @Override
    public List<Category> categoryToDocument(MongoCollection<Document> collection) {
        List<Category> categoryList= new ArrayList<>();
        for(Document doc: collection.find()){
            categoryList.add(new Category(doc.get("_id", UUID.class),doc.getString("name"),doc.getString("linkImg"),doc.getString("linkImgBanner")));
        }
        return categoryList;
    }

    @Override
    public List<Category> listCategoryEntityToListCategory(List<CategoryEntity> categoryEntityList) {
        List<Category> categoryList=new ArrayList<>();
        for (CategoryEntity categoryEntity:categoryEntityList
        ) {
            categoryList.add(this.categoryToCategoryEntity(categoryEntity));
        }
        return categoryList;
    }

    @Override
    public List<CategoryEntity> listCategoryToListCategoryEntity(List<Category> categoryList) {
        List<CategoryEntity> categoryEntityList=new ArrayList<>();
        for (Category category:categoryList
        ) {
            categoryEntityList.add(this.categoryEntityToCategory(category));
        }
        return categoryEntityList;
    }

    @Override
    public Category categoryToCategoryDto(CategoryDTO categoryDTO) {
        return new Category(categoryDTO.getId(),categoryDTO.getName(),categoryDTO.getLinkImg(),categoryDTO.getLinkImgBanner());
    }

    @Override
    public CategoryDTO categoryDtoToCategory(Category category) {
        return new CategoryDTO(category.getId(),category.getName(),category.getLinkImg(),category.getLinkImgBanner());
    }

    @Override
    public List<CategoryDTO> listCategoryToListCategoryDto(List<Category> categoryList) {
        List<CategoryDTO> categoryDtoList=new ArrayList<>();
        for (Category category:categoryList
        ) {
            categoryDtoList.add(this.categoryDtoToCategory(category));
        }
        return categoryDtoList;
    }
}
