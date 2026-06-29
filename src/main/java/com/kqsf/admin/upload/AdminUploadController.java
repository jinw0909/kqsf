package com.kqsf.admin.upload;

import com.kqsf.global.s3.S3UploadService;
import com.kqsf.global.s3.UploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin/uploads")
@RequiredArgsConstructor
public class AdminUploadController {

    private final S3UploadService s3UploadService;

    @PostMapping("/image")
    @ResponseBody
    public ResponseEntity<UploadResponse> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam(defaultValue = "products") String dir
    ) {
        String url = s3UploadService.uploadImage(file, dir);
        return ResponseEntity.ok(new UploadResponse(url));
    }
}
