package ru.work.trainsheep;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

public class Util {

    static void setEditTextFocusListener(Activity activity, int... ids){
        for (int id: ids) {
            View view = activity.findViewById(id);
            view.setOnFocusChangeListener((v, hasFocus) -> {
                if (v.hasFocus())
                    ((RelativeLayout)(v.getParent())).setBackgroundResource(R.drawable.border_style_color);
                else
                    ((RelativeLayout)(v.getParent())).setBackgroundResource(R.drawable.border_style_gray);
            });
        }
    }
    public static FlowingDrawer connectActivityLayout(Activity activity, int id){
        RelativeLayout drawer = activity.findViewById(R.id.content_drawer);
        View view = LayoutInflater.from(activity).inflate(id, drawer, false);
        drawer.addView(view);

        final FlowingDrawer mDrawer = activity.findViewById(R.id.drawerlayout);
        mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_FULLSCREEN);
        prepareLeftPanel(activity);
        return mDrawer;
    }
    public static void prepareLeftPanel(Activity activity)
    {
        ((LinearLayout)activity.findViewById(R.id.search_line)).setOnClickListener(v -> {
            Intent intent = new Intent(activity, MainActivity.class);
            activity.startActivity(intent);
        });

        ((LinearLayout)activity.findViewById(R.id.favorite_line)).setOnClickListener(v -> {
            Log.e("Поля \"Избранное\" ещё не существует", "Поля \"Избранное\" ещё не существует");
        });

        ((LinearLayout)activity.findViewById(R.id.message_line)).setOnClickListener(v -> {
            Intent intent = new Intent(activity, AllChatsActivity.class);
            activity.startActivity(intent);
        });

        ((LinearLayout)activity.findViewById(R.id.profile_line)).setOnClickListener(v -> {
            Intent intent = new Intent(activity, ProfileActivity.class);
            activity.startActivity(intent);
        });

        ((LinearLayout)activity.findViewById(R.id.settings_line)).setOnClickListener(v -> {
            Log.e("________________________________________", "_____________________________________________");
            Intent intent = new Intent(activity, SettingsActivity.class);
            activity.startActivity(intent);
        });

        ((LinearLayout)activity.findViewById(R.id.contacts_line)).setOnClickListener(v -> {
            Log.e("Поля \"Наши контакты\" ещё не существует", "Поля \"Наши контакты\" ещё не существует");
        });
    }
}
