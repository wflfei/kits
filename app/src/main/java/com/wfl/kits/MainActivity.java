package com.wfl.kits;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wfl.kits.commons.BaseActivity;
import com.wfl.kits.commons.utils.Views;
import com.wfl.kits.module.Module;
import com.wfl.kits.module.Moudles;
import com.wfl.kits.overscroll.OverActivity;
import static com.wfl.kits.commons.utils.DisplayUtil.dp2px;

import java.util.List;

public class MainActivity extends BaseActivity {
    Context mActivity;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton fab;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        setContentView(R.layout.activity_main);
        toolbar = Views.find(this, R.id.toolbar);
        setSupportActionBar(toolbar);


        fab = Views.find(this, R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        recyclerView = Views.find(this, R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new ModuleAdapter());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_overscroll) {
            Intent intent = new Intent(this, OverActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private class ModuleAdapter extends RecyclerView.Adapter<ViewHolder> {
        List<Module> modules;
        public ModuleAdapter() {
            Moudles moudlesManager = Moudles.getInstance(mActivity);
            modules = moudlesManager.getModules();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.module_item, null));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Module module = modules.get(position);
            holder.itemView.setTag(module);
            holder.titleTv.setText(module.getName());
        }

        @Override
        public int getItemCount() {
            return modules == null ? 0 : modules.size();
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTv;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(mItemOnClickListener);
            itemView.setPadding(dp2px(mActivity, 20), dp2px(mActivity, 20), dp2px(mActivity, 20), dp2px(mActivity, 20));
            titleTv = (TextView) itemView.findViewById(R.id.textView);
        }
    }

    private View.OnClickListener mItemOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Module module = (Module) v.getTag();
            if (module != null) {
                module.start(mActivity);
            }
        }
    };
}
