package application.adapters.persistence.inventory;

import application.adapters.persistence.inventory.entity.CategoryEntity;
import application.domain.Category;
import application.port.out.CategoryPort;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.internal.MongoClientImpl;
import lombok.AllArgsConstructor;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
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

        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017");

        // Create a new MongoClientSettings object
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        // Create a new MongoClient object using the settings
        MongoClient mongoClient = MongoClients.create(settings);

        // Get the database instance from the client
        MongoDatabase database = mongoClient.getDatabase("octopus");

        MongoCollection<Document> collection = database.getCollection("category");
        MongoCollection<CategoryEntity> categoryEntityList;
        Document myDoc = collection.find().first();
        System.out.println(myDoc.toJson());
        List<Category> categoryList=new ArrayList<>();
       /* for (CategoryEntity categoryEntity:categoryEntityList
             ) {
            System.out.println("can add product");
            categoryList.add(new Category(categoryEntity.getId(),categoryEntity.getName(),categoryEntity.getLinkImg(),categoryEntity.getLinkImgBanner()));

        }*/
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
