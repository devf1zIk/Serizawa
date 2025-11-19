package kz.devf1zik.serizawaconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class SerizawaConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SerizawaConfigServerApplication.class, args);
    }
}
