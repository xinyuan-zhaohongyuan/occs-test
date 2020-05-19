package com.knowology.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * <p></p>
 *
 * @author : Conan
 * @date : 2019-07-30 14:37
 **/
@Data
@ConfigurationProperties(prefix = "occs.security")
public class SecurityProperties {

    private List<String> ignoreTokenUrls = new ArrayList<>();

}
