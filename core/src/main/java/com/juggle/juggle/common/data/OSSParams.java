package com.juggle.juggle.common.data;

import java.io.Serializable;

public class OSSParams implements Serializable {
    private String accessKeyId;

    private String accessKeySecret;

    private String bucketName;

    public String getAccessKeyId(){
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId){
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret(){
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret){
        this.accessKeySecret = accessKeySecret;
    }

    public String getBucketName(){
        return bucketName;
    }

    public void setBucketName(String bucketName){
        this.bucketName = bucketName;
    }
}
