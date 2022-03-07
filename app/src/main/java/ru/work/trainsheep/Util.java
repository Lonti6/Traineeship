package ru.work.trainsheep;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

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
}
