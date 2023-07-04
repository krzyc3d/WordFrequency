package kp.config;

import jakarta.servlet.MultipartConfigElement;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

@Configuration
public class MultipartConfig {
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory multipartConfigFactory = new MultipartConfigFactory();
        long bytesUploadLimit = 107374182400L;
        multipartConfigFactory.setMaxFileSize(DataSize.ofBytes(bytesUploadLimit));
        multipartConfigFactory.setMaxRequestSize(DataSize.ofBytes(bytesUploadLimit));
        return multipartConfigFactory.createMultipartConfig();
    }
}
