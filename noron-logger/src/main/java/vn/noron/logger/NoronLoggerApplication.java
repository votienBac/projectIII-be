package vn.noron.logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("vn.noron")
public class NoronLoggerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NoronLoggerApplication.class, args);
    }

}
