package com.github.term_project.global.config;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public record AppProperties(Upload upload, Cors cors) {

    public record Upload(String rootDir, String profileSubdir, String publicUrlPrefix) {}

    public record Cors(List<String> allowedOrigins) {}
}
