package br.bruno.greenmiledesafio.docs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.RelativePathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletContext;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket apiDoc(ServletContext servletContext){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.bruno.greenmiledesafio.controller"))
                .paths(regex("/v1.*"))
                .build()
            .useDefaultResponseMessages(false)
            .apiInfo(metaData())
            .pathProvider(new RelativePathProvider(servletContext){
                @Override
                public String getApplicationBasePath() {
                    return "https://api-desafio-bruno.herokuapp.com/v1/";
                }
            });
    }

    private ApiInfo metaData(){
        return new ApiInfoBuilder()
                .title("Greenmile - Desafio")
                .description("Sistema de gestão de horas trabalhadas. " +
                        "O sistema é composto por duas entidades: Usuário e Horas Trabalhadas. " +
                        "Todas as operações de leitura não requerem autenticação, bem como todas " +
                        "as de escrita requerem")
                .version("1.0")
                .contact(new Contact("Bruno Carvalho",
                        "http://linkedin.com/in/brunocarvalho7",
                        "brunocarvalho287@gmail.com"))
                .build();
    }

}
