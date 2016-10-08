package com.wfl.kits.animateScaleType;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wfl.kits.R;
import com.wfl.kits.commons.BaseActivity;

import java.util.Random;

public class AnimatedScaleTypeActivity extends BaseActivity {
    ImageView.ScaleType mScaleType = ImageView.ScaleType.CENTER_CROP;
    Toolbar mToolbar;
    RecyclerView mRecyclerView;
    private int[] images = new int[] {R.drawable.googlelogo, R.drawable.scenery0, R.drawable.googlelogo};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animated_scale_type);
        mToolbar = findView(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mRecyclerView = findView(R.id.recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(new ImagesAdapter());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_animate_scaletype, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.action_fitxy) {
            mScaleType = ImageView.ScaleType.FIT_XY;
        } else if (id == R.id.action_fitcenter) {
            mScaleType = ImageView.ScaleType.FIT_CENTER;
        } else if (id == R.id.action_fitstart) {
            mScaleType = ImageView.ScaleType.FIT_START;
        } else if (id == R.id.action_fitend) {
            mScaleType = ImageView.ScaleType.FIT_END;
        } else if (id == R.id.action_center) {
            mScaleType = ImageView.ScaleType.CENTER;
        } else if (id == R.id.action_centercrop) {
            mScaleType = ImageView.ScaleType.CENTER_CROP;
        } else if (id == R.id.action_centerinside) {
            mScaleType = ImageView.ScaleType.CENTER_INSIDE;
        }
        return super.onOptionsItemSelected(item);
    }

    class ImagesAdapter extends RecyclerView.Adapter<ImageViewHolder> {

        @Override
        public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final AnimatedImageView animatedImageView = new AnimatedImageView(parent.getContext());
//            animatedImageView.setScaleType(ImageView.ScaleType.);
            animatedImageView.setAnimatedScaleType(mScaleType);
            animatedImageView.setAnimDuration(1000);  // default is 500
            animatedImageView.setStartDelay(300);
            RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 500);
            animatedImageView.setLayoutParams(layoutParams);
            animatedImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    animatedImageView.manualAnimate();
                }
            });
            return new ImageViewHolder(animatedImageView);
        }

        @Override
        public void onBindViewHolder(ImageViewHolder holder, int position) {
            AnimatedImageView animatedImageView = (AnimatedImageView) holder.itemView;
            animatedImageView.setAnimatedScaleType(mScaleType);
            animatedImageView.setImageResource(images[new Random().nextInt(3)]);
        }

        @Override
        public int getItemCount() {
            return 10000;
        }

    }

    class ImageViewHolder extends RecyclerView.ViewHolder {

        public ImageViewHolder(View itemView) {
            super(itemView);
        }
    }

}
