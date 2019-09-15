package com.juggle.juggle.api.v1.platform;

import com.juggle.juggle.common.data.OSSParams;
import com.juggle.juggle.common.util.AliyunOssUtil;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.primary.setting.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/v1/platform/oss")
public class OSSApi {
    @Autowired
    private DictionaryService dictionaryService;

    @RequestMapping(value = "/upload",method = {RequestMethod.POST})
    public @ResponseBody Object upload(MultipartFile[] file) throws Exception{
        OSSParams ossParams = dictionaryService.getOssDictionaries();
        AliyunOssUtil aliyunOssUtil = new AliyunOssUtil(ossParams);
        List<String> uris = new ArrayList<>();
        for (MultipartFile multipartFile : file) {
            String uri = aliyunOssUtil.uploadFile(multipartFile.getInputStream(),multipartFile.getOriginalFilename());
            uris.add(uri);
        }
        return ApiResponse.make(uris);
    }

    @RequestMapping(value="/signature",method = {RequestMethod.POST})
    public @ResponseBody Object signature() throws Exception {
        OSSParams ossParams = dictionaryService.getOssDictionaries();
        AliyunOssUtil aliyunOssUtil = new AliyunOssUtil(ossParams);
        return ApiResponse.make(aliyunOssUtil.getPolicy(600));
    }
}
