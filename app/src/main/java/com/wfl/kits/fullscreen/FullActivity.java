package com.wfl.kits.fullscreen;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.wfl.kits.R;

public class FullActivity extends AppCompatActivity {
    static final String TAG = "FullActivity";
    String path = "sd";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate");
        setContentView(R.layout.activity_full);

        setFullScreen();



    }


    private void setFullScreen() {
        View content = findViewById(R.id.content_view);
//        content.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    public void openTranslucentActivity(View v) {
        path = "hi";
        startActivityForResult(new Intent(this, TranslucentActivity.class), 5);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.v(TAG, "onSaveInstanceState");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.v(TAG, "onConfigurationChanged");
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            LogUtil.v("AlbumActivity", "orientation changed: ORIENTATION_LANDSCAPE");
            //nothing
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
//            LogUtil.v("AlbumActivity", "orientation changed: ORIENTATION_PORTRAIT");
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.v(TAG, "onNewIntent");
        Toast.makeText(FullActivity.this, "onNewIntent", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy");
        Toast.makeText(FullActivity.this, "Oh,Im destoryed", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.v(TAG, "onActivityResult, path=" + path);
        Toast.makeText(FullActivity.this, "onResult: " + requestCode, Toast.LENGTH_SHORT).show();
    }
}
