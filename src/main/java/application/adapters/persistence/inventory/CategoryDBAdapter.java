package application.adapters.persistence.inventory;

import application.adapters.persistence.inventory.entity.CategoryEntity;
import application.domain.Category;
import application.port.out.CategoryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class CategoryDBAdapter implements CategoryPort {
    private CategoryRepository categoryRepository;

    @Override
    public Category createCategory(Category category) {
        CategoryEntity categoryEntity= new CategoryEntity(category.getId(),category.getName(), category.getLinkImg(), category.getLinkImgBanner());
        CategoryEntity savedCategory=categoryRepository.save(categoryEntity);
        Category categoryreturned=new Category(savedCategory.getId(),savedCategory.getName(),savedCategory.getLinkImg(),savedCategory.getLinkImgBanner());
        return categoryreturned;
    }

    @Override
    public Category getCategory(UUID id) {
        CategoryEntity returnedCategory=categoryRepository.findById(id).get();
        Category resultedCategory=new Category(returnedCategory.getId(),returnedCategory.getName(),returnedCategory.getLinkImg(),returnedCategory.getLinkImgBanner());
        return resultedCategory;
    }

    @Override
    public List<Category> listCategories() {
        System.out.println("can access to db adapter");
        //List<CategoryEntity> categoryEntityList=categoryRepository.findAll();
        Iterable<CategoryEntity> categoryEntityList;
        categoryEntityList = categoryRepository.findAll();
        System.out.println("all found");
        List<Category> categoryList=new ArrayList<>();
        for (CategoryEntity categoryEntity:categoryEntityList
             ) {
            System.out.println("can add product");
            categoryList.add(new Category(categoryEntity.getId(),categoryEntity.getName(),categoryEntity.getLinkImg(),categoryEntity.getLinkImgBanner()));

        }
        return categoryList;
    }

    @Override
    public Category updateCategory(Category category, UUID id) {
        CategoryEntity categoryEntity = new CategoryEntity(id, category.getName(), category.getLinkImg(),category.getLinkImgBanner());
        CategoryEntity savedCategory=categoryRepository.save(categoryEntity);
        Category categoryreturned=new Category(savedCategory.getId(),savedCategory.getName(),savedCategory.getLinkImg(),savedCategory.getLinkImgBanner());
        return categoryreturned;
    }


}
