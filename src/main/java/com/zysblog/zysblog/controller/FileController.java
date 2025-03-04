package com.zysblog.zysblog.controller;

import com.zysblog.zysblog.common.api.ResponseWrapper;
import com.zysblog.zysblog.common.util.CloudApi3Util;
import com.zysblog.zysblog.common.util.FileUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/legendBlog/t-admin")
@Slf4j
public class FileController {
    @Autowired
    private FileUtils cosService;

    @PostMapping("/upload")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "File to upload", required = true, dataType = "__file", paramType = "form")
    })
    public ResponseWrapper<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String etag = cosService.uploadFile(file);
            log.info("getFile:{}",etag);
            return CloudApi3Util.getApi3Response("true");
        } catch (Exception e) {
            return CloudApi3Util.getApi3Response("Failed to upload file: " + e.getMessage());
        }
    }

}
