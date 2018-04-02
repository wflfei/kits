package com.wfl.kits.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by wfl on 2017/12/19.
 */

public class MyApp {
    
    private static MyApp myApp = null;
    private Context mAppContext = null;
    
    
    private MyApp() {
        
    }
    
    public synchronized static MyApp getInstance() {
        if (myApp == null) {
            myApp = new MyApp();
        }
        return myApp;
    }
    
    public void setApplicationContext(Application appContext) {
        this.mAppContext = appContext;
    }

    public Context getAppContext() {
        return mAppContext;
    }
}
