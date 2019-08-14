package com.pjhu.medicine.product.application.service.command;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BatchUpdateCommand {

    private MultipartFile file;
}
