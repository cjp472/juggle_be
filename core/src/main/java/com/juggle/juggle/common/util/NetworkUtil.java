package com.juggle.juggle.common.util;

import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

@Service
public class NetworkUtil {
    //发起get网络请求
    public JSONObject httpGet(String url) throws Exception{
        URI uri = URI.create(url);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(uri);
        httpget.setHeader(new BasicHeader("Accept", "text/plain;charset=utf-8"));
        httpget.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));
        HttpResponse result = httpclient.execute(httpget);
        HttpEntity httpEntity = result.getEntity();
        String resultStr = EntityUtils.toString(httpEntity,"utf-8");
        JSONObject jb = JSONObject.fromObject(resultStr);
        return jb;
    }

    //发起post网络请求
    public Map<String,String> httpPost(String url, String data) throws Exception{
        URI uri = URI.create(url);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(uri);
        httppost.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));
        httppost.setHeader(new BasicHeader("Accept", "text/plain;charset=utf-8"));
        httppost.setEntity(new StringEntity(data, "utf-8"));
        HttpResponse result = httpclient.execute(httppost);
        HttpEntity httpEntity = result.getEntity();
        String resultStr = EntityUtils.toString(httpEntity,"utf-8");
        XMLUtil xmlUtil= new XMLUtil();
        Map<String, String> map = xmlUtil.parseXml(resultStr);
        return map;
    }

    //生成随时编号
    public String getCode(){
        String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
                "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
                "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
                "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
                "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                "W", "X", "Y", "Z" };
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();

    }
}
