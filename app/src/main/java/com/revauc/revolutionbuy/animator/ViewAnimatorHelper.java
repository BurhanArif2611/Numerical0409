package com.revauc.revolutionbuy.animator;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;

public class ViewAnimatorHelper {
    /**
     * Initial delay before to show items - in ms
     */
    private static final int INIT_DELAY = 500;

    /**
     * Initial entrance tension parameter.
     * See https://facebook.github.io/rebound/
     */
    private static final int INIT_TENSION = 150;
    /**
     * Initial entrance friction parameter.
     */
    private static final int INIT_FRICTION = 25;

    /**
     * Scroll entrance animation tension parameter.
     */
    private static final int SCROLL_TENSION = 250;
    /**
     * Scroll entrance animation friction parameter.
     */
    private static final int SCROLL_FRICTION = 30;


    private int mHeight;
    private RecyclerView mRecyclerView;
    private SpringSystem mSpringSystem;

    private boolean mFirstViewInit = true;
    private int mLastPosition = -1;
    private int mStartDelay;

    public ViewAnimatorHelper(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mSpringSystem = SpringSystem.create();

        // Use height of RecyclerView to slide-in items from bottom.
        mHeight = mRecyclerView.getResources().getDisplayMetrics().heightPixels;

        mStartDelay = INIT_DELAY;
    }

    public void onCreateViewHolder(View item) {
        /**
         * mFirstViewInit is used because we only want to show animation once at initialization.
         * (onCreateViewHolder can be called after if you use multiple view types).
         */
        if (mFirstViewInit) {
            slideInBottom(item, mStartDelay, INIT_TENSION, INIT_FRICTION);
            mStartDelay += 70;
        }
    }

    public void onBindViewHolder(View item, int position) {
        /**
         * After init, animate once item by item when user scroll down.
         */
        if (!mFirstViewInit && position > mLastPosition) {
            slideInBottom(item, 0, SCROLL_TENSION, SCROLL_FRICTION);
            mLastPosition = position;
        }
    }

    private void slideInBottom(final View item,
                               final int delay,
                               final int tension,
                               final int friction) {
        // Move item far outside the RecyclerView
        item.setTranslationY(mHeight);

        Runnable startAnimation = new Runnable() {
            @Override
            public void run() {
                SpringConfig config = new SpringConfig(tension, friction);
                Spring spring = mSpringSystem.createSpring();
                spring.setSpringConfig(config);
                spring.addListener(new SimpleSpringListener() {
                    @Override
                    public void onSpringUpdate(Spring spring) {
                        /**
                         * Decrease translationY until 0.
                         */
                        float val = (float) (mHeight - spring.getCurrentValue());
                        item.setTranslationY(val);
                    }

                    @Override
                    public void onSpringEndStateChange(Spring spring) {
                        mFirstViewInit = false;
                    }
                });

                // Set the spring in motion; moving from 0 to height
                spring.setEndValue(mHeight);
            }
        };

        mRecyclerView.postDelayed(startAnimation, delay);
    }


    public static void slideInBottom(final View item , final int mHeight) {
        // Move item far outside the RecyclerView
        //final View item = this.getView();
       // final int mHeight = 2000;
       // final int mHeight = 2000;
        item.setTranslationY(mHeight);
        final SpringSystem mSpringSystem = SpringSystem.create();
        Runnable startAnimation = new Runnable() {
            @Override
            public void run() {
                SpringConfig config = new SpringConfig(SCROLL_TENSION, SCROLL_FRICTION);
                Spring spring = mSpringSystem.createSpring();
                spring.setSpringConfig(config);
                spring.addListener(new SimpleSpringListener() {
                    @Override
                    public void onSpringUpdate(Spring spring) {
                        /**
                         * Decrease translationY until 0.
                         */

                      //  LogUtils.LOGE("spring" , "Value"+ spring.getCurrentValue());
                        float val = (float) (mHeight - spring.getCurrentValue());
                        item.setTranslationY(val);
                    }

                    @Override
                    public void onSpringEndStateChange(Spring spring) {
                        //   mFirstViewInit = false;
                    }
                });

                // Set the spring in motion; moving from 0 to height
                spring.setEndValue(mHeight);
            }
        };

        item.postDelayed(startAnimation, 0);
    }
}
