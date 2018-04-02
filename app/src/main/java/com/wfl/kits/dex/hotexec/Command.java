package com.wfl.kits.dex.hotexec;

import android.widget.Toast;

import com.wfl.kits.app.MyApp;

/**
 * Created by wfl on 2017/12/19.
 */

public class Command {
    
    
    public void run() {
        Toast.makeText(MyApp.getInstance().getAppContext(), "hot toast", Toast.LENGTH_SHORT).show();
    }
}
