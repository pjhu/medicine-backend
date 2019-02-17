package com.pjhu.medicine.application;

import org.apache.commons.io.ByteOrderMark;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class ResponseCreator {
    private ResponseCreator() {
        throw new IllegalStateException("Utility class");
    }

    public static ResponseEntity<byte[]> createAttachmentResponse(String fileName, byte[] data) {
        byte[] dataWithBom = ArrayUtils.addAll(ByteOrderMark.UTF_8.getBytes(), data);
        return create(fileName, MediaType.TEXT_PLAIN, dataWithBom);
    }

    public static ResponseEntity<byte[]> craeteXlsxResponse(String fileName, byte[] data) {
        return create(fileName,
                MediaType.valueOf("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
                data);
    }

    private static ResponseEntity<byte[]> create(String fileName, MediaType mimeType, byte[] data) {
        return ResponseEntity.ok()
                .contentType(mimeType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName)
                .contentLength(data.length)
                .body(data);
    }
}
