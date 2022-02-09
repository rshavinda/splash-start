package com.acentura.splashstart.util;

import java.util.List;

public class HelperUtil {

    public static <T> boolean isDataAvailable(T data){
        if(data == null) return false;
        if(data instanceof List<?>){
            return !((List<?>) data).isEmpty();
        }
        return true;
    }

}
