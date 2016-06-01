package com.wfl.kits.commons;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import static com.wfl.kits.commons.utils.DisplayUtil.dp2px;

/**
 * Created by wfl on 16/4/12.
 */
public class BaseActivity extends AppCompatActivity {

    protected <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }
}
