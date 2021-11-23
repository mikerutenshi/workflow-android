package com.workflow.presentation.view.itemdecoration;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class VerticalSingleColumDecorationLargeBot extends RecyclerView.ItemDecoration {
    private int itemOffset;
    private int botOffset;

    public VerticalSingleColumDecorationLargeBot(Context context, int itemOffset, int botOffset) {
        this.itemOffset = context.getResources().getDimensionPixelSize(itemOffset);
        this.botOffset = context.getResources().getDimensionPixelOffset(botOffset);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position
        int itemCount = state.getItemCount();

        outRect.top = itemOffset / 2;
        outRect.left = itemOffset;
        outRect.right = itemOffset;
        outRect.bottom = itemOffset / 2;

        if (position == 0) {
            outRect.top = itemOffset;
        }

        if (position == itemCount - 1) {
            outRect.bottom = botOffset;
        }
    }
}
