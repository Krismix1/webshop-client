package dk.cristi.app.webshop.client.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

// From http://www.baeldung.com/swagger-2-documentation-for-spring-rest-api
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    // The configuration of Swagger mainly centers around the Docket bean.

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select() // select() method returns an instance of ApiSelectorBuilder, which provides a way to control the endpoints exposed by Swagger.
                .apis(RequestHandlerSelectors.any()) // TODO: 25-Mar-18 Consider filtering API for Swagger
                .paths(PathSelectors.any()) // Using any() for both will make documentation for your entire API available through Swagger.
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("E-Commerce REST API")
                .description("API for a web shop")
                .version("1.0") // FIXME: 06-Aug-18 Extract version from Maven
                .contact(new Contact("Cristian Betivu", "", "cbetivu@gmail.com"))
                .build();
    }
}
