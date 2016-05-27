package com.wfl.kits.moudle;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

/**
 * Created by wfl on 16/4/11.
 */
public abstract class ActivityEntryMoudle implements Moudle{
    protected String name;
    protected Class<Activity> entryActivity;
    protected String entryClassName;


    public ActivityEntryMoudle(String name) {
        this.name = name;
    }

    public ActivityEntryMoudle(String name, Class<Activity> tClass) {
        this(name);
        this.entryActivity = tClass;
    }

    public ActivityEntryMoudle(String name, String entryClassName) {
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
