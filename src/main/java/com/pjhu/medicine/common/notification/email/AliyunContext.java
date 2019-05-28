package com.pjhu.medicine.common.notification.email;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AliyunContext {

    @Bean
    public DefaultAcsClient aliyunEmailTemplate(AliyunEmailConfig config) {
        IClientProfile profile = DefaultProfile.getProfile(config.getRegion(), config.getAccessKey(), config.getAccessSecret());
        return new DefaultAcsClient(profile);
    }
}
