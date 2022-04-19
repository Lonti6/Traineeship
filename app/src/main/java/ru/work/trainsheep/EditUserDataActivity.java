package ru.work.trainsheep;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import org.apmem.tools.layouts.FlowLayout;

import lombok.val;
import ru.work.trainsheep.data.UserInfo;

public class EditUserDataActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left_panel);
        final FlowingDrawer drawer = Util.connectActivityLayout(this, R.layout.activity_edit_user_data);

        findViewById(R.id.menuBut).setOnClickListener(v -> drawer.openMenu(true));

        findViewById(R.id.scroller).setOnScrollChangeListener(new ScrollListeners.MyScrollListener(findViewById(R.id.header),
                getDrawable(R.drawable.bg_header)));

        prepare();
        final String[] сats = { "Мурзик", "Рыжик", "Барсик", "Борис",
                "Мурзилка", "Мурка", "Муму", "Матроскин" };

        DataGenerator dataGenerator = new DataGenerator();

        AutoCompleteTextView autoCompleteTags = findViewById(R.id.competenciesField);
        autoCompleteTags.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, сats));

        AutoCompleteTextView autoCompleteCities = findViewById(R.id.cityField);
        autoCompleteCities.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, dataGenerator.getCities()));

        FlowLayout tagsField = findViewById(R.id.tags_field);
        tagsField.setVisibility(View.GONE);

        findViewById(R.id.addBut).setOnClickListener(v -> {
            if (autoCompleteTags.getText().toString().trim().length()!=0) {
                val view = LayoutInflater.from(v.getContext()).inflate(R.layout.tag_item, tagsField, false);
                tagsField.addView(view);
                tagsField.getChildAt(tagsField.getChildCount()-1).setOnClickListener(new DoubleClickListener() {
                    @Override
                    public void onDoubleClick(View v) {
                        try {
                            tagsField.removeView(v);
                        }
                        catch (Exception e){}
                    }
                });
                ((TextView) view.findViewById(R.id.tag)).setText(autoCompleteTags.getText().toString());
                tagsField.setVisibility(View.VISIBLE);
                autoCompleteTags.setText("");
            }
        });
    }

    public void prepare()
    {
        val instance = UserInfo.getInstance().getData();
        instance.setLastName("Жильцов");
        instance.setPatronymic("Сергеевич");
        instance.setUniversity("УрГЭУ");
        instance.setCurs(2);
        ((TextView)findViewById(R.id.surnameField)).setText(instance.getLastName());
        ((TextView)findViewById(R.id.nameField)).setText(instance.getFirstName());
        ((TextView)findViewById(R.id.patronymicField)).setText(instance.getPatronymic());
        ((TextView)findViewById(R.id.vyzField)).setText(instance.getUniversity());
        //((TextView)findViewById(R.id.cityField)).setText(instance.get());
        ((TextView)findViewById(R.id.cursField)).setText(String.valueOf(instance.getCurs()));
        ((TextView)findViewById(R.id.mail_field)).setText(instance.getEmail());
        //((TextView)findViewById(R.id.numberField)).setText(instance.get());
        //((TextView)findViewById(R.id.competenciesField)).setText(instance.get());
        ((TextView)findViewById(R.id.descriptionField)).setText(instance.getSpecialization());

    }
}
