package com.workflow.presentation.view.itemdecoration;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class VerticalSingleColumnDecorationNoTop extends RecyclerView.ItemDecoration {
    private int itemOffset;

    public VerticalSingleColumnDecorationNoTop(Context context, int itemOffset) {
        this.itemOffset = context.getResources().getDimensionPixelSize(itemOffset);
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
            outRect.top = 0;
        }

        if (position == itemCount - 1) {
            outRect.bottom = itemOffset;
        }
    }
}
