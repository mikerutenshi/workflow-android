package com.workflow.presentation.view.itemdecoration;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GridColumnDecoration extends RecyclerView.ItemDecoration {
    private int itemOffset;
    private int spanCount;

    public GridColumnDecoration(Context context, int itemOffset, int spanCount) {
        this.itemOffset = context.getResources().getDimensionPixelSize(itemOffset);
        this.spanCount = spanCount;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view); // item position
        int column = position % spanCount; // item column

        outRect.left = itemOffset - column * itemOffset / spanCount; // spacing - column * ((1f / spanCount) * spacing)
        outRect.right = (column + 1) * itemOffset / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

        if (position < spanCount) { // top edge
            outRect.top = itemOffset;
        }
        outRect.bottom = itemOffset; // item bottom
    }
}
