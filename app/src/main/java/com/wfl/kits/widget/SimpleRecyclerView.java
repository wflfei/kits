package com.wfl.kits.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wfl.kits.R;
import com.wfl.kits.commons.utils.Logger;

import java.util.List;

/**
 * Created by wfl on 17/1/12.
 */

public class SimpleRecyclerView extends RecyclerView {
    private static final String TAG = "SimpleRecyclerView";
    
    private LayoutManager mLayoutManager;
    private SimpleAdapter mSimpleAdapter;
    
    
    public SimpleRecyclerView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public SimpleRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public SimpleRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }
    
    private void init(Context context, AttributeSet attrs, int defStyle) {
        mLayoutManager = new LinearLayoutManager(context);
        setLayoutManager(mLayoutManager);
        mSimpleAdapter = new SimpleAdapter();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SimpleRecyclerView, defStyle, 0);
        CharSequence[] data = a.getTextArray(R.styleable.SimpleRecyclerView_dataSource);
        a.recycle();
        if (data != null) {
            setData(data);
        }
        this.setAdapter(mSimpleAdapter);
    }

    public void setData(CharSequence[] data) {
        mSimpleAdapter.setData(data);
    }
    
    public void setData(String[] data) {
        mSimpleAdapter.setData(data);
    }
    
    public void setData(List<String> data) {
        mSimpleAdapter.setData(data);
    }
    
    class SimpleAdapter extends Adapter<ViewHolder> {
        
        private CharSequence[] data;

        public void setData(CharSequence[] data) {
            this.data = data;
        }
        
        public void setData(String[] data) {
            this.data = data;
        }
        
        public void setData(List<String> dataList) {
            if (dataList == null) {
                this.data = new String[]{};
            }
            try {
                this.data = (String[]) dataList.toArray();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, null);
            ViewHolder holder = new ViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            String con = position + ":   " + this.data[position];
            Logger.v(TAG, con);
            ((TextView) holder.itemView).setText(con);
        }

        @Override
        public int getItemCount() {
            return this.data == null ? 0 : this.data.length;
        }
    }
    
    
    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
    
}
