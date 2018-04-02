package com.wfl.kits.overscroll;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by wfl on 17/1/12.
 */

@CoordinatorLayout.DefaultBehavior(MyNestedView.MyBehaiver.class)
public class MyNestedView extends FrameLayout {
    
    
    public MyNestedView(Context context) {
        super(context);
    }

    public MyNestedView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyNestedView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyNestedView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    
    
    class MyBehaiver extends CoordinatorLayout.Behavior<MyNestedView> {
        
        
        
    }
}
