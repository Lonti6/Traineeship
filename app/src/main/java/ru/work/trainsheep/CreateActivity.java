package ru.work.trainsheep;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import org.apmem.tools.layouts.FlowLayout;

import lombok.val;
import ru.work.trainsheep.data.ServerRepositoryFactory;
import ru.work.trainsheep.data.UserInfo;
import ru.work.trainsheep.send.VacancyNote;

import java.util.ArrayList;

public class CreateActivity extends AppCompatActivity {

    FlowLayout tagsField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left_panel);
        final FlowingDrawer drawer = Util.connectActivityLayout(this, R.layout.activity_create);

        findViewById(R.id.menuBut).setOnClickListener(v -> drawer.openMenu(true));

        Util.setEditTextFocusListener(this, R.id.vacancyNameField);
        Util.setEditTextFocusListener(this, R.id.competenciesField);
        Util.setEditTextFocusListener(this, R.id.zpField);
        Util.setEditTextFocusListener(this, R.id.workTimeField);
        Util.setEditTextCreateFocusListener(this, R.id.descriptionField);

        DataGenerator dataGenerator = new DataGenerator();
        final String[] tags = dataGenerator.tags.toArray(new String[0]);
        final String[] zpTypes = dataGenerator.zpTypes.toArray(new String[0]);


        AutoCompleteTextView autoCompleteTags = findViewById(R.id.competenciesField);
        autoCompleteTags.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, tags));

        AutoCompleteTextView autoCompleteCities = findViewById(R.id.cityField);
        autoCompleteCities.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, dataGenerator.getCities()));

        AutoCompleteTextView autoCompleteZpTypes = findViewById(R.id.zpTypeField);
        autoCompleteZpTypes.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, zpTypes));

        tagsField = findViewById(R.id.tags_field);
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
                        catch (Exception e){ e.printStackTrace(); }
                    }
                });
                ((TextView) view.findViewById(R.id.tag)).setText(autoCompleteTags.getText().toString());
                tagsField.setVisibility(View.VISIBLE);
                autoCompleteTags.setText("");
            }
        });

        findViewById(R.id.scroller).setOnScrollChangeListener(new ScrollListeners.MyScrollListener(
                findViewById(R.id.header),
                getDrawable(R.drawable.bg_header)));
        findViewById(R.id.create).setOnClickListener((v) -> {
            readDataAndSend();
        });

    }


    void readDataAndSend(){

        String header = ((EditText)findViewById(R.id.vacancyNameField)).getText().toString();
        String content = ((EditText)findViewById(R.id.descriptionField)).getText().toString();
        String company = UserInfo.getInstance().getData().getFirstName();
        String salary = ((EditText)findViewById(R.id.zpField)).getText().toString();
        String zpType = ((AutoCompleteTextView)findViewById(R.id.zpTypeField)).getText().toString();
        boolean free = ((CheckBox)findViewById(R.id.freeCheck)).isChecked();

        if (header.equals("") || content.equals("")) {
            Toast.makeText(getApplicationContext(), "Поля не заполнены!", Toast.LENGTH_SHORT).show();
            return;
        }

        val list = new ArrayList<String>();
        for (int i = 0; i < tagsField.getChildCount(); i++) {
            TextView view = tagsField.getChildAt(i).findViewById(R.id.tag);
            list.add("" + view.getText());
        }
        if (free){
            list.add("Бесплатно");
        }
        val note = new VacancyNote(list, header, content, company, (salary + " " + zpType), false, 0);
        ServerRepositoryFactory.getInstance().createVacancy(note, (n) -> {
            Toast.makeText(getApplicationContext(), "Создана вакансия " + header, Toast.LENGTH_SHORT).show();
            ((EditText)findViewById(R.id.vacancyNameField)).setText("");
            ((EditText)findViewById(R.id.descriptionField)).setText("");
            ((EditText)findViewById(R.id.zpField)).setText("");
            ((CheckBox)findViewById(R.id.freeCheck)).setChecked(false);
            tagsField.removeAllViews();
        });
    }
}
