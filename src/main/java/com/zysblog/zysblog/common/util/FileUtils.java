package com.zysblog.zysblog.common.util;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
import java.io.File;
import java.io.IOException;

@Service
public class FileUtils {

    @Autowired
    private COSClient cosClient;

    @Value("${tencent.cos.bucketName}")
    private String bucketName;

    public String uploadFile(MultipartFile file) throws IOException {
        String key = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File localFile = File.createTempFile("temp", null);
        file.transferTo(localFile);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        PutObjectResult result = cosClient.putObject(putObjectRequest);
        localFile.delete();
        return result.getETag();
    }
}
