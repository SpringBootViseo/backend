package application;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"application"})
public class HexagonalApplication {



    public static void main(String[] args) {
        SpringApplication.run(HexagonalApplication.class, args);


    }


//    @Bean
//    public CommandLineRunner commandLineRunner(
//            AuthenticationAdapter service
//    ) {
//        return args -> {
//            var admin = new RegisterRequest("Admin","Admin","admin10@mail.com","password", Role.ADMIN);
//
//            System.out.println("Admin token: " + service.register(admin).getAccessToken());
//
//            var manager = new RegisterRequest("Admin","Admin","prep10@mail.com","password", Role.PREPARATOR);
//
//
//            System.out.println("prep token: " + service.register(manager).getAccessToken());
//
//        };
//    }

}