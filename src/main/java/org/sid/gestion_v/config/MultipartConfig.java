package org.sid.gestion_v.config;

import jakarta.servlet.MultipartConfigElement;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

@Configuration
public class MultipartConfig {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofMegabytes(20)); // Limite par fichier
        factory.setMaxRequestSize(DataSize.ofMegabytes(40)); // Total des fichiers
        factory.setFileSizeThreshold(DataSize.ofKilobytes(512)); // seuil mémoire
        return factory.createMultipartConfig();
    }
}
