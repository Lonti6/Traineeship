package ru.work.trainsheep;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;
import ru.work.trainsheep.data.ServerRepositoryFactory;
import ru.work.trainsheep.send.SetNotification;

public class AdminActivity extends AppCompatActivity {


    Handler handler = new Handler(Looper.getMainLooper());
    TextView info;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left_panel);
        final FlowingDrawer drawer = Util.connectActivityLayout(this, R.layout.activity_admin);

        findViewById(R.id.menuBut).setOnClickListener(v -> drawer.openMenu(true));
        info = findViewById(R.id.text_info);
        findViewById(R.id.on_notifi).setOnClickListener((v) -> {
            ServerRepositoryFactory.getInstance().setNotifi(new SetNotification(true), (r) -> {});
        });
        findViewById(R.id.off_notifi).setOnClickListener((v) -> {
            ServerRepositoryFactory.getInstance().setNotifi(new SetNotification(false), (r) -> {});
        });
        autoUpdateNotifiFromServer();

    }
    private void updateFromServer(){
        ServerRepositoryFactory.getInstance().getNotifi((result -> {
            info.setText("status:" + result.isResult() + "\nКоличесто отправленных:" + result.getCount());
        }));
    }

    private void autoUpdateNotifiFromServer(){
        handler.postDelayed(() -> {
            updateFromServer();
            autoUpdateNotifiFromServer();
        }, 2000);
    }



    @Override
    protected void onStart() {
        super.onStart();
        Util.prepareLeftData(this);

    }
}
