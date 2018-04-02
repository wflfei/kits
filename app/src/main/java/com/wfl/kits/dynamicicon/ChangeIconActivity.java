package com.wfl.kits.dynamicicon;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.wfl.kits.R;
import com.wfl.kits.commons.BaseActivity;

public class ChangeIconActivity extends BaseActivity {
    private ComponentName mComponentDefault, mComponent1, mComponent2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_icon);
        
        mComponentDefault = new ComponentName(this, getPackageName() + ".MainActivity");
        mComponent1 = new ComponentName(this, getPackageName() + ".Icon1Act");
        mComponent2 = new ComponentName(this, getPackageName() + ".Icon2Act");
        
    }

    public void onChangeIconDefault(View v) {
        enableComponent(mComponentDefault);
        disableComponent(mComponent1);
        disableComponent(mComponent2);
    }
    
    public void onChangeIcon1(View v) {
        enableComponent(mComponent1);
        disableComponent(mComponentDefault);
        disableComponent(mComponent2);
    }

    public void onChangeIcon2(View v) {
        enableComponent(mComponent2);
        disableComponent(mComponentDefault);
        disableComponent(mComponent1);
    }
    
    private void enableComponent(ComponentName componentName) {
        PackageManager pm = getPackageManager();
        pm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }

    private void disableComponent(ComponentName componentName) {
        PackageManager pm = getPackageManager();
        pm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }
}
