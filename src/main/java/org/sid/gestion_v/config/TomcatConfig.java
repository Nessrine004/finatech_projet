package org.sid.gestion_v.config;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatConfig {

    @Bean
    public ServletWebServerFactory servletContainer() {
        return new TomcatServletWebServerFactory() {
            @Override
            protected void customizeConnector(Connector connector) {
                super.customizeConnector(connector);

                // 💡 Débloquer les limites de fichiers
                connector.setProperty("fileCountMax", "10"); // Jusqu'à 10 fichiers autorisés
                connector.setMaxPostSize(20971520); // 20 MB
                connector.setProperty("maxSwallowSize", "20971520");
            }
        };
    }
}
