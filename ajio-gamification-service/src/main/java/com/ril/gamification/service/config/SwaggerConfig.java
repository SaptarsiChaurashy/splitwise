/**
 * Copyright (C) Reliance Retail 2016. All rights reserved.
 *
 * <p>This software is the confidential and proprietary information of Reliance Retail. You shall
 * not disclose such Confidential Information and shall use it only in accordance with the terms and
 * conditions entered into with Reliance Retail.
 */
package com.ril.gamification.service.config;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {

  @Bean
  public Docket productApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .paths(regex("/*.*"))
        .build()
        .apiInfo(metaData())
        .securitySchemes(Lists.newArrayList(apiKey(), clientToken()))
        .securityContexts(Arrays.asList(securityContext()));
  }

  private ApiInfo metaData() {
    return new ApiInfoBuilder()
        .title("AJIO GAMIFICATION SERVICE")
        .description("\"AJIO SERVICE FOR API GATEWAY AGGREGATOR\"")
        .version("1.0")
        .contact(new Contact("Saptarsi Chaurashy", "www.ajio.com", "Saptarsi.Chaurashy@ril.com"))
        .build();
  }

  @Override
  protected void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry
        .addResourceHandler("swagger-ui.html")
        .addResourceLocations("classpath:/META-INF/resources/");

    registry
        .addResourceHandler("/webjars/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/");
  }

  @Bean
  public SecurityConfiguration security() {
    return SecurityConfigurationBuilder.builder()
        .scopeSeparator(",")
        .additionalQueryStringParams(null)
        .useBasicAuthenticationWithAccessCodeGrant(false)
        .build();
  }

  private ApiKey apiKey() {
    return new ApiKey("apiKey", "Authorization", "header");
  }

  private ApiKey clientToken() {
    return new ApiKey("clientToken", "X-Client-Type", "header");
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
    return Arrays.asList(
        new SecurityReference("apiKey", authorizationScopes),
        new SecurityReference("clientToken", authorizationScopes));
  }
}
