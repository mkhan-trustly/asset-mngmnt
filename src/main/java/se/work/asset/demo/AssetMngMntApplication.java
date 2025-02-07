package se.work.asset.demo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Asset management API", version = "2.0", description = "Asset management"))
@Slf4j
public class AssetMngMntApplication {

    public static void main(String[] args) {
        SpringApplication.run(AssetMngMntApplication.class, args);
    }
}
