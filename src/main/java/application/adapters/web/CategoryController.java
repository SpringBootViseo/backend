package application.adapters.web;

import application.domain.Category;
import application.port.in.CategoryUseCase;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryController {
    @Autowired
    private CategoryUseCase categoryUseCase;
    @PostMapping
    Category createCategory(@RequestBody Category category){
        return categoryUseCase.createCategory(category);
    }
    @GetMapping("/{id}")
    Category getCategory(@PathVariable(name = "id") UUID id){
        return categoryUseCase.getCategory(id);
    }
    @GetMapping("/list")
    List<Category> listCategories(){
        System.out.println("can access to the controller");
        return categoryUseCase.listCategories();
    }
    @PutMapping("/{id}")
    Category updateCategory(@RequestBody Category category,@PathVariable(name = "id") UUID id){
        return categoryUseCase.updateCategory(category,id);

    }
}
