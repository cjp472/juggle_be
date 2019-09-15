package com.juggle.juggle.common.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PolicyConditions;
import com.juggle.juggle.common.data.OSSParams;
import com.juggle.juggle.common.data.OSSPolicy;

import java.io.InputStream;
import java.util.Date;

public class AliyunOssUtil {
    private OSSParams params;

    public AliyunOssUtil(OSSParams params) {
        this.params = params;
    }

    public String uploadFile(InputStream fileContent, String fileName) throws Exception{
        fileName = new Date().getTime() + fileName.substring(fileName.lastIndexOf("."));
        OSSClient client = new OSSClient("oss-cn-shenzhen.aliyuncs.com", params.getAccessKeyId(), params.getAccessKeySecret());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(fileContent.available());
        objectMetadata.setContentEncoding("utf-8");
        objectMetadata.setCacheControl("no-cache");
        objectMetadata.setContentType(contentType(fileName.substring(fileName.lastIndexOf("."))));
        objectMetadata.setContentDisposition("inline;filename=" + fileName);
        client.putObject(params.getBucketName(), fileName, fileContent, objectMetadata);
        client.shutdown();
        fileContent.close();
        return "https://"+params.getBucketName()+".oss-cn-shenzhen.aliyuncs.com/"+fileName;
    }

    public OSSPolicy getPolicy(long expireTime) throws Exception{
        OSSClient client = new OSSClient("oss-cn-shenzhen.aliyuncs.com", params.getAccessKeyId(), params.getAccessKeySecret());
        try {
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            String postPolicy = client.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = client.calculatePostSignature(postPolicy);
            OSSPolicy policy = new OSSPolicy();
            policy.setEndpoint("oss-cn-shenzhen.aliyuncs.com");
            policy.setBucket(params.getBucketName());
            policy.setAccessId(params.getAccessKeyId());
            policy.setAccessSecret(params.getAccessKeySecret());
            policy.setPolicy(encodedPolicy);
            policy.setPath("https://"+params.getBucketName()+".oss-cn-shenzhen.aliyuncs.com");
            policy.setSignature(postSignature);
            policy.setExpire(expireEndTime);
            return policy;
        } catch (Exception e) {
            throw new Exception("获取OSS签名出错:"+e.getMessage());
        }
    }

    public String contentType(String FilenameExtension) {
        if (FilenameExtension.equals(".BMP") || FilenameExtension.equals(".bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equals(".GIF") || FilenameExtension.equals(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equals(".JPEG") || FilenameExtension.equals(".jpeg") || FilenameExtension.equals(".JPG")
                || FilenameExtension.equals(".jpg") || FilenameExtension.equals(".PNG")
                || FilenameExtension.equals(".png")) {
            return "image/jpeg";
        }
        if (FilenameExtension.equals(".HTML") || FilenameExtension.equals(".html")) {
            return "text/html";
        }
        if (FilenameExtension.equals(".TXT") || FilenameExtension.equals(".txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equals(".VSD") || FilenameExtension.equals(".vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equals(".PPTX") || FilenameExtension.equals(".pptx") || FilenameExtension.equals(".PPT")
                || FilenameExtension.equals(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equals(".DOCX") || FilenameExtension.equals(".docx") || FilenameExtension.equals(".DOC")
                || FilenameExtension.equals(".doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equals(".XML") || FilenameExtension.equals(".xml")) {
            return "text/xml";
        }
        if (FilenameExtension.equals(".apk") || FilenameExtension.equals(".APK")) {
            return "application/octet-stream";
        }
        return "text/html";
    }
}
