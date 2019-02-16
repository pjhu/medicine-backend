package com.pjhu.medicine;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.pjhu.medicine.infrastructure.notification.email.AliyunEmailConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MedicineApplicationContext {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public DefaultAcsClient aliyunEmailTemplate(AliyunEmailConfig config) {
        IClientProfile profile = DefaultProfile.getProfile(config.getRegion(), config.getAccessKey(), config.getAccessSecret());
        return new DefaultAcsClient(profile);
    }
}
