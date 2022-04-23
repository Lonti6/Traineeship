package ru.work.trainsheep;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import lombok.val;

public class ScrollListeners {
    public static class MyScrollListener implements View.OnScrollChangeListener{
        View header;
        Drawable standartBg, whiteBg;
        public MyScrollListener(View header, Drawable bg)
        {
            this.header = header;
            standartBg =bg;
            whiteBg = new ColorDrawable(Color.rgb(255, 255, 255));
        }
        @Override
        public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
            if (scrollY < header.getHeight() || scrollY ==0)
            {
                header.setY(0);
                header.setBackground(whiteBg);
                header.findViewById(R.id.headerLineBottom).setVisibility(View.GONE);
                return;
            }
            else
            {
                header.setBackground(standartBg);
                header.findViewById(R.id.headerLineBottom).setVisibility(View.VISIBLE);
            }

            if (scrollY == oldScrollY)
                Log.e("No scroll", "No scroll");
            else if (oldScrollY>scrollY&& header.getY() < 0)
            {
                header.setY(header.getY()+(int)(header.getHeight()*0.1));
                if (header.getY() >= 0)
                    header.setY(0);
                Log.e("No scrollaaa", "No scrollaaa");
            }
            else if (oldScrollY<scrollY  && header.getY() > header.getHeight()*-1)
            {
                header.setY(header.getY()-(int)(header.getHeight()*0.1));
                if (header.getY()<header.getHeight()*-1)
                    header.setY(header.getHeight()*-1);
                Log.e("No scrolleeee", "No scrolleee");
            }
        }
    }

    public static class CustomScrollListener extends RecyclerView.OnScrollListener {
        View header;
        Drawable standartBg, whiteBg;
        View otherElement;
        public CustomScrollListener(View header, Drawable bg, View otherElement) {
            this.header = header;
            standartBg =bg;
            whiteBg = new ColorDrawable(Color.rgb(255, 255, 255));
            this.otherElement = otherElement;
        }

        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        }

        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            LinearLayoutManager manager = (LinearLayoutManager)recyclerView.getLayoutManager();
            if (manager.findFirstVisibleItemPosition() == 0
                    && manager.findFirstCompletelyVisibleItemPosition() == 0)
            {
                header.setBackground(whiteBg);
                header.findViewById(R.id.headerLineBottom).setVisibility(View.GONE);
            }
            else
            {
                header.setBackground(standartBg);
                header.findViewById(R.id.headerLineBottom).setVisibility(View.VISIBLE);
            }

            if (dy > 0) {
                header.setY(header.getY()-(int)(header.getHeight()*0.1));
                if (header.getY()<header.getHeight()*-1)
                    header.setY(header.getHeight()*-1);
                otherElement.setY(header.getY()+header.getHeight()+5);
            }
            else if (dy < 0) {
                header.setY(header.getY()+(int)(header.getHeight()*0.1));
                if (header.getY() >= 0)
                    header.setY(0);
                otherElement.setY(header.getY()+header.getHeight()+10);
            }
        }
    }
}
