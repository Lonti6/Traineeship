package ru.work.trainsheep;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import org.jetbrains.annotations.NotNull;

import io.ghyeok.stickyswitch.widget.StickySwitch;


public class SettingsActivity extends AppCompatActivity {
    FlowingDrawer mDrawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mDrawer = (FlowingDrawer) findViewById(R.id.drawerlayout);
        mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_FULLSCREEN);
        Util.prepareLeftPanel(this);

        ((ImageButton)findViewById(R.id.user_button)).setOnClickListener(v -> mDrawer.openMenu(true));

        StickySwitch stickySwitchMessage = (StickySwitch) findViewById(R.id.sticky_switch_message);
        initToggleBut(stickySwitchMessage);

        stickySwitchMessage.setOnSelectedChangeListener((direction, s) -> {
            if (direction.name().equals("RIGHT"))
                stickySwitchMessage.setSliderBackgroundColor(getColor(R.color.light_green));
            if (direction.name().equals("LEFT"))
                stickySwitchMessage.setSliderBackgroundColor(getColor(R.color.line_grey));
        });

        StickySwitch stickySwitchVacansy = (StickySwitch) findViewById(R.id.sticky_switch_vacansy);
        initToggleBut(stickySwitchVacansy);

        stickySwitchVacansy.setOnSelectedChangeListener((direction, s) -> {
            if (direction.name().equals("RIGHT"))
                stickySwitchVacansy.setSliderBackgroundColor(getColor(R.color.light_green));
            if (direction.name().equals("LEFT"))
                stickySwitchVacansy.setSliderBackgroundColor(getColor(R.color.line_grey));
        });
    }

    private void initToggleBut(StickySwitch stickySwitch)
    {
        if (stickySwitch.getDirection().name().equals("RIGHT"))
            stickySwitch.setSliderBackgroundColor(getColor(R.color.light_green));
        if (stickySwitch.getDirection().name().equals("LEFT"))
            stickySwitch.setSliderBackgroundColor(getColor(R.color.line_grey));
    }
}
