package gov.goias.pin;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Log4j2
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket colaboradorApi() {
        log.trace("Adicionando Api Swagger para o grupo Colaborador");
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("colaborador-api")
                /* Necessario caso esteja usando LocalDate(java 8) nos pojos do servicos */
                .directModelSubstitute(LocalDate.class, java.util.Date.class)
                /* Necessario caso esteja usando LocalDateTime(java 8) nos pojos do servicos */
                .directModelSubstitute(LocalDateTime.class, java.util.Date.class)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(colaboradorPaths())
                .build()
                .apiInfo(apiInfo());
    }
    
    @SuppressWarnings("unchecked")
	private Predicate<String> colaboradorPaths() {
        log.trace("Retornando paths válidos para Colaborador");
        return or(
                regex("/api/colaboradores.*"),
                regex("/api/colaboradores")
        );
    }

    @Bean
    public Docket unidadeAdministrativaApi() {
        log.trace("Adicionando Api Swagger para o grupo Unidade Administrativa");
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("unidade-api")
                /* Necessario caso esteja usando LocalDate(java 8) nos pojos do servicos */
                .directModelSubstitute(LocalDate.class, java.util.Date.class)
                /* Necessario caso esteja usando LocalDateTime(java 8) nos pojos do servicos */
                .directModelSubstitute(LocalDateTime.class, java.util.Date.class)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(unidadeAdminstrativaPaths())
                .build()
                .apiInfo(apiInfo());
    }

    private Predicate<String> unidadeAdminstrativaPaths() {
        log.trace("Retornando paths válidos para Unidades Administrativas");
        return or(
                regex("/api/unidades.*"),
                regex("/api/unidades")
        );
    }

    private ApiInfo apiInfo() {
        /*
         * Exemplo de Dados, adeque ao seu projeto e equipe
         */
        log.trace("Retornando Builder de Informações da API");
        return new ApiInfoBuilder()
                .title("PIN - API Exemplo de Arquitetura e Tecnologia")
                .description("PIN - API do Sistema Exemplo de Arquitetura e Tecnologia. Para exemplificar o Pin Goiás que é baseado no OpenAPI(Swagger) versão Java.")
                .termsOfServiceUrl("http://springfox.io")
                .contact(new Contact("Supart"
                        , "https://github.com/OAI/OpenAPI-Specification/blob/master/versions/2.0.md"
                        , "supart@segplan.go.gov.br"))
                .license("Apache License Version 2.0")
                .licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE")
                .version("2.0.0")
                .build();
    }

}