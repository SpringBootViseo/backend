package application.port;

import application.domain.Category;
import application.port.in.CategoryUseCase;
import application.port.out.CategoryPort;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CategoryService implements CategoryUseCase {

    private final CategoryPort categoryPort;
    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Override
    public Category createCategory(Category category) {
        logger.info("Creating category: {}", category);
        return categoryPort.createCategory(category);
    }

    @Override
    public Category getCategory(UUID id) {
        logger.info("Getting category with ID: {}", id);
        return categoryPort.getCategory(id);
    }

    @Override
    public List<Category> listCategories() {
        logger.info("Listing categories");
        return categoryPort.listCategories();
    }

    @Override
    public Category updateCategory(Category category, UUID id) {
        logger.info("Updating category (ID: {}): {}", id, category.toString());
        return categoryPort.updateCategory(category, id);
    }

    @Override
    public void deleteCategory(UUID id) {
        logger.warn("Deleting category with ID: {}", id);
        categoryPort.deleteCategory(id);
    }
}
