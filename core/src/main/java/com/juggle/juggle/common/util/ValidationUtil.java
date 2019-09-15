package com.juggle.juggle.common.util;

import com.juggle.juggle.framework.common.errors.ServiceException;
import com.juggle.juggle.framework.common.errors.StandardErrors;

public class ValidationUtil {

    public static void valiMobile(String mobile) {
        String reg = "^[1][3,4,5,7,8][0-9]{9}$";
        if(!mobile.matches(reg)) {
            throw new ServiceException(StandardErrors.EXTERNAL_ERROR.getStatus(), "手机格式不正确");
        }
    }

    public static void valiEmail(String email) {
        String reg = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        if(!email.matches(reg)) {
            throw new ServiceException(StandardErrors.EXTERNAL_ERROR.getStatus(), "邮箱格式不正确");
        }
    }

    public static boolean isMobile(String mobile){
        String reg = "^[1][3,4,5,7,8][0-9]{9}$";
        if(mobile.matches(reg)) {
            return true;
        }
        return false;
    }
}
