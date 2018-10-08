package net.rayab.mahimah.Component;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

import net.rayab.mahimah.Activities.actMain;

public class ReternRecyclerView extends  RecyclerView {
    private static final String TAG = ReternRecyclerView.class.getName();

    private static final int STATE_ONSCREEN = 0;
    private static final int STATE_OFFSCREEN = 1;
    private static final int STATE_RETURNING = 2;
    private int mState = STATE_ONSCREEN;
    private int mMinRawY = 0;
    private int linWidthHeight;
    private int mGravity = Gravity.BOTTOM;
    public static View mView;

    public ReternRecyclerView(Context context) {
        super(context);
        init();
    }

    public ReternRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ReternRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
    }

    /**
     * The view that should be showed/hidden when scrolling the content.
     * Make sure to set the gravity on the this view to either Gravity.Bottom or
     * Gravity.TOP and to put it preferable in a FrameLayout.
     */
    public void setReturningView(View view) {
        try {
            mView = view;
            mGravity = Gravity.BOTTOM;
        } catch (ClassCastException e) {
//            throw new RuntimeException("The return view need to be put in a FrameLayout");
        }

        measureView();
        linWidthHeight = mView.getMeasuredHeight();
        addOnScrollListener(new RecyclerScrollListener());
    }

    private void measureView() {
        ViewGroup.LayoutParams p = mView.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        mView.measure(childWidthSpec, childHeightSpec);
    }

    private class RecyclerScrollListener extends OnScrollListener {
        private int mScrolledY;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if(mGravity == Gravity.BOTTOM)
                mScrolledY += dy;
            else if(mGravity == Gravity.TOP)
                mScrolledY -= dy;

            if(mView == null)
                return;

            int translationY = 0;
            int rawY = mScrolledY;

            switch (mState) {
                case STATE_OFFSCREEN:
                    if(mGravity == Gravity.BOTTOM) {
                        if (rawY >= mMinRawY) {
                            mMinRawY = rawY;
                        } else {
                            mState = STATE_RETURNING;
                        }
                    } else if(mGravity == Gravity.TOP) {
                        if (rawY <= mMinRawY) {
                            mMinRawY = rawY;
                        } else {
                            mState = STATE_RETURNING;
                        }
                    }

                    translationY = rawY;
                    break;

                case STATE_ONSCREEN:
                    if(mGravity == Gravity.BOTTOM) {

                        if (rawY > linWidthHeight) {
                            mState = STATE_OFFSCREEN;
                            mMinRawY = rawY;
                        }
                    } else if(mGravity == Gravity.TOP) {

                        if (rawY < -linWidthHeight) {
                            mState = STATE_OFFSCREEN;
                            mMinRawY = rawY;
                        }
                    }
                    translationY = rawY;
                    break;

                case STATE_RETURNING:
                    if(mGravity == Gravity.BOTTOM) {
                        translationY = (rawY - mMinRawY) + linWidthHeight;

                        if (translationY < 0) {
                            translationY = 0;
                            mMinRawY = rawY + linWidthHeight;
                        }

                        if (rawY == 0) {
                            mState = STATE_ONSCREEN;
                            translationY = 0;
                        }

                        if (translationY > linWidthHeight) {
                            mState = STATE_OFFSCREEN;
                            mMinRawY = rawY;
                        }
                    } else if(mGravity == Gravity.TOP) {
                        translationY = (rawY + Math.abs(mMinRawY)) - linWidthHeight;

                        if (translationY > 0) {
                            translationY = 0;
                            mMinRawY = rawY - linWidthHeight;
                        }

                        if (rawY == 0) {
                            mState = STATE_ONSCREEN;
                            translationY = 0;
                        }

                        if (translationY < -linWidthHeight) {
                            mState = STATE_OFFSCREEN;
                            mMinRawY = rawY;
                        }
                    }
                    break;
            }

            if(translationY <= 91) {
                /** this can be used if the build is below honeycomb **/
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB) {
                    TranslateAnimation anim = new TranslateAnimation(0, 0, translationY, translationY);
                    anim.setFillAfter(true);
                    anim.setDuration(0);
                    mView.startAnimation(anim);
                } else {
                    mView.setTranslationY(translationY);
                }
            }
        }
    }

}