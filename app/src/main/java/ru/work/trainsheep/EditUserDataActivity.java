package ru.work.trainsheep;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import org.apmem.tools.layouts.FlowLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lombok.val;
import ru.work.trainsheep.data.ServerRepository;
import ru.work.trainsheep.data.ServerRepositoryFactory;
import ru.work.trainsheep.data.UserInfo;
import ru.work.trainsheep.send.UserData;

public class EditUserDataActivity extends AppCompatActivity {
    DataGenerator dataGenerator;
    UserData instance;
    FragmentManager manager;
    MyDialogFragment myDialogFragment;
    FlowingDrawer drawer;

    int day;
    int month;
    int year;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left_panel);
        drawer = Util.connectActivityLayout(this, R.layout.activity_edit_user_data);

        findViewById(R.id.scroller).setOnScrollChangeListener(new ScrollListeners.MyScrollListener(findViewById(R.id.header),
                getDrawable(R.drawable.bg_header)));
        dataGenerator = new DataGenerator();

        AutoCompleteTextView autoCompleteTags = findViewById(R.id.competenciesField);
        autoCompleteTags.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, dataGenerator.tags));

        AutoCompleteTextView autoCompleteCities = findViewById(R.id.cityField);
        autoCompleteCities.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, dataGenerator.getCities()));

        FlowLayout tagsField = findViewById(R.id.tags_field);
        if (tagsField.getChildCount() == 0) tagsField.setVisibility(View.GONE);

        findViewById(R.id.addBut).setOnClickListener(v -> {
            if (autoCompleteTags.getText().toString().trim().length() != 0) {
                val view = LayoutInflater.from(v.getContext()).inflate(R.layout.tag_item, tagsField, false);
                tagsField.addView(view);
                tagsField.getChildAt(tagsField.getChildCount() - 1).setOnClickListener(new DoubleClickListener() {
                    @Override
                    public void onDoubleClick(View v) {
                        try {
                            tagsField.removeView(v);
                        } catch (Exception e) {
                        }
                    }
                });
                ((TextView) view.findViewById(R.id.tag)).setText(autoCompleteTags.getText().toString());
                tagsField.setVisibility(View.VISIBLE);
                autoCompleteTags.setText("");
            }
        });


        findViewById(R.id.dateBut).setOnClickListener(v -> {
            DatePickerDialog dpd = new DatePickerDialog(EditUserDataActivity.this,
                    (view, year1, month1, dayOfMonth) -> {
                        ((TextView) findViewById(R.id.dateText)).
                                setText("Дата рождения: " + dayOfMonth + "." + (month1 + 1) + "." + year1);
                        day = dayOfMonth;
                        month = month1;
                        year = year1;
                    }, day, month, year);
            dpd.updateDate(year, month, 6);
            dpd.show();
        });

        findViewById(R.id.saveBut).setOnClickListener(v -> {
            instance = UserInfo.getInstance().getData();
            instance.setLastName(((TextView) findViewById(R.id.surnameField)).getText().toString());
            instance.setFirstName(((TextView) findViewById(R.id.nameField)).getText().toString());
            instance.setPatronymic(((TextView) findViewById(R.id.patronymicField)).getText().toString());
            instance.setUniversity(((TextView) findViewById(R.id.vyzField)).getText().toString());
            instance.setCity(((TextView) findViewById(R.id.cityField)).getText().toString());
            instance.setCurs(Integer.parseInt(((TextView) findViewById(R.id.cursField)).getText().toString()));
            instance.setEmail(((TextView) findViewById(R.id.mail_field)).getText().toString());
            instance.setPhoneNumber(((TextView) findViewById(R.id.numberField)).getText().toString());
            instance.setDescription(((TextView) findViewById(R.id.descriptionField)).getText().toString());
            String passOne = ((TextView) findViewById(R.id.password_field)).getText().toString();
            String passTwo = ((TextView) findViewById(R.id.repeatPasswordField)).getText().toString();
//            if (!(passOne != "") && !(passTwo != "") && passOne.equals(passTwo)) {
//                UserInfo.getInstance().setPassword(passOne);
//            }

            String text = ((TextView) findViewById(R.id.dateText)).getText().toString();

            instance.setBirthdate(new Date(
                    Integer.parseInt(text.substring(text.lastIndexOf('.') + 1)),
                    Integer.parseInt(text.substring(text.indexOf('.') + 1, text.lastIndexOf('.'))) - 1,
                    Integer.parseInt(text.substring(text.lastIndexOf(' ') + 1, text.indexOf('.'))))
            );
            List<String> list = new ArrayList<>();
            for (int i = 0; i < tagsField.getChildCount(); i++) {
                val child = tagsField.getChildAt(i);

                list.add(((TextView) child.findViewById(R.id.tag)).getText().toString());
            }
            instance.setCompetencies(list);

            UserInfo.getInstance().save(this);
            ServerRepository serverRepository = ServerRepositoryFactory.getInstance();
            serverRepository.sendUser(instance, userData -> {
                Toast.makeText(getApplicationContext(), "Сохранение произошло", Toast.LENGTH_SHORT).show();
            });

        });

        manager = getSupportFragmentManager();
        myDialogFragment = new MyDialogFragment();


        findViewById(R.id.delete_text).setOnClickListener(v -> {
            myDialogFragment.show(manager, "Frag");
        });
    }

    public static class MyDialogFragment extends DialogFragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            getDialog().getWindow().setBackgroundDrawableResource(R.drawable.round_corner);
            View view = inflater.inflate(R.layout.dialog_layout, container, true);
            view.findViewById(R.id.delete_field).setOnClickListener(v1 -> Log.e(v1.getClass() + "", "Ещё не умеем удалять"));
            view.findViewById(R.id.cancel_field).setOnClickListener(v1 -> getDialog().cancel());

            return view;
        }

        @Override
        public void onStart() {
            super.onStart();
            int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.85);
            getDialog().getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    public void prepare() {
        instance = UserInfo.getInstance().getData();
        Log.i(getClass().getSimpleName(), "prepare: user data " + instance);
        ((EditText) findViewById(R.id.surnameField)).setText(instance.getLastName());
        ((EditText) findViewById(R.id.nameField)).setText(instance.getFirstName());
        ((EditText) findViewById(R.id.patronymicField)).setText(instance.getPatronymic());
        ((EditText) findViewById(R.id.vyzField)).setText(instance.getUniversity());
        ((EditText) findViewById(R.id.cityField)).setText(instance.getCity());
        ((EditText) findViewById(R.id.cursField)).setText(String.valueOf(instance.getCurs()));
        ((EditText) findViewById(R.id.mail_field)).setText(instance.getEmail());
        ((EditText) findViewById(R.id.numberField)).setText(instance.getPhoneNumber());
        val tagsField = (FlowLayout) findViewById(R.id.tags_field);
        tagsField.removeAllViews();

        for (String tag : instance.getCompetencies()) {
            val view = LayoutInflater.from(this).inflate(R.layout.tag_item, tagsField, false);
            tagsField.addView(view);
            ((TextView) view.findViewById(R.id.tag)).setText(tag);
            view.setOnClickListener(new DoubleClickListener() {
                @Override
                public void onDoubleClick(View v) {
                    try {
                        tagsField.removeView(v);
                    } catch (Exception e) {
                    }
                }
            });
        }
        if (tagsField.getChildCount() > 0) tagsField.setVisibility(View.VISIBLE);
        day = instance.getBirthdate().getDay();
        month = instance.getBirthdate().getMonth();
        year = instance.getBirthdate().getYear();
        ((TextView) findViewById(R.id.dateText)).setText("Дата рождения: " + day + "." + (month + 1) + "." + year);
        ((EditText) findViewById(R.id.descriptionField)).setText(instance.getDescription());
    }

    @Override
    protected void onStart() {
        super.onStart();
        prepare();
        Util.prepareLeftData(this);
        findViewById(R.id.menuBut).setOnClickListener(v -> finish());
    }
}
