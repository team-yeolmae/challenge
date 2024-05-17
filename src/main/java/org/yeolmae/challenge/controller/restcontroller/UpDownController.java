package org.yeolmae.challenge.controller.restcontroller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.yeolmae.challenge.domain.dto.upload.UploadFileResponse;
import net.coobird.thumbnailator.Thumbnailator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequestMapping("/api/v1")
@Log4j2
public class UpDownController {

    @Value("${org.yeolmae.upload.path}")
    private String uploadPath;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "이미지 업로드", description = "POST로 파일 등록")
    public ResponseEntity<List<UploadFileResponse>> upload(
            @Parameter(content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE, array = @ArraySchema(schema = @Schema(type = "string", format = "binary"))))
            @RequestPart("files") MultipartFile[] files) {

        if (files != null) {

            final List<UploadFileResponse> responses = new ArrayList<>();

            for(MultipartFile multipartFile : files) {
                String uuid = UUID.randomUUID().toString();
                String originalName = multipartFile.getOriginalFilename();

                Path savedPath = Paths.get(uploadPath, uuid + "_" + originalName);

                // image 여부 초기화(default = false)
                boolean isImage = false;

                try {
                    // 실제 파일 저장
                    multipartFile.transferTo(savedPath);

                    // 저장된 파일이 MIME 유형인지 확인
                    if(Files.probeContentType(savedPath).startsWith("image")) {
                        isImage = true;
                        File thumbFile = new File(uploadPath, "thumb_" + uuid + "_" + originalName);
                        Thumbnailator.createThumbnail(savedPath.toFile(), thumbFile, 600, 600);
                    }

                } catch(IOException e) {
                    log.error(e.getMessage());
                }

                responses.add(UploadFileResponse.builder()
                        .uuid(uuid)
                        .fileName(originalName)
                        .img(isImage)
                        .build());

            }

            return ResponseEntity.ok(responses);
        }
        return ResponseEntity.badRequest().build();
    }


    // 첨부파일 조회
    @GetMapping(value = "/view/{fileName}")
    @Operation(summary = "이미지 파일 조회", description = "GET 방식으로 파일 조회")
    public ResponseEntity<Resource> getViewFile(@PathVariable String fileName) {

        Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);

        if(!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        String resourceName = resource.getFilename();

        // http 헤더 설정 : MIME 타입을 확인하고(proveContentType 메소드 사용), 해당 MIME 타입을 http 응답 헤더에 추가
        HttpHeaders headers = new HttpHeaders();

        try {
            headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));

        } catch (Exception e) {

            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok().headers(headers).body(resource);
    }


    // 첨부파일 삭제
    @DeleteMapping(value = "/remove/{fileName}")
    @Operation(summary = "이미지 파일 삭제", description = "DELETE 방식으로 파일 조회")
    public Map<String, Boolean> removeFile(@PathVariable String fileName) {

        Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);

        String resourceName = resource.getFilename();

        Map<String, Boolean> response = new HashMap<>();
        boolean isRemoved = false;

        try {
            String contentType = Files.probeContentType(resource.getFile().toPath());
            isRemoved = resource.getFile().delete();

            // 썸네일 존재 시
            if(contentType.startsWith("image")) {
                File thumb = new File(uploadPath + File.separator + "thumb_" + fileName);
                thumb.delete();

            }
        } catch(Exception e) {
            log.error(e.getMessage());
        }

        response.put("result", isRemoved);

        return response;
    }


}