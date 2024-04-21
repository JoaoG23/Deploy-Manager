package com.deploy.manager.infra.SpringFoxConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.deploy.manager"))
				.paths(PathSelectors.any())
				.build();
	}

//	private ApiInfo metaInfo() {
//
//		ApiInfo apiInfo = new ApiInfo(
//				"Atividades API REST",
//				"API REST de cadastro de atividades.",
//				"1.0",
//				"Terms of Service",
//				new Contact("João VR", "www.una.br/",
//						" "),
//				"Apache License Version 2.0",
//				"https://www.apache.org/licesen.html"
//		);
//
//		return apiInfo;
//	}
}
