package com.pjhu.medicine.infrastructure.notification.email;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.exceptions.ClientException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AliyunEmailClient {

    private final DefaultAcsClient aliyunEmailTemplate;

    public void send(SendEmailCommand command) {
        SingleSendMailRequest request = command.getRequestFrom();
        try {
            aliyunEmailTemplate.getAcsResponse(request);
        } catch (ClientException e) {
            log.error("send email failed", e);
            e.printStackTrace();
        }
    }
}
