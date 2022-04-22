package ru.work.trainsheep;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import io.ghyeok.stickyswitch.widget.StickySwitch;
import io.ghyeok.stickyswitch.widget.StickySwitch.Direction;
import ru.work.trainsheep.data.UserInfo;
import ru.work.trainsheep.send.UserData;


public class SettingsActivity extends AppCompatActivity {
    FlowingDrawer mDrawer;

    UserData instance = UserInfo.getInstance().getData();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left_panel);
        final FlowingDrawer drawer = Util.connectActivityLayout(this, R.layout.activity_settings);

        mDrawer = findViewById(R.id.drawerlayout);
        mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_FULLSCREEN);
        Util.prepareLeftPanel(this);

        (findViewById(R.id.menuBut)).setOnClickListener(v -> drawer.openMenu(true));

        StickySwitch stickySwitchMessage = findViewById(R.id.sticky_switch_message);
        initToggleBut(stickySwitchMessage);

        stickySwitchMessage.setOnSelectedChangeListener((direction, s) -> {
            if (direction.equals(Direction.RIGHT))
                stickySwitchMessage.setSliderBackgroundColor(getColor(R.color.light_green));
            if (direction.equals(Direction.LEFT))
                stickySwitchMessage.setSliderBackgroundColor(getColor(R.color.light_gray));
        });

        StickySwitch stickySwitchVacansy = findViewById(R.id.sticky_switch_vacansy);
        initToggleBut(stickySwitchVacansy);

        stickySwitchVacansy.setOnSelectedChangeListener((direction, s) -> {
            if (direction.equals(Direction.RIGHT))
                stickySwitchVacansy.setSliderBackgroundColor(getColor(R.color.light_green));
            if (direction.equals(Direction.LEFT))
                stickySwitchVacansy.setSliderBackgroundColor(getColor(R.color.light_gray));
        });

        findViewById(R.id.exit_text).setOnClickListener(v -> Util.loadActivity(drawer, SettingsActivity.this, LoginActivity.class));
    }


    private void initToggleBut(StickySwitch stickySwitch)
    {
        if (stickySwitch.getDirection().equals(Direction.RIGHT))
            stickySwitch.setSliderBackgroundColor(getColor(R.color.light_green));
        if (stickySwitch.getDirection().equals(Direction.LEFT))
            stickySwitch.setSliderBackgroundColor(getColor(R.color.light_gray));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Glide.with(this)
                .load(instance.getAvatarSrc())
                .circleCrop()
                .into((ImageView) this.findViewById(R.id.left_icon_user));
    }
}