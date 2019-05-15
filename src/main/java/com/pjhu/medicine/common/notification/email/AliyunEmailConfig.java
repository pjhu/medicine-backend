package com.pjhu.medicine.common.notification.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("aliyun.email")
@Setter
@Getter
public class AliyunEmailConfig {

    private String region;
    private String accessKey;
    private String accessSecret;
}
