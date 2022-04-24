package ru.work.trainsheep;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import ru.work.trainsheep.data.UserInfo;
import ru.work.trainsheep.send.UserData;

public class EditCompanyDataActivity extends AppCompatActivity {
    FlowingDrawer drawer;
    UserData instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left_panel);
        drawer = Util.connectActivityLayout(this, R.layout.activity_edit_company_data);

        findViewById(R.id.scroller).setOnScrollChangeListener(new ScrollListeners.MyScrollListener(findViewById(R.id.header),
                getDrawable(R.drawable.bg_header)));

        prepare();

    }

    @Override
    protected void onStart() {
        super.onStart();
        prepare();
        Util.prepareLeftData(this);

        findViewById(R.id.menuBut).setOnClickListener(v -> Util.loadActivity(drawer, this, CompanyProfileActivity.class));
    }

    public void prepare() {
        instance = UserInfo.getInstance().getData();
        ((EditText) findViewById(R.id.nameField)).setText(instance.getFirstName());
        ((EditText) findViewById(R.id.mail_field)).setText(instance.getEmail());
        ((EditText) findViewById(R.id.numberField)).setText(instance.getPhoneNumber());

        ((EditText) findViewById(R.id.descriptionField)).setText(instance.getDescription());
    }
}
