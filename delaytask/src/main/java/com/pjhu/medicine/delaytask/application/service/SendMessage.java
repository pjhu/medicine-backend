package com.pjhu.medicine.delaytask.application.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SendMessage implements Serializable {
    private static final long serialVersionUID = -4731326195678504565L;

    private long id;

    private String type;
}
