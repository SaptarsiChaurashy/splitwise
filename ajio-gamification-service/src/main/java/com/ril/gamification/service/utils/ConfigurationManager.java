package com.ril.gamification.service.utils;

import com.ril.api.config.exception.ConfigurationNotFoundException;
import com.ril.api.config.service.ConfigurationService;
import com.ril.integration.api.dao.domain.ApplicationConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public final class ConfigurationManager<T> {
  private final ConfigurationService configurationService;

  public static Boolean getOrDefaultFromConfig(
      ConfigurationService configurationService, String key, boolean defaultValue) {
    try {
      ApplicationConfiguration configuration = configurationService.getConfigurationDetails(key);
      return Boolean.valueOf(configuration.getValue());
    } catch (ConfigurationNotFoundException ex) {
      log.warn(
          "Configured value not found for key '{}', reason : {}/n falling back to default value : '{}'",
          key,
          ex.getMessage(),
          defaultValue);
    }
    return defaultValue;
  }

  public static String getOrDefaultFromConfig(
      ConfigurationService configurationService, String key, String defaultValue) {
    try {
      ApplicationConfiguration configuration = configurationService.getConfigurationDetails(key);
      return configuration.getValue();
    } catch (ConfigurationNotFoundException ex) {
      log.warn(
          "Configured value not found for key '{}', reason : {}/n falling back to default value : '{}'",
          key,
          ex.getMessage(),
          defaultValue);
    }
    return defaultValue;
  }

  public static String get(ConfigurationService configurationService, String key) {
    return getOrDefaultFromConfig(configurationService, key, null);
  }

  public Boolean getOrDefaultFromConfig(String key, boolean defaultValue) {
    return getOrDefaultFromConfig(configurationService, key, defaultValue);
  }

  public String getOrDefaultFromConfig(String key, String defaultValue) {
    return getOrDefaultFromConfig(configurationService, key, defaultValue);
  }

  public int get(String key, int defaultValue) {
    try {
      ApplicationConfiguration configuration = configurationService.getConfigurationDetails(key);
      return Integer.valueOf(configuration.getValue());
    } catch (ConfigurationNotFoundException ex) {
      log.warn(
          "Configured value not found for key '{}', reason : {}/n falling back to default value : '{}'",
          key,
          ex.getMessage(),
          defaultValue);
    }
    return defaultValue;
  }

}
