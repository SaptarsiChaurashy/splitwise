package com.ril.gamification.service.filter;

import com.ril.api.config.service.ConfigurationService;
import com.ril.gamification.service.constant.Constants;
import com.ril.gamification.service.utils.ConfigurationManager;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class ApplicationFilter extends OncePerRequestFilter {

  private final ConfigurationService configurationService;

  @Override
  public void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {
    String maxAge =
        ConfigurationManager.getOrDefaultFromConfig(
            configurationService, Constants.MAX_AGE, Constants.DEFAULT_MAX_AGE);
    String cacheControl =
        ConfigurationManager.getOrDefaultFromConfig(
            configurationService, Constants.CACHE_CONTROL, Constants.DEFAULT_CACHE_CONTROL);
    response.setHeader(Constants.MAX_AGE_HEADER, maxAge);
    response.setHeader(Constants.CACHE_CONTROL_HEADER, cacheControl);
    chain.doFilter(request, response);
  }
}
