package org.gumpframework.web.base.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "gump",ignoreInvalidFields = false)
public class GumpProperties {
    private String adminPath = "/a";
    private String adminPicPath;
    private String clientPath;
    private String homeAddress;
}
