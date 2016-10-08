package com.wfl.kits.module;


import android.app.Activity;

/**
 * Created by wfl on 16/4/11.
 */
public class SimpleModule extends ActivityEntryModule {
    public SimpleModule(String name) {
        super(name);
    }

    public SimpleModule(String name, Class<Activity> tClass) {
        super(name, tClass);
    }

    public SimpleModule(String name, String entryClassName) {
        super(name, entryClassName);
    }
}
