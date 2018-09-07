package com.fcgtask.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class GridImage extends AppCompatImageView {
    public GridImage(Context context) {
        super(context);
    }

    public GridImage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = this.getMeasuredWidth();
        setMeasuredDimension(w, w);
    }
}
