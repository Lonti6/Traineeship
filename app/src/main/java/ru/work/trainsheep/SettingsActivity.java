package ru.work.trainsheep;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import org.jetbrains.annotations.NotNull;

import io.ghyeok.stickyswitch.widget.StickySwitch;
import io.ghyeok.stickyswitch.widget.StickySwitch.Direction;


public class SettingsActivity extends AppCompatActivity {
    FlowingDrawer mDrawer;
    FragmentManager manager;
    MyDialogFragment myDialogFragment;
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
            if (direction.equals(Direction.RIGHT))
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

        manager = getSupportFragmentManager();
        myDialogFragment = new MyDialogFragment();

        findViewById(R.id.delete_text).setOnClickListener(v ->
                myDialogFragment.show(manager, "Frag"));
    }


    private void initToggleBut(StickySwitch stickySwitch)
    {
        if (stickySwitch.getDirection().name().equals("RIGHT"))
            stickySwitch.setSliderBackgroundColor(getColor(R.color.light_green));
        if (stickySwitch.getDirection().name().equals("LEFT"))
            stickySwitch.setSliderBackgroundColor(getColor(R.color.line_grey));
    }

    public static class MyDialogFragment extends DialogFragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            getDialog().getWindow().setBackgroundDrawableResource(R.drawable.round_corner);
            View view = inflater.inflate(R.layout.dialog_layout, container, true);
            view.findViewById(R.id.delete_field).setOnClickListener(v1 -> Log.e(v1.getClass()+"","Ещё не умеем удалять"));
            view.findViewById(R.id.cancel_field).setOnClickListener(v1 -> getDialog().cancel());

            return view;
        }

        @Override
        public void onStart() {
            super.onStart();
            int width = (int)(getResources().getDisplayMetrics().widthPixels*0.85);
            getDialog().getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}
