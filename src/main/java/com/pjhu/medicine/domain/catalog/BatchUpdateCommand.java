package com.pjhu.medicine.domain.catalog;

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
