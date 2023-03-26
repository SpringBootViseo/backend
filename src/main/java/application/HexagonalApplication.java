package application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"application"})
public class HexagonalApplication {



    public static void main(String[] args) {
        SpringApplication.run(HexagonalApplication.class, args);

    }

}