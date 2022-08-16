package com.example.firebase_chat_basic.custom;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

/**
 * [LinearLayout CustomClass]
 *
 * <Topic>
 *     This class is "linear custom"
 *     for example, i want to move down last list
 *     we made function that if i touch "EditText" or "ChatRecyclerAdapter ImageViewHolder"
 *     if execute the functions scroll action occur
 *     before scroll action occur, we add animation or manage scroll speed.
 * </Topic>
 */


public class LinearLayoutCustomClass extends LinearLayoutManager {

    private static final float MILLISECONDS_PER_INCH = 45f;

    public LinearLayoutCustomClass(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {

        final LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {

            @Override
            public PointF computeScrollVectorForPosition(int targetPosition) {
                return super.computeScrollVectorForPosition(targetPosition);
            }

            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return MILLISECONDS_PER_INCH / displayMetrics.densityDpi;
            }
        };
        linearSmoothScroller.setTargetPosition(position);
        startSmoothScroll(linearSmoothScroller);
    }
}
