package com.shaqcc.rotatablecard.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * A layout that can rotate by click
 * <p>
 * Created by shaqCc on 2017/1/19.
 */

public class RotatableLayout extends ViewGroup implements View.OnClickListener {
    private static final String TAG = "RotatableLayout";
    private View mFrontView;//front view
    private View mBackView;//behind view
    private int mChildCount;//child count
    private int mSpecWidth;
    private int mSpecHeight;
    private boolean isRotated = false;//is behind view at front
    private boolean playReverse = false;//play animation reverse
    private boolean isAnimatProgress = false;//is animation playing
    private float startDegree = 0;
    private float endDegree = -180;

    /**
     * constructor
     *
     * @param context
     */
    public RotatableLayout(Context context) {
        this(context, null);
    }

    /**
     * constructor
     *
     * @param context
     * @param attrs
     */
    public RotatableLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * constructor
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public RotatableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * measure child view and viewgroup
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mChildCount = getChildCount();
        if (mChildCount != 2)
            throw new IllegalArgumentException("you must specify 2 child and your childcount is" + mChildCount);

        mSpecWidth = MeasureSpec.getSize(widthMeasureSpec);
        mSpecHeight = MeasureSpec.getSize(heightMeasureSpec);
        int childwidthSpec = MeasureSpec.makeMeasureSpec(mSpecWidth, MeasureSpec.AT_MOST);
        int childheightSpec = MeasureSpec.makeMeasureSpec(mSpecHeight, MeasureSpec.AT_MOST);

        for (int i = 0; i < mChildCount; i++) {
            View child = getChildAt(i);
            measureChild(child, childwidthSpec, childheightSpec);
        }

        if (mBackView == null) mBackView = getChildAt(0);
        if (mFrontView == null) mFrontView = getChildAt(1);

        setMeasuredDimension(mSpecWidth, mSpecHeight);
    }

    /**
     * click events
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (!isAnimatProgress) {
            startObjectAnim(isRotated);
            isRotated = !isRotated;
        }
    }

    /**
     * reverse layout animator
     *
     * @param isReverse true: front->behind
     *                  false:behind->front
     */
    private void startObjectAnim(final boolean isReverse) {
        isAnimatProgress = true;
        initAnim(isReverse);

        ObjectAnimator rotationX = ObjectAnimator.ofFloat(this, "rotationX", startDegree, endDegree);
        rotationX.setDuration(500);
        rotationX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedFraction = animation.getAnimatedFraction();
                if (animatedFraction >= 0.5) {
                    if (isReverse) {
                        mFrontView.setVisibility(VISIBLE);
                        mBackView.setVisibility(GONE);
                    } else {
                        mFrontView.setVisibility(GONE);
                        mBackView.setVisibility(VISIBLE);
                    }
                    if (animatedFraction == 1) isAnimatProgress = false;
                }
            }
        });
        rotationX.start();
    }


    /**
     * init Animation
     *
     * @param isReverse
     */
    private void initAnim(boolean isReverse) {
        if (isReverse) {
            startDegree = -180;
            endDegree = 0;
        } else {
            startDegree = 0;
            endDegree = -180;
        }
        if (mBackView.getRotationX() == 0) reverseView(mBackView);
    }

    /**
     * init behind view
     *
     * @param view
     */
    private void reverseView(View view) {
        float rotationX = view.getRotationX();
        if (rotationX == 0) {
            int height = view.getMeasuredHeight();
            view.setPivotY(height / 2);
            view.setRotationX(-180);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int cl = 0, ct = 0, cr = mSpecWidth, cb = mSpecHeight;
        for (int i = 0; i < mChildCount; i++) {
            View childAt = getChildAt(i);
            Log.d(TAG, "layout area：" + cl + "--" + ct + "--" + cr + "--" + cb);
            childAt.layout(cl, ct, cr, cb);
        }
        this.setOnClickListener(this);
    }


}
