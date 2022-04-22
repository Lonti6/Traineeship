package ru.work.trainsheep;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

public class FullSearchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left_panel);
        final FlowingDrawer drawer = Util.connectActivityLayout(this, R.layout.activity_full_search);

        findViewById(R.id.scroller).setOnScrollChangeListener(new ScrollListeners.MyScrollListener(findViewById(R.id.header),
                getDrawable(R.drawable.bg_header)));

        findViewById(R.id.menuBut).setOnClickListener(v -> Util.loadActivity(drawer, this, SearchActivity.class));
    }
}
