package ru.work.trainsheep;

import android.graphics.Rect;
import android.util.TypedValue;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    int margin;
    public SpaceItemDecoration(int marginTop)
    {
        this.margin = marginTop;
    }
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int space = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, margin, view.getResources().getDisplayMetrics());
        if(parent.getChildAdapterPosition(view) == 0){
            outRect.top = space;
            outRect.bottom = 0;
        }
    }
}