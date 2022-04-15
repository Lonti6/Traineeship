package ru.work.trainsheep;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

public class CreateActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left_panel);
        final FlowingDrawer drawer = Util.connectActivityLayout(this, R.layout.activity_create);

        findViewById(R.id.user_button).setOnClickListener(v -> drawer.openMenu(true));

        Util.setEditTextFocusListener(this, R.id.vacancyNameField);
        Util.setEditTextFocusListener(this, R.id.competenciesField);
        Util.setEditTextFocusListener(this, R.id.zpField);
        Util.setEditTextFocusListener(this, R.id.workTimeField);
        Util.setEditTextFocusListener(this, R.id.descriptionField);
    }
}
