package com.pjhu.medicine.delaytask.application.adapter.resouce.view;

import com.pjhu.medicine.delaytask.application.service.DelayTaskApplication;
import com.pjhu.medicine.delaytask.application.service.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.pjhu.medicine.common.utils.Constant.ADMIN;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = ADMIN + "/delay-task", produces = APPLICATION_JSON_UTF8_VALUE)
public class DelayTaskController {

    private final DelayTaskApplication delayTaskApplication;

    @PostMapping
    public ResponseEntity<String> sendMsg(SendMessage sendMessage) {
        String id = delayTaskApplication.create(sendMessage);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }
}
