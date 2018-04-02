package com.wfl.kits.dex;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wfl.kits.R;
import com.wfl.kits.commons.BaseActivity;

import java.io.File;

import dalvik.system.DexClassLoader;

public class LoaderTestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader_test);

    }
    
    
    public void loadMyDex(View view) {
        String dexPath = "/mnt/sdcard/test.dex";
        File file = new File(dexPath);
        if (!file.exists()) {
            return;
        }
        String directory = getDir("dexes", MODE_PRIVATE).getAbsolutePath();
        String libraryPath = null;
        
        DexClassLoader dexClassLoader = new DexClassLoader(dexPath, directory, libraryPath, getClassLoader());
        try {
            Class c = dexClassLoader.loadClass("com.wfl.kits.dex.TestImpl");
            TestInterface testInterface = (TestInterface) c.newInstance();
            ((TextView) findViewById(R.id.text)).setText(testInterface.processString("hello"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
