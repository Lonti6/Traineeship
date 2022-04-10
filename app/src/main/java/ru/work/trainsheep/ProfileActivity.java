package ru.work.trainsheep;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import org.apmem.tools.layouts.FlowLayout;

public class ProfileActivity extends AppCompatActivity {
    FlowingDrawer mDrawer;
    FlowLayout flowLayout;
    DataGenerator generator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //FlowLayout flowLayout = findViewById(R.id.user_tags_field);
        //flowLayout.removeAllViews();
        generator = new DataGenerator();


        //((TextView)findViewById(R.id.name_user)).setText(server.getRandom(server.names));

/*        for (int i = 0; i<((int)(Math.random()*7)+100); i++)
        {
            View layout = LayoutInflater.from(this).inflate(R.layout.tag_item, flowLayout, true);
            ((TextView) layout.findViewById(R.id.tag)).setText(server.getRandom(server.tags));
        }*/



        mDrawer = (FlowingDrawer) findViewById(R.id.drawerlayout);
        mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_FULLSCREEN);
        Util.prepareLeftPanel(this);
        ((ImageButton)findViewById(R.id.user_button)).setOnClickListener(v -> mDrawer.openMenu(true));

        BottomSheetBehavior.from(findViewById(R.id.sheet)).setPeekHeight(0);
        //BottomSheetBehavior.from(findViewById(R.id.sheet)).setState(BottomSheetBehavior.STATE_COLLAPSED);
        Util.prepareLeftPanel(this);

        flowLayout = findViewById(R.id.fields_field_bottom_sheet);
        ((Button)findViewById(R.id.tags_but)).setTag("tags");
        ((Button)findViewById(R.id.stages_but)).setTag("stages");

        MyListener myListener = new MyListener();

        ((Button)findViewById(R.id.tags_but)).setOnClickListener(myListener);
        ((Button)findViewById(R.id.stages_but)).setOnClickListener(myListener);

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
        //BottomSheetBehavior.from(findViewById(R.id.sheet)).setHideable(true);
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
                    ((TextView)((ConstraintLayout)(flowLayout.getChildAt(flowLayout.getChildCount()-1))).getChildAt(0)).setText(generator.getRandom(generator.tags));
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
                    ((TextView)((LinearLayout)(flowLayout.getChildAt(flowLayout.getChildCount()-1))).getChildAt(0)).setText(generator.getRandom(generator.companies));
                }
                BottomSheetBehavior.from(findViewById(R.id.sheet)).setPeekHeight(600, true);
            }
        }
    }
}