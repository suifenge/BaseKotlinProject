package com.suifeng.kotlin.base.widget.other;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextPaint;
import android.util.AttributeSet;

/**
 * 滚动文字
 */
public class MarqueTextView extends AppCompatTextView {


    private StringBuilder mSb = new StringBuilder();
    public boolean isInit = true;

    public MarqueTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MarqueTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueTextView(Context context) {
        super(context);
    }

    @Override
    public boolean isFocused() {
        return true;
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        final StringBuilder sb = mSb;
        if (isInit) {
            int length = sb.length();
            if (length > 0) {
                sb.delete(0, length);
            }
            TextPaint paint = getPaint();
            sb.append(getText());
            float textLength = paint.measureText(sb.toString());
            float needWidth = getWidth() - textLength;
            if (needWidth >= 0) {
                float spaceWidth = paint.measureText(" ");
                int number = (int) (needWidth / spaceWidth + 0.5f) + 2;
                for (int i = 0; i < number; i++) {
                    sb.append(" ");
                }
                setText(sb.toString());
            }
            isInit = false;
        }
        super.onDraw(canvas);
    }
}