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

public class CreateActivity extends AppCompatActivity {
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

        AutoCompleteTextView mAutoCompleteTextView;
        final String[] mCats = { "Мурзик", "Рыжик", "Барсик", "Борис",
                "Мурзилка", "Мурка", "Муму", "Матроскин" };


        mAutoCompleteTextView = findViewById(R.id.competenciesField);
        mAutoCompleteTextView.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, mCats));

        FlowLayout tagsField = (FlowLayout)findViewById(R.id.tags_field);
        tagsField.setVisibility(View.GONE);

        TextView textView = (TextView)findViewById(R.id.competenciesField);

        findViewById(R.id.addBut).setOnClickListener(v -> {
            if (textView.getText().toString().trim().length()!=0) {
                val view = LayoutInflater.from(v.getContext()).inflate(R.layout.tag_item, tagsField, false);
                tagsField.addView(view);
                ((TextView) view.findViewById(R.id.tag)).setText(textView.getText().toString());
                tagsField.setVisibility(View.VISIBLE);
                textView.setText("");
            }
        });

        findViewById(R.id.scroller).setOnScrollChangeListener(new ScrollListeners.MyScrollListener(
                findViewById(R.id.header),
                getResources().getDrawable(R.drawable.bg_header)));
    }
}
