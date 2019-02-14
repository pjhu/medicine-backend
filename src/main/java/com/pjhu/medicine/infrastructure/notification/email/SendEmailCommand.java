package com.pjhu.medicine.infrastructure.notification.email;

import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import lombok.Builder;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Builder
@Getter
public class SendEmailCommand {

    private String accountName;
    private String fromAlias;
    private Integer addressType;
    private String tagName;
    private Boolean replyToAddress;
    @Builder.Default
    private List<String> toAddresses =Collections.emptyList();
    private String subject;
    private String htmlBody;

    public SingleSendMailRequest getRequestFrom() {
        SingleSendMailRequest request = new SingleSendMailRequest();
        request.setAccountName(accountName);
        request.setFromAlias(fromAlias);
        request.setAddressType(addressType);
        request.setTagName(tagName);
        request.setReplyToAddress(replyToAddress);
        //可以给多个收件人发送邮件，收件人之间用逗号分开，批量发信建议使用BatchSendMailRequest方式
        request.setToAddress(String.join(", ", toAddresses));
        request.setSubject(subject);
        request.setHtmlBody(htmlBody);
        return request;

    }

}
