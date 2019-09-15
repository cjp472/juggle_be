package com.juggle.juggle.common.data;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;

import java.io.Serializable;

public class SmsResponse implements Serializable {
    private boolean success;
    private String requestId;
    private String errCode;
    private String errMsg;

    public SmsResponse(com.aliyuncs.exceptions.ClientException e) {
        requestId = e.getRequestId();
        errCode = e.getErrCode();
        errMsg = e.getErrMsg();
        success = false;
    }

    public SmsResponse(SendSmsResponse r) {
        requestId = r.getRequestId();
        success = true;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    @Override
    public String toString() {
        return "SmsResponse [success=" + success + ", requestId=" + requestId + ", errCode=" + errCode + ", errMsg="
                + errMsg + "]";
    }

}
