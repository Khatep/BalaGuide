package kz.balaguide;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class BalaGuideApplication {

    public static void main(String[] args) {
        SpringApplication.run(BalaGuideApplication.class, args);
    }

}