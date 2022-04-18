package ru.work.trainsheep;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

public class EditUserDataActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left_panel);
        final FlowingDrawer drawer = Util.connectActivityLayout(this, R.layout.activity_edit_user_data);
    }
}
