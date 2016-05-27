package com.wfl.kits.moudle;

import android.content.ComponentName;
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

    private List<Moudle> moudles;

    private Moudles(Context context) {
        moudles = new ArrayList<>();
        moudles.addAll(ManifestMoudles.getMoudles(context));
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

    public List<Moudle> getMoudles() {
        return moudles;
    }

    public void addMoudle(Moudle moudle) {
        if (moudles == null) {
            moudles = new ArrayList<>();
        }
        moudles.add(moudle);
    }

    static class ManifestMoudles {
        public static final String KEY_MOUDLE = "moudle";


        public static List<Moudle> getMoudles(Context context) {
            List<Moudle> moudles = new ArrayList<>();
            PackageManager pm = context.getApplicationContext().getPackageManager();
            try {
                ActivityInfo[] activities = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES | PackageManager.GET_META_DATA).activities;
                for (ActivityInfo activityInfo : activities) {
                    if (isActivityMoudle(activityInfo)) {
                        moudles.add(new SimpleMoudle((String) activityInfo.loadLabel(pm), activityInfo.name));
                    }
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            return moudles;
        }

        private static boolean isActivityMoudle(ActivityInfo activityInfo) {
            if (activityInfo == null) {
                return false;
            }
            Bundle metaData = activityInfo.metaData;
            if (metaData != null && metaData.containsKey(KEY_MOUDLE)) {
                String value = metaData.getString(KEY_MOUDLE);
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
