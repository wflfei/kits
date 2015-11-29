package com.wfl.kits.overscroll;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wfl.kits.R;
import com.wfl.kits.widget.OverScrollListView;

public class OverActivity extends AppCompatActivity {
    private OverScrollListView overScrollListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_over);
        overScrollListView = (OverScrollListView) findViewById(R.id.listView);
        overScrollListView.setAdapter(new OverAdapter());

    }

    private boolean completion(String first, String second) {
        return first.contains(second);

    }

    private class OverAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 100;
        }

        @Override
        public int getItemViewType(int position) {
            return position == 0 ? 0 : 1;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                if (getItemViewType(position) == 0) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.over_header, null);
                } else {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, null);
                }
            }
            if (getItemViewType(position) == 1) {
                ((TextView) convertView.findViewById(android.R.id.text1)).setText("AIYOU" + position);
            }

            return convertView;
        }
    }
}
