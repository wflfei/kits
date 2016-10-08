package com.wfl.kits.module;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wfl on 16/4/11.
 */
public class Moudles {
    private static volatile Moudles instance;

    private List<Module> modules;

    private Moudles(Context context) {
        modules = new ArrayList<>();
        modules.addAll(ManifestModules.getModules(context));
    }

    public static Moudles getInstance(Context context) {
        if (instance == null) {
            synchronized (Moudles.class) {
                if (instance == null) {
                    instance = new Moudles(context);
                }
            }
        }
        return instance;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void addMoudle(Module module) {
        if (modules == null) {
            modules = new ArrayList<>();
        }
        modules.add(module);
    }

    static class ManifestModules {
        public static final String KEY_MODULE = "module";


        public static List<Module> getModules(Context context) {
            List<Module> modules = new ArrayList<>();
            PackageManager pm = context.getApplicationContext().getPackageManager();
            try {
                ActivityInfo[] activities = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES | PackageManager.GET_META_DATA).activities;
                for (ActivityInfo activityInfo : activities) {
                    if (isActivityModule(activityInfo)) {
                        modules.add(new SimpleModule((String) activityInfo.loadLabel(pm), activityInfo.name));
                    }
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            return modules;
        }

        private static boolean isActivityModule(ActivityInfo activityInfo) {
            if (activityInfo == null) {
                return false;
            }
            Bundle metaData = activityInfo.metaData;
            if (metaData != null && metaData.containsKey(KEY_MODULE)) {
                String value = metaData.getString(KEY_MODULE);
                if (value == null) {
                    return false;
                }
                return true;
            } else {
                return false;
            }
        }
    }
}
