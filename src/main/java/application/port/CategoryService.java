package application.port;

import application.domain.Category;
import application.port.in.CategoryUseCase;
import application.port.out.CategoryPort;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CategoryService implements CategoryUseCase {
    private final static Logger logger= LogManager.getLogger(CategoryService.class);

    private final CategoryPort categoryPort;
    @Override
    public Category createCategory(Category category) {
        logger.info("Create category "+category.toString());
        return categoryPort.createCategory(category);
    }

    @Override
    public Category getCategory(UUID id) {
        logger.info("Get category with id "+id);
        return categoryPort.getCategory(id);
    }

    @Override
    public List<Category> listCategories() {
        logger.info("Get list of all categories");
        return categoryPort.listCategories();
    }

    @Override
    public Category updateCategory(Category category, UUID id) {
        logger.info("Update category with id "+id+" to category "+category.toString());
        return categoryPort.updateCategory(category,id);
    }

    @Override
    public void deleteCategory(UUID id) {
        logger.info("Delete category with id "+id);
        categoryPort.deleteCategory(id);
    }


}