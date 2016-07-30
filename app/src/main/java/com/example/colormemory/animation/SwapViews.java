package com.example.colormemory.animation;

/**
 * Created by james on 29/7/16.
 * All Right Reserved
 */
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

public final class SwapViews implements Runnable {
    private boolean mIsFirstView;
    ImageView image1;
    ImageView image2;
    private Animation.AnimationListener listener;

    public SwapViews(boolean isFirstView, ImageView image1, ImageView image2,Animation.AnimationListener listener) {
        mIsFirstView = isFirstView;
        this.image1 = image1;
        this.image2 = image2;
        this.listener = listener;
    }

    public void run() {
        final float centerX = image1.getWidth() / 2.0f;
        final float centerY = image1.getHeight() / 2.0f;
        Flip3dAnimation rotation;

        if (mIsFirstView) {
            image1.setVisibility(View.GONE);
            image2.setVisibility(View.VISIBLE);
            image2.requestFocus();

            rotation = new Flip3dAnimation(-90, 0, centerX, centerY);
        } else {
            image2.setVisibility(View.GONE);
            image1.setVisibility(View.VISIBLE);
            image1.requestFocus();

            rotation = new Flip3dAnimation(90, 0, centerX, centerY);
        }

        rotation.setDuration(500);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new DecelerateInterpolator());
        rotation.setAnimationListener(listener);
        if (mIsFirstView) {
            image2.startAnimation(rotation);
        } else {
            image1.startAnimation(rotation);
        }
    }
}