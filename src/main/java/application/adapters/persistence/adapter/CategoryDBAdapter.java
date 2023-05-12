package application.adapters.persistence.adapter;

import application.adapters.mapper.mapperImpl.CategoryMapperImpl;
import application.adapters.persistence.MongoConfig;
import application.adapters.persistence.entity.CategoryEntity;
import application.adapters.persistence.repository.CategoryRepository;
import application.domain.Category;
import application.port.out.CategoryPort;




import com.mongodb.client.MongoCollection;


import lombok.AllArgsConstructor;
import org.bson.Document;


import org.springframework.stereotype.Component;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Component
@AllArgsConstructor
public class CategoryDBAdapter implements CategoryPort {
    private CategoryRepository categoryRepository;
    private CategoryMapperImpl categoryMapper;
    private final MongoConfig mongoConfig;

    @Override
    public Category createCategory(Category category) {
        CategoryEntity categoryEntity= categoryMapper.categoryEntityToCategory(category);
        CategoryEntity savedCategory=categoryRepository.save(categoryEntity);
        return categoryMapper.categoryToCategoryEntity(savedCategory);
    }

    @Override
    public Category getCategory(UUID id) {
        if(categoryRepository.findById(id).isPresent()) {
            CategoryEntity returnedCategory = categoryRepository.findById(id).get();
            return categoryMapper.categoryToCategoryEntity(returnedCategory);
        }
        else{
            throw new NoSuchElementException("This element doesn't exist");
        }
    }

    @Override
    public List<Category> listCategories() {


        MongoCollection<Document> collection =  mongoConfig.getAllDocuments("Categories");



        return categoryMapper.categoryToDocument(collection);
    }

    @Override
    public Category updateCategory(Category category, UUID id) {
        if(categoryRepository.findById(id).isPresent()) {
            category.setId(id);
            CategoryEntity categoryEntity = categoryMapper.categoryEntityToCategory(category);
            CategoryEntity savedCategory=categoryRepository.save(categoryEntity);
            return categoryMapper.categoryToCategoryEntity(savedCategory);
        }
        else{
            throw new NoSuchElementException("This element doesn't exist");
        }

    }
    @Override
    public void deleteCategory(UUID id) {
        categoryRepository.deleteById(id);
    }


}
