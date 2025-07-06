package org.csu.healthsystem.pojo.PO;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "deepseek")
@Data
@Component
public class DeepSeekProps {
    private String apiKey, baseUrl, model;
}
