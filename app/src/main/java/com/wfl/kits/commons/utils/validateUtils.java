package com.wfl.kits.commons.utils;

/**
 * Created by wfl on 16/4/11.
 */
public class validateUtils {

    public static boolean checkNotNull(Object object, String message) {
        if (object == null) {
            throw new RuntimeException(message);
        }
        return false;
    }
}
