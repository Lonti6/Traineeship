package ru.work.trainsheep;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import ru.work.trainsheep.data.UserInfo;
import ru.work.trainsheep.send.UserData;

public class FullVacancyActivity extends AppCompatActivity {
    UserData instance = UserInfo.getInstance().getData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left_panel);
        final FlowingDrawer mDrawer = Util.connectActivityLayout(this, R.layout.activity_full_vacancy);
        findViewById(R.id.scroller).setOnScrollChangeListener(new ScrollListeners.MyScrollListener(findViewById(R.id.header),
                getDrawable(R.drawable.bg_header)));

        findViewById(R.id.editBut).setOnClickListener(v -> LoadActivity(mDrawer));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Glide.with(this)
                .load(instance.getAvatarSrc())
                .circleCrop()
                .into((ImageView) this.findViewById(R.id.left_icon_user));
    }

    private void LoadActivity(FlowingDrawer drawer) {
        Intent intent = new Intent(this, EditUserDataActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        this.startActivity(intent);
        drawer.closeMenu(false);
    }
}
