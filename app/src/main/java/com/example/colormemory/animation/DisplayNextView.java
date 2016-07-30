package com.example.colormemory.animation;

/**
 * Created by james on 29/7/16.
 * All Right Reserved
 */

import android.view.animation.Animation;
import android.widget.ImageView;

public final class DisplayNextView implements Animation.AnimationListener {
    private boolean mCurrentView;
    ImageView image1;
    ImageView image2;
    private Animation.AnimationListener listener;

    public DisplayNextView(boolean currentView, ImageView image1, ImageView image2, Animation.AnimationListener listener) {
        mCurrentView = currentView;
        this.image1 = image1;
        this.image2 = image2;
        this.listener = listener;
    }

    public void onAnimationStart(Animation animation) {
    }

    public void onAnimationEnd(Animation animation) {
        image1.post(new SwapViews(mCurrentView, image1, image2,listener));
    }

    public void onAnimationRepeat(Animation animation) {
    }
}