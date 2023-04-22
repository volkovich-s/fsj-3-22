package com.example.intermediate_certification.configs;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.io.IOException;

@Component
public class ContentConfig implements WebMvcConfigurer {
  @Value("${content.images}")
  private String images;
  public String getImages() {
    return images;
  }
  private Path getFullPath(@NonNull String path) {
    return Paths.get(getImages() + path);
  }
  public void SaveImage(@NonNull String path, @NonNull byte[] content) throws IOException {
    final var full_path = getFullPath(path);
    Files.createDirectories(full_path.getParent());
    if(Files.notExists(full_path))
      Files.createFile(full_path);
    Files.write(full_path, content);
  }
  public void DeleteImage(@NonNull String path) {
    try {
      Files.delete(getFullPath(path));
    }
    catch (IOException e) {

    }
  }
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry resourceHandlerRegistry) {
    resourceHandlerRegistry.addResourceHandler("/img/**").addResourceLocations("file:///" + images);
  }
}
