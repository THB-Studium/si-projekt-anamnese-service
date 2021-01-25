package thb.siprojektanamneseservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@EnableJpaAuditing
@EnableResourceServer
@EnableJpaRepositories(basePackages = {"thb.siprojektanamneseservice.repository"})
@EntityScan(basePackages = {"thb.siprojektanamneseservice.model"})
@SpringBootApplication
public class SiProjektAnamneseServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SiProjektAnamneseServiceApplication.class, args);
    }
}
