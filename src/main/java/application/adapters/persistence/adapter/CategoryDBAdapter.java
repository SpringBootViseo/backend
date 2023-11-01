package application.adapters.persistence.adapter;

import application.adapters.mapper.mapperImpl.CategoryMapperImpl;
import application.adapters.persistence.MongoConfig;
import application.adapters.persistence.entity.CategoryEntity;
import application.adapters.persistence.repository.CategoryRepository;
import application.domain.Category;
import application.port.out.CategoryPort;




import com.mongodb.client.MongoCollection;


import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private final Logger logger = LogManager.getLogger(CategoryDBAdapter.class);

    @Override
    public Category createCategory(Category category) {
        logger.info("Create Category with id : {}",category.getId());
        CategoryEntity categoryEntity= categoryMapper.categoryEntityToCategory(category);
        CategoryEntity savedCategory=categoryRepository.save(categoryEntity);
        logger.info("Category with id {} created successfully", savedCategory.getId());
        return categoryMapper.categoryToCategoryEntity(savedCategory);
    }

    @Override
    public Category getCategory(UUID id) {
        logger.trace("Checking if category with id: {} exists", id);
        if(categoryRepository.findById(id).isPresent()) {
            logger.debug("Getting Category with id : {}",id);
            CategoryEntity returnedCategory = categoryRepository.findById(id).get();
            return categoryMapper.categoryToCategoryEntity(returnedCategory);
        }
        else{
            logger.error("Category with id : {} doesn't exist",id);
            throw new NoSuchElementException("This element doesn't exist");
        }
    }

    @Override
    public List<Category> listCategories() {

        logger.info("Listing All Categories");
        MongoCollection<Document> collection =  mongoConfig.getAllDocuments("Categories");
        List<Category> categories = categoryMapper.categoryToDocument(collection);

        logger.debug("Listed {} categories", categories.size());
        return categories;
    }

    @Override
    public Category updateCategory(Category category, UUID id) {
        if(categoryRepository.findById(id).isPresent()) {
            category.setId(id);
            CategoryEntity categoryEntity = categoryMapper.categoryEntityToCategory(category);
            CategoryEntity savedCategory=categoryRepository.save(categoryEntity);
            logger.info("Category with id {} updated successfully", savedCategory.getId());
            return categoryMapper.categoryToCategoryEntity(savedCategory);
        }
        else{
            logger.error("Category with id: {} doesn't exist, cannot update", id);
            throw new NoSuchElementException("This element doesn't exist");
        }

    }
    @Override
    public void deleteCategory(UUID id) {
        categoryRepository.deleteById(id);
        logger.info("Category with id {} has been deleted", id);
    }


}