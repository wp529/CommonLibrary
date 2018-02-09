package com.wp.commonlibrary.image.preview;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import com.github.chrisbanes.photoview.PhotoView;
import com.wp.commonlibrary.R;
import com.wp.commonlibrary.utils.LogUtils;

/**
 * 类今日头条图片预览的ViewPager
 * Created by lijian on 2017/11/22.
 */

public class ImagesPreviewViewPager extends ViewPager {
    private static final String TAG = "ImagesPreviewViewPager";

    private static final float FLING_RATIO = 0.25f;
    private static final int SCROLL_BACK_DURATION = 400;
    private static final int FLING_OUT_DURATION = 600;
    private float mLastMotionX;
    private float mLastMotionY;
    private float mLastDownX;
    private float mLastDownY;
    private float mTouchSlop;
    private int mInitLeft = Integer.MIN_VALUE;
    private int mInitTop = Integer.MIN_VALUE;
    private int mInitBottom = Integer.MIN_VALUE;
    private Scroller mScroller;
    private boolean mScrolling = false;
    private boolean mFlinging = false;
    private int mHeight = -1;
    private ViewGroup mParent;

    private OnPositionChangeListener mPositionListener;
    private DisallowInterruptHandler mDisallowInterruptHandler;
    private TouchState mTouchState = TouchState.NONE;

    public ImagesPreviewViewPager(Context context) {
        this(context, null);
    }

