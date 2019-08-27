package com.pjhu.medicine.delaytask.application.adapter.persisitence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.amqp.rabbit.connection.CorrelationData;

@Getter
@Setter
@AllArgsConstructor
public class SpecialCorrelationData extends CorrelationData {

    private volatile Object message;
    private String exchange;
    private String routingKey;
    private int retryCount = 0;

    public SpecialCorrelationData() {
        super();
    }

    public SpecialCorrelationData(String id) {
        super(id);
    }

    public SpecialCorrelationData(String id, Object data) {
        this(id);
        this.message = data;
    }
}
