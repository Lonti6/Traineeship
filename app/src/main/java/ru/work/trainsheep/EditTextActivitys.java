package ru.work.trainsheep;

import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class EditTextActivitys extends AppCompatActivity {
    ArrayList<EditText> list = new ArrayList<>();
    public void setTextFieldsAuthorizationListeners()
    {
        list.clear();
        list.add(findViewById(R.id.mail_field));
        list.add(findViewById(R.id.password_field));
        for (EditText field: list)
        {
            field.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (v.hasFocus())
                        ((RelativeLayout)(v.getParent())).setBackgroundResource(R.drawable.border_style_color);
                    else
                        ((RelativeLayout)(v.getParent())).setBackgroundResource(R.drawable.border_style_gray);
                }
            });
        }
    }

    public void setTextFieldsRegistrationListeners()
    {
        list.clear();
        list.add(findViewById(R.id.reg_name_field));
        list.add(findViewById(R.id.reg_mail_field));
        list.add(findViewById(R.id.reg_password_field));
        list.add(findViewById(R.id.reg_conf_password_field));
        for (EditText field: list)
        {
            field.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (v.hasFocus())
                        ((RelativeLayout)(v.getParent())).setBackgroundResource(R.drawable.border_style_color);
                    else
                        ((RelativeLayout)(v.getParent())).setBackgroundResource(R.drawable.border_style_gray);
                }
            });
        }
    }
}
