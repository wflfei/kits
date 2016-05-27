package com.wfl.kits.commons.utils;

import android.util.Log;

/**
 * Created by wfl on 16/3/14.
 */
public class Logger {

    public final static boolean IS_OPEN = true;
    private final static String TAG = "Thailand";
    private static final String LOG_PREFIX = "Td_";
    private static final int LOG_PREFIX_LENGTH = LOG_PREFIX.length();
    private static final int MAX_LOG_TAG_LENGTH = 30;

    public static String makeLogTag(String str) {
        if (str.length() > MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH) {
            return LOG_PREFIX + str.substring(0, MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH - 1);
        }

        return LOG_PREFIX + str;
    }

    /**
     *  混淆的类不要使用这个
     */
    public static String makeLogTag(Class cls) {
        return makeLogTag(cls.getSimpleName());
    }

    public static void d(String log) {
        if (IS_OPEN) {
            Log.d(TAG, log);
        }
    }

    public static void d(String tag, String log) {
        if (IS_OPEN) {
            Log.d(tag, log);
        }
    }

    public static void i(String log) {
        if (IS_OPEN) {
            Log.i(TAG, log);
        }
    }

    public static void i(String tag, String log) {
        if (IS_OPEN) {
            Log.i(tag, log);
        }
    }

    public static void w(String log) {
        if (IS_OPEN) {
            Log.w(TAG, log);
        }
    }

    public static void w(String tag, String log) {
        if (IS_OPEN) {
            Log.w(tag, log);
        }
    }

    public static void v(String log){
        if (IS_OPEN) {
            Log.v(TAG, log);
        }
    }

    public static void v(String tag, String log) {
        if (IS_OPEN) {
            Log.v(tag, log);
        }
    }

    public static void e(String log){
        if (IS_OPEN) {
            Log.e(TAG, log);
        }
    }

    public static void e(String tag, String log) {
        if (IS_OPEN) {
            Log.e(tag, log);
        }
    }
}

