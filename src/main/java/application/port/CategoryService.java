package application.port;

import application.domain.Category;
import application.port.in.CategoryUseCase;
import application.port.out.CategoryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CategoryService implements CategoryUseCase {

    private final CategoryPort categoryPort;
    @Override
    public Category createCategory(Category category) {
        return categoryPort.createCategory(category);
    }

    @Override
    public Category getCategory(UUID id) {
        return categoryPort.getCategory(id);
    }

    @Override
    public List<Category> listCategories() {
        return categoryPort.listCategories();
    }

    @Override
    public Category updateCategory(Category category, UUID id) {
        return categoryPort.updateCategory(category,id);
    }

    @Override
    public void deleteCategory(UUID id) {
        categoryPort.deleteCategory(id);
    }


}