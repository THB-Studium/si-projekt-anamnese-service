package thb.siprojektanamneseservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "si")
public class AppSettings {
    private String authorizationUsername;
    private String authorizationResourceId;
    private String authorizationSecret;
    private int accessTokenValidity;
    private boolean initData;
}
