package ru.work.trainsheep;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import lombok.val;
import org.apmem.tools.layouts.FlowLayout;
import ru.work.trainsheep.data.FakeServerRepository;

public class ProfileActivity extends AppCompatActivity {
    FlowingDrawer mDrawer;
    FlowLayout flowLayout;
    FakeServerRepository server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        server = new FakeServerRepository();

        mDrawer = (FlowingDrawer) findViewById(R.id.drawerlayout);
        mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_FULLSCREEN);
        Util.prepareLeftPanel(this);
        ((ImageButton)findViewById(R.id.user_button)).setOnClickListener(v -> mDrawer.openMenu(true));

        BottomSheetBehavior.from(findViewById(R.id.sheet)).setPeekHeight(0);
        Util.prepareLeftPanel(this);

        flowLayout = findViewById(R.id.fields_field_bottom_sheet);
        ((Button)findViewById(R.id.tags_but)).setTag("tags");
        ((Button)findViewById(R.id.stages_but)).setTag("stages");

        MyListener myListener = new MyListener();

        ((Button)findViewById(R.id.tags_but)).setOnClickListener(myListener);
        ((Button)findViewById(R.id.stages_but)).setOnClickListener(myListener);

        String q = "";
        for (int i = 0; i <10000; i++)
        {
            q+="a";
        }
        ((TextView)findViewById(R.id.user_description)).setText(q);

        (findViewById(R.id.close_but_profile)).setOnClickListener(v -> {
            if (BottomSheetBehavior.from(findViewById(R.id.sheet)).getState() == Integer.parseInt("3")) {
                BottomSheetBehavior.from(findViewById(R.id.sheet)).setState(BottomSheetBehavior.STATE_COLLAPSED);
                BottomSheetBehavior.from(findViewById(R.id.sheet)).setPeekHeight(0, true);
                BottomSheetBehavior.from(findViewById(R.id.sheet)).setState(BottomSheetBehavior.STATE_COLLAPSED);
                BottomSheetBehavior.from(findViewById(R.id.sheet)).setPeekHeight(0, true);
            }
            else
                {
                    BottomSheetBehavior.from(findViewById(R.id.sheet)).setState(BottomSheetBehavior.STATE_COLLAPSED);
                    BottomSheetBehavior.from(findViewById(R.id.sheet)).setPeekHeight(0, true);
                }
        });

    }

    class MyListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            if (v.getTag().equals("tags"))
            {
                ((TextView)findViewById(R.id.category_text)).setText("Квалификация");
                flowLayout.removeAllViews();
                BottomSheetBehavior.from(findViewById(R.id.sheet)).setPeekHeight(600, true);
                for (int i = 0; i<((int)(Math.random()*20)+2); i++)
                {
                    LayoutInflater.from(ProfileActivity.this).inflate(R.layout.tag_item, flowLayout, true);
                    ((TextView)((ConstraintLayout)(flowLayout.getChildAt(flowLayout.getChildCount()-1))).getChildAt(0)).setText(server.getRandom(server.tags));
                }
                return;
            }
            if (v.getTag().equals("stages"))
            {
                ((TextView)findViewById(R.id.category_text)).setText("Прошлые стажировки");
                flowLayout.removeAllViews();
                for (int i = 0; i<((int)(Math.random()*20)+2); i++)
                {
                    LayoutInflater.from(ProfileActivity.this).inflate(R.layout.stage_title, flowLayout, true);
                    ((TextView)((LinearLayout)(flowLayout.getChildAt(flowLayout.getChildCount()-1))).getChildAt(0)).setText(server.getRandom(server.companies));
                }
                BottomSheetBehavior.from(findViewById(R.id.sheet)).setPeekHeight(600, true);
            }
        }
    }
}