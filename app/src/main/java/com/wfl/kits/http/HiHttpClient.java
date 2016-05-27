package com.wfl.kits.http;

import android.os.AsyncTask;
import android.support.v4.view.ViewPager;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by wfl on 16/3/31.
 */
public class HiHttpClient {
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE = 1;
    private static volatile HiHttpClient mClient;


    private Executor threadPool = Executors.newSingleThreadExecutor();




    public static HiHttpClient getInstance() {
        if (mClient == null) {
            synchronized (HiHttpClient.class) {
                if (null == mClient) {
                    mClient = new HiHttpClient();
                }
            }
        }
        return mClient;
    }




    public void get(String url) {

    }

    public void post(String url) {

    }
}