    public ImagesPreviewViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        final ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledTouchSlop();
        mScroller = new Scroller(getContext(), sInterpolator);
        setPageTransformer(false, (page, position) -> {
            final float normalizedposition = Math.abs(Math.abs(position) - 1);
            page.setAlpha(normalizedposition);
        });
        setPageMargin(getResources().getDimensionPixelOffset(R.dimen.common_library_20dp));
        setOverScrollMode(ViewPager.OVER_SCROLL_NEVER);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtils.d(TAG, "dispatchTouchEvent()" + ev);
        if (mFlinging || mScrolling) {
            LogUtils.d(TAG, "not need handle event when view is anim");
            return true;
        }
        if (mDisallowInterruptHandler != null && mDisallowInterruptHandler.disallowInterrupt()) {
            LogUtils.d(TAG, "disallow interrupt,just handle by super");
            return super.dispatchTouchEvent(ev);
        }
        int actionMask = ev.getActionMasked();
        LogUtils.d(TAG, "actionMask=" + actionMask + "mTouchState=" + mTouchState);
        switch (actionMask) {
            case MotionEvent.ACTION_DOWN:
                mTouchState = TouchState.NONE;
                mLastMotionX = ev.getRawX();
                mLastMotionY = ev.getRawY();
                mLastDownX = ev.getRawX();
                mLastDownY = ev.getRawY();
                LogUtils.d(TAG, "mLastMotionX=" + mLastMotionX);
                LogUtils.d(TAG, "ev.getRawX()=" + ev.getRawX());
                LogUtils.d(TAG, "mLastMotionY=" + mLastMotionY);
                break;
            case MotionEvent.ACTION_MOVE:
                final float x = ev.getRawX();

                final float xDistance = Math.abs(x - mLastDownX);
                final float y = ev.getRawY();
                final float yDistance = Math.abs(y - mLastDownY);
                LogUtils.d(TAG, "ev.getRawX()=" + x);
                LogUtils.d(TAG, "mLastMotionX=" + mLastMotionX);
                LogUtils.d(TAG, "ev.getRawY()=" + y);
                LogUtils.d(TAG, "mLastMotionY=" + mLastMotionY);
                LogUtils.d(TAG, "xDistance=" + xDistance + "yDistance=" + yDistance + "mTouchSlop=" + mTouchSlop);

                //判断触摸方向
                if (mTouchState == TouchState.NONE) {
                    if (xDistance + mTouchSlop < yDistance) {
                        mTouchState = TouchState.VERTICAL_MOVE;
                    }
                    if (xDistance > yDistance + mTouchSlop) {
                        mTouchState = TouchState.HORIZONTAL_MOVE;
                    }
                }
                ImagesPreviewPagerAdapter adapter = (ImagesPreviewPagerAdapter) getAdapter();
                View view = adapter.getCurrentView();
                ViewGroup viewContainer = (ViewGroup) view;
                PhotoView photoView = (PhotoView) viewContainer.getChildAt(0);
                float scale = photoView.getScale();
                //如果是纵向触摸，移动ViewPager
                if (mTouchState == TouchState.VERTICAL_MOVE && scale == 1) {
                    move(false, x - mLastMotionX, (y - mLastMotionY));
                }
                mLastMotionX = ev.getRawX();
                mLastMotionY = ev.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                mLastMotionX = ev.getRawX();
                mLastMotionY = ev.getRawY();
                //纵向触摸结束，判断是否需要飞出，需要ViewPager动画飞出，不需要，飞回原位
                if (mTouchState == TouchState.VERTICAL_MOVE) {
                    if (needToFlingOut()) {
                        int finalY = getTop() < mInitTop ? -(mHeight + mInitTop) : mParent.getHeight();
                        mFlinging = true;
                        startScrollTopView(0, finalY, FLING_OUT_DURATION);
                    } else {
                        startScrollTopView(mInitLeft, mInitTop, SCROLL_BACK_DURATION);
                    }
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                if (mTouchState != TouchState.VERTICAL_MOVE) {
                    mTouchState = TouchState.MORE_TOUCH;
                }
                break;
        }
        //除了纵向触摸，其他都由父类的super.dispatchTouchEvent(ev)处理
        if (mTouchState == TouchState.VERTICAL_MOVE) {
            return true;
        } else {
            LogUtils.d(TAG, "super.dispatchTouchEvent()");
            return super.dispatchTouchEvent(ev);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            LogUtils.e(TAG, e.toString());
            return false;
        }
    }


    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }

    private boolean needToFlingOut() {
        int distance = Math.abs(getTop() - mInitTop);
        return mHeight * FLING_RATIO <= distance;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (mHeight < 0) {
            mHeight = getHeight();
        }
        if (mParent == null) {
            mParent = (ViewGroup) getParent();
        }
    }

    @Override
    public void computeScroll() {
        LogUtils.d(TAG, "mScrolling=" + mScrolling + "mFlinging=" + mFlinging);
        if (mScroller.computeScrollOffset()) {
            final int x = mScroller.getCurrX();
            final int y = mScroller.getCurrY();
            LogUtils.d(TAG, "mScroller.getCurrX()=" + x + "mScroller.getCurrY()=" + y);
            final int dx = x - getLeft();
            final int dy = y - getTop();
            LogUtils.d(TAG, " moveTopView() dx=" + dx + "dy=" + dy);
            move(false, dx, dy);
            if (mFlinging && mPositionListener != null && dy == 0) {
                mPositionListener.onFlingOutFinish();
            }
            ViewCompat.postInvalidateOnAnimation(this);
        } else {
            LogUtils.d(TAG, "computeScrollOffset()=false");
            mScrolling = false;
            super.computeScroll();
        }

    }

    private void move(boolean needHorizontalMove, float deltaX, float deltaY) {
        LogUtils.d(TAG, "move()deltaX=" + deltaX + "deltaY=" + deltaY);
        if (mInitLeft == Integer.MIN_VALUE || mInitTop == Integer.MIN_VALUE || mInitBottom == Integer.MIN_VALUE) {
            mInitLeft = getLeft();
            mInitTop = getTop();
            mInitBottom = getBottom();
            LogUtils.d(TAG, "mInitLeft=" + mInitLeft + "mInitTop=" + mInitTop);
        }
        if (needHorizontalMove) {
            offsetLeftAndRight((int) deltaX);
        }
        offsetTopAndBottom((int) deltaY);
        if (mPositionListener != null) {
            mPositionListener.onPositionChange(mInitTop, getTop(), Math.abs(mInitTop - getTop()) * 1.0f / mHeight);
        }
    }

    public void startScrollTopView(int finalLeft, int finalTop, int duration) {
        LogUtils.d(TAG, "startScrollTopView finalLeft=" + finalLeft + "finalTop" + finalTop);
        final int startLeft = getLeft();
        final int startTop = getTop();
        final int dx = finalLeft - startLeft;
        final int dy = finalTop - startTop;
        if (dx != 0 || dy != 0) {
            mScroller.abortAnimation();
            mScrolling = true;
            mScroller.startScroll(startLeft, startTop, dx, dy, duration);
            ViewCompat.postInvalidateOnAnimation(this);
        } else {
            mScrolling = false;
        }
    }

    public void setPositionListener(OnPositionChangeListener positionListener) {
        mPositionListener = positionListener;
    }

    public void setDisallowInterruptHandler(DisallowInterruptHandler disallowInterruptHandler) {
        mDisallowInterruptHandler = disallowInterruptHandler;
    }


    /**
     * Interpolator defining the animation curve for mScroller
     */
    private static final Interpolator sInterpolator = new Interpolator() {
        @Override
        public float getInterpolation(float t) {
            t -= 1f;
            return t * t * t * t * t + 1f;
        }
    };

    public interface OnPositionChangeListener {

        void onPositionChange(int initTop, int nowTop, float ratio);

        void onFlingOutFinish();
    }

    public interface DisallowInterruptHandler {

        boolean disallowInterrupt();

    }

    public enum TouchState {
        NONE,//普通状态
        DISALLOW_INTERRUPT,//不允许拦截
        HORIZONTAL_MOVE,//横滑动
        VERTICAL_MOVE,//竖滑动
        MORE_TOUCH//多点触摸
    }

}
