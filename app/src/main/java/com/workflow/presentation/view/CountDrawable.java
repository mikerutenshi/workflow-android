package com.workflow.presentation.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.workflow.R;

public class CountDrawable extends Drawable {

    private Paint badgePaint;
    private Paint textPaint;
    private Rect textRect = new Rect();

    private String count = "";
    private boolean isDrawing;

    public CountDrawable(Context context) {
        badgePaint = new Paint();
        badgePaint.setColor(ContextCompat.getColor(context, R.color.orange));
        badgePaint.setAntiAlias(true);
        badgePaint.setStyle(Paint.Style.FILL);

        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTypeface(Typeface.DEFAULT);
        textPaint.setTextSize(context.getResources().getDimension(R.dimen.text_size_badge_count));
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        if (!isDrawing) {
            return;
        }

        Rect bounds = getBounds();
        float width = bounds.right - bounds.left;
        float height = bounds.bottom - bounds.top;

        // Position the badge in the top-right quadrant of the icon.

	        /*Using Math.max rather than Math.min */

        float radius = ((Math.max(width, height) / 2)) / 2;
        float centerX = (width - radius - 1) + 5;
        float centerY = radius - 5;

        //draw badge circle
        if (count.length() <= 2) {
            canvas.drawCircle(centerX, centerY, (int) (radius + 5.5), badgePaint);
        } else {
            canvas.drawCircle(centerX, centerY, (int) (radius + 6.5), badgePaint);
        }
        // Draw badge count text inside the circle.
        textPaint.getTextBounds(count, 0 ,count.length(), textRect);
        float textHeight = textRect.bottom - textRect.top;
        float textY = centerY + (textHeight / 2f);
        if(count.length() > 2)
            canvas.drawText("99+", centerX, textY, textPaint);
        else
            canvas.drawText(count, centerX, textY, textPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        //do nothing
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        //do nothing
    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }

    public void setCount(String count) {
        this.count = count;
        isDrawing = !count.equalsIgnoreCase("0");
        invalidateSelf();
    }
}
