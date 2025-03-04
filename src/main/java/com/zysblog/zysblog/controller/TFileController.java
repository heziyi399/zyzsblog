package com.zysblog.zysblog.controller;

import java.time.LocalDateTime;
import com.zysblog.zysblog.common.api.ResponseWrapper;
import com.zysblog.zysblog.common.util.CloudApi3Util;
import com.zysblog.zysblog.common.util.FileUtils;
import com.zysblog.zysblog.common.util.JwtUtil;
import com.zysblog.zysblog.entity.TFile;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 文件表 前端控制器
 * </p>
 *
 * @author zy
 * @since 2024-12-03
 */
@RestController
@RequestMapping("/legendBlog/file")
public class TFileController {
    @Autowired
    private FileUtils cosService;

    @PostMapping("/upload")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "上传文件", required = true, dataType = "__file", paramType = "form")
    })
    public ResponseWrapper<String> uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        try {
            String url = cosService.uploadFile(file);
            String token = request.getHeader("Authorization");
            Map<String, Object> claims = JwtUtil.parseToken(token);
            String uid = (String) claims.get("userId");
            TFile userFile = new TFile();
            userFile.setPicUrl(url);
            userFile.setUid(uid);
            userFile.setFileOldName(file.getOriginalFilename());
            userFile.setCreateTime(LocalDateTime.now());
            userFile.setPicName(file.getName());
            userFile.setFileSize(file.getSize());
            userFile.insert();
            return CloudApi3Util.getApi3Response(url);
        } catch (Exception e) {
            return CloudApi3Util.getApi3Response("Failed to upload file: " + e.getMessage());
        }
    }
}
