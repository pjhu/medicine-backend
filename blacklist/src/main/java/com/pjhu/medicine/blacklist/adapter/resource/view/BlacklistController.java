package com.pjhu.medicine.blacklist.adapter.resource.view;

import com.pjhu.medicine.blacklist.application.service.BlacklistApplicationService;
import com.pjhu.medicine.blacklist.application.service.command.AddToBlackListCommand;
import com.pjhu.medicine.blacklist.application.service.command.CheckInBlacklistCommand;
import com.pjhu.medicine.blacklist.application.service.response.CheckInBlacklistResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.pjhu.medicine.common.utils.Constant.ADMIN;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = ADMIN + "/members", produces = APPLICATION_JSON_UTF8_VALUE)
public class BlacklistController {

    private final BlacklistApplicationService blacklistApplicationService;

    @PostMapping("/blacklists")
    public ResponseEntity<Object> addToBlackList(@RequestBody AddToBlackListCommand command) {
        blacklistApplicationService.addToBlackList(command);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/blacklists/check")
    public CheckInBlacklistResponse checkInBlackList(@RequestBody CheckInBlacklistCommand command) {
        return blacklistApplicationService.checkInBlackList(command);
    }
}
