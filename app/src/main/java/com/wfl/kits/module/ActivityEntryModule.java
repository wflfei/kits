package com.wfl.kits.module;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

/**
 * Created by wfl on 16/4/11.
 */
public abstract class ActivityEntryModule implements Module {
    protected String name;
    protected Class<Activity> entryActivity;
    protected String entryClassName;


    public ActivityEntryModule(String name) {
        this.name = name;
    }

    public ActivityEntryModule(String name, Class<Activity> tClass) {
        this(name);
        this.entryActivity = tClass;
    }

    public ActivityEntryModule(String name, String entryClassName) {
        this(name);
        this.entryClassName = entryClassName;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void start(Context context) {
        if (entryActivity != null) {
            context.startActivity(new Intent(context, entryActivity));
        } else if (!TextUtils.isEmpty(entryClassName)) {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(context.getPackageName(), entryClassName));
            context.startActivity(intent);
        }

    }
}
