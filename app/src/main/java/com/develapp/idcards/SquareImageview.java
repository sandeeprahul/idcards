package com.develapp.idcards;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * Created by Chinni on 15-10-2015.
 */
public class SquareImageview extends AppCompatImageView {

    public SquareImageview(Context context) {
        super(context);
    }

    public SquareImageview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec,
                             int heightMeasureSpec) {
//   let the default measuring occur, then force the desired aspect ratio
//   on the view (not the drawable).
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
//force a 4:3 aspect ratio
        int height = Math.round((width*2)/3);
        setMeasuredDimension(width, height);
    }
}
