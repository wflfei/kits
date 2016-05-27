package com.wfl.kits.commons.utils;

import android.content.Context;

/**
 * Created by wfl on 16/3/16.
 */
public class DisplayUtil {

    public static int dp2px(Context context, float dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }

}
