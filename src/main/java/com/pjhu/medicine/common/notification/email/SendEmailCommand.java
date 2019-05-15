package com.pjhu.medicine.common.notification.email;

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
        request.setAccountName(getAccountName());
        request.setFromAlias(getFromAlias());
        request.setAddressType(getAddressType());
        request.setTagName(getTagName());
        request.setReplyToAddress(getReplyToAddress());
        request.setToAddress(String.join(", ", getToAddresses()));
        request.setSubject(getSubject());
        request.setHtmlBody(getHtmlBody());
        return request;
    }

}
