package vn.noron.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("vn.noron")
public class NoronApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(NoronApiApplication.class, args);
    }

}
