package com.juggle.juggle.common.util;

import com.juggle.juggle.framework.common.utils.MD5Utils;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/*
 * 微信支付加密工具类
 */
public class WxPaySignatureUtils {

    //微信支付加密工具
    public static String signature(Map<String, String> map, String key) {
        Set<String> keySet = map.keySet();
        String[] str = new String[map.size()];
        StringBuilder tmp = new StringBuilder();
        str = keySet.toArray(str);
        Arrays.sort(str);
        for (int i = 0; i < str.length; i++) {
            String t = str[i] + "=" + map.get(str[i]) + "&";
            tmp.append(t);
        }
        if (null != key) {
            tmp.append("key=" + key);
        }
        return MD5Utils.encript(tmp.toString()).toUpperCase();
    }

    //微信支付加密工具
    public static String signatureSHA1(Map<String, String> map) throws Exception {
        Set<String> keySet = map.keySet();
        String[] str = new String[map.size()];
        StringBuilder tmp = new StringBuilder();
        // 进行字典排序
        str = keySet.toArray(str);
        Arrays.sort(str);
        for (int i = 0; i < str.length; i++) {
            String t = str[i] + "=" + map.get(str[i]) + "&";
            tmp.append(t);
        }

        String tosend = tmp.toString().substring(0, tmp.length() - 1);
        MessageDigest md = null;
        byte[] bytes = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
            bytes = md.digest(tosend.getBytes("utf-8"));
        } catch (Exception e) {
            throw e;
        }
        String singe = byteToStr(bytes);
        return singe.toLowerCase();

    }

    public static String sha1Check(String[] str) throws Exception{
        StringBuilder tmp = new StringBuilder();
        Arrays.sort(str);
        for (int i = 0; i < str.length; i++) {
            String t = str[i];
            tmp.append(t);
        }
        String tosend = tmp.toString();
        MessageDigest md = null;
        byte[] bytes = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
            bytes = md.digest(tosend.getBytes("utf-8"));
        } catch (Exception e) {
            throw e;
        }

        String singe = byteToStr(bytes);
        return singe.toUpperCase();
    }

    //字节数组转换为字符串
    public static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    //字节转换为字符串
    public static String byteToHexStr(byte mByte) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
                'B', 'C', 'D', 'E', 'F' };
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];

        String s = new String(tempArr);
        return s;
    }

}