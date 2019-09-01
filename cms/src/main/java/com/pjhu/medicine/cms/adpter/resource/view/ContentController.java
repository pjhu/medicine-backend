package com.pjhu.medicine.cms.adpter.resource.view;

import com.pjhu.medicine.cms.application.service.ContentApplicationService;
import com.pjhu.medicine.cms.application.service.ContentQueryService;
import com.pjhu.medicine.cms.application.service.command.ContentCreateCommand;
import com.pjhu.medicine.cms.application.service.command.ContentUpdateCommand;
import com.pjhu.medicine.cms.application.service.response.ContentResponse;
import com.pjhu.medicine.cms.domain.model.ContentType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.pjhu.medicine.common.utils.Constant.ADMIN;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = ADMIN + "/contents", produces = APPLICATION_JSON_UTF8_VALUE)
public class ContentController {

    private final ContentApplicationService contentApplicationService;
    private final ContentQueryService contentQueryService;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody ContentCreateCommand contentCreateCommand) {
        Long contentId = contentApplicationService.create(contentCreateCommand);
        return ResponseEntity.accepted().body(contentId);
    }

    @PostMapping("/{contentId}")
    public ResponseEntity<Object> update(@PathVariable Long contentId,
                                         @RequestBody ContentUpdateCommand contentUpdateCommand) {
        contentUpdateCommand.setContentId(contentId);
        contentApplicationService.update(contentUpdateCommand);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{contentId}/publish")
    public ResponseEntity<Object> publish(@PathVariable Long contentId) {
        contentApplicationService.publish(contentId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{contentId}/archive")
    public ResponseEntity<Object> archive(@PathVariable Long contentId) {
        contentApplicationService.archive(contentId);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/{contentId}/delete")
    public ResponseEntity<Object> delete(@PathVariable Long contentId) {
        contentApplicationService.delete(contentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public Page<ContentResponse> findContentsBy(String contentType, Pageable pageable) {
        return contentQueryService.findContentsForAdmin(ContentType.valueOf(contentType), pageable);
    }
}
