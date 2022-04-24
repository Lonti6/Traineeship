package ru.work.trainsheep;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import lombok.val;
import ru.work.trainsheep.adapters.VacancyItemAdapter;
import ru.work.trainsheep.data.ServerRepository;
import ru.work.trainsheep.data.ServerRepositoryFactory;
import ru.work.trainsheep.data.UserInfo;
import ru.work.trainsheep.send.UserData;

public class CompanyProfileActivity extends AppCompatActivity {
    UserData instance;
    VacancyItemAdapter adapter;
    RecyclerView recyclerView;
    public ImageView icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left_panel);
        final FlowingDrawer drawer = Util.connectActivityLayout(this, R.layout.activity_company_profile);

        findViewById(R.id.scroller).setOnScrollChangeListener(new ScrollListeners.MyScrollListener(findViewById(R.id.header),
                getDrawable(R.drawable.bg_header)));

        icon = findViewById(R.id.company_icon);

        recyclerView = findViewById(R.id.rv);
        adapter = new VacancyItemAdapter(recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SpaceItemDecoration(85));

        recyclerView.addOnScrollListener(new ScrollListeners.CustomScrollListener(
                findViewById(R.id.header), getDrawable(R.drawable.bg_header), null));

        findViewById(R.id.menuBut).setOnClickListener(v -> drawer.openMenu(true));

        findViewById(R.id.editBut).setOnClickListener(v -> {
            Util.loadActivity(drawer, CompanyProfileActivity.this, EditCompanyDataActivity.class);
        });

        findViewById(R.id.createBut).setOnClickListener(v -> {
            Util.loadActivity(drawer, CompanyProfileActivity.this, CreateActivity.class);
        });

        findViewById(R.id.changeBut).setOnClickListener(v -> {
            Util.loadActivity(drawer, CompanyProfileActivity.this, EditCompanyDataActivity.class);
        });

        findViewById(R.id.changeIconBut).setOnClickListener(v -> {
            val manager = getSupportFragmentManager();
            val myDialogFragment = new MyDialogFragment(this);
            myDialogFragment.show(manager, "Frag");
        });
    }

    @Override
    protected void onStart() {
        instance = UserInfo.getInstance().getData();
        super.onStart();
        Util.prepareLeftData(this);

        adapter.clear();
        adapter.serverUpdateSearch();

        Glide.with(this)
                .load(instance.getAvatarSrc())
                .placeholder(R.drawable.ic_zaticha)
                .error(R.drawable.ic_zaticha)
                .into((ImageView)findViewById(R.id.company_icon));

        ((TextView)findViewById(R.id.descriptionText)).setText(instance.getDescription());
    }

    public static class MyDialogFragment extends DialogFragment {
        CompanyProfileActivity parentActivity;
        public MyDialogFragment(CompanyProfileActivity parentActivity)
        {
            this.parentActivity = parentActivity;
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            getDialog().getWindow().setBackgroundDrawableResource(R.drawable.round_corner);
            View view = inflater.inflate(R.layout.dialog_change_image, container, true);
            view.findViewById(R.id.cancelBut).setOnClickListener(v1 -> getDialog().cancel());
            view.findViewById(R.id.saveBut).setOnClickListener(v ->
            {
                val instance = UserInfo.getInstance().getData();
                ServerRepository serverRepository = ServerRepositoryFactory.getInstance();
                instance.setAvatarSrc(((EditText)view.findViewById(R.id.srcField)).getText().toString());

                UserInfo.getInstance().save(parentActivity);

                serverRepository.sendUser(instance, userData -> Toast.makeText(parentActivity.icon.getContext(),
                        "Аватар обновлён", Toast.LENGTH_SHORT).show());

                Glide.with(parentActivity)
                        .load(instance.getAvatarSrc())
                        .circleCrop()
                        .placeholder(R.drawable.ic_zaticha)
                        .error(R.drawable.ic_zaticha)
                        .into(parentActivity.icon);

                Util.prepareLeftData(parentActivity);

                getDialog().cancel();
            });
            return view;
        }

        @Override
        public void onStart() {
            super.onStart();
            int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.85);
            getDialog().getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}
