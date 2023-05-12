package application.port.in;

import application.domain.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryUseCase {
    Category createCategory(Category category);
    Category getCategory(UUID id);
    List<Category> listCategories();
    Category updateCategory(Category category,UUID id);
    void deleteCategory(UUID id);
}
