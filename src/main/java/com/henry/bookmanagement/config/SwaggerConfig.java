package com.henry.bookmanagement.config;


import static springfox.documentation.builders.PathSelectors.regex;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Predicate;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	  public Docket apiDocket() {
	        return new Docket(DocumentationType.SWAGGER_2)
	                .select()
	                .apis(RequestHandlerSelectors.any())
	                .paths(PathSelectors.any())
	                .build();
	    }



	@RestController
	public class SwaggerRedirect {
		@RequestMapping(value = "/api", method = RequestMethod.GET)
		public void method(HttpServletResponse httpServletResponse) {
			httpServletResponse.setHeader("Location", "/swagger-ui.html");
			httpServletResponse.setStatus(302);
		}
	}

	private Predicate<String> multipartPaths() {

		return regex("/v1*");
	}

}