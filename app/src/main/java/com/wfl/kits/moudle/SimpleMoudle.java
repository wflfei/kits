package com.wfl.kits.moudle;


import android.app.Activity;

/**
 * Created by wfl on 16/4/11.
 */
public class SimpleMoudle extends ActivityEntryMoudle {
    public SimpleMoudle(String name) {
        super(name);
    }

    public SimpleMoudle(String name, Class<Activity> tClass) {
        super(name, tClass);
    }

    public SimpleMoudle(String name, String entryClassName) {
        super(name, entryClassName);
    }
}
