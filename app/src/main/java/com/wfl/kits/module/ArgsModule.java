package com.wfl.kits.module;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by wfl on 16/4/11.
 */
public class ArgsModule extends ActivityEntryModule {
    private Bundle args;

    public ArgsModule(String name, Bundle args) {
        super(name);
        this.args = args;
    }

    @Override
    public void start(Context context) {
        Intent intent = new Intent(context, entryActivity);
        if (args != null) {
            intent.putExtras(args);
        }
        context.startActivity(intent);
    }
}
