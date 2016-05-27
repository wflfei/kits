package com.wfl.kits.moudle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by wfl on 16/4/11.
 */
public class ArgsMoudle extends ActivityEntryMoudle {
    private Bundle args;

    public ArgsMoudle(String name, Bundle args) {
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
