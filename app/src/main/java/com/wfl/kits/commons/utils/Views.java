package com.wfl.kits.commons.utils;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wfl on 15/11/15.
 */
public class Views {
    public static <T extends View> T find(ViewGroup viewGroup, int id) {
        return (T) viewGroup.findViewById(id);
    }

    public static <T extends View> T find(Activity activity, int id) {
        return (T) activity.findViewById(id);
    }
}
