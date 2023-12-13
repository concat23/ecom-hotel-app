package dev.concat.vab.ecomhotelappbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.swagger2.configuration.Swagger2DocumentationConfiguration;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
@EnableWebMvc
public class SwaggerConfig extends Swagger2DocumentationConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/api/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
                .resourceChain(false)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException, IOException {
                        Resource requestedResource = location.createRelative(resourcePath);
                        return requestedResource.exists() && requestedResource.isReadable() ? requestedResource : new ClassPathResource("/META-INF/resources/webjars/springfox-swagger-ui/index.html");
                    }
                });
    }
    //http://localhost:8080/swagger-ui/index.html
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("dev.concat.vab"))
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .enableUrlTemplating(true)
                .apiInfo(apiInfo())
                .ignoredParameterTypes(HttpServletRequest.class)
                .globalResponseMessage(RequestMethod.GET, Arrays.asList(
                        new ResponseMessageBuilder()
                                .code(500)
                                .message("Internal server error")
                                .build()))
                //.apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, customGetResponseMessages())
                .directModelSubstitute(LocalDate.class, Date.class)
                .directModelSubstitute(LocalDateTime.class, java.util.Date.class)
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()));

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Ecom Hotel")
                .description("This is an awesome API")
                .version("1.0.0")
                .contact(new Contact("VAB", "", "anhbangluckystar@gmail.com"))
                .build();
    }

    private List<ResponseMessage> customGetResponseMessages() {
        return Arrays.asList(
                new ResponseMessageBuilder()
                        .code(200)
                        .message("OK")
                        .build(),
                new ResponseMessageBuilder()
                        .code(404)
                        .message("Object not found")
                        .build()
        );
    }


    @Bean
    public UiConfiguration uiConfig() {
        return UiConfigurationBuilder
                .builder()
                .filter(true)
                .showCommonExtensions(true)
                .displayOperationId(true)
                .displayRequestDuration(true)
                .validatorUrl("")
                .build();
    }


    private ApiKey apiKey() {
        return new ApiKey("X-API-KEY", "api_key", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("X-API-KEY", authorizationScopes));
    }


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/api/swagger-ui/", "/swagger-ui/index.html");
    }

}
