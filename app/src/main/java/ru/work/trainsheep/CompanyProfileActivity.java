package ru.work.trainsheep;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import org.apmem.tools.layouts.FlowLayout;

import java.util.function.Consumer;

import lombok.val;
import ru.work.trainsheep.adapters.VacancyItemAdapter;
import ru.work.trainsheep.data.RealServerRepository;
import ru.work.trainsheep.data.ServerRepository;
import ru.work.trainsheep.data.ServerRepositoryFactory;
import ru.work.trainsheep.data.UserInfo;
import ru.work.trainsheep.send.UserData;
import ru.work.trainsheep.send.VacancyRequest;
import ru.work.trainsheep.send.VacancyResult;

public class CompanyProfileActivity extends AppCompatActivity {
    UserData instance;
    View changeIconView;
    public ImageView icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left_panel);
        final FlowingDrawer drawer = Util.connectActivityLayout(this, R.layout.activity_company_profile);

        findViewById(R.id.scroller).setOnScrollChangeListener(new ScrollListeners.MyScrollListener(findViewById(R.id.header),
                getDrawable(R.drawable.bg_header)));

        icon = findViewById(R.id.company_icon);

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

        changeIconView = findViewById(R.id.changeIconBut);
        changeIconView.setOnClickListener(v -> {
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

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("company")) {
            instance = (UserData) extras.getSerializable("company");
            changeIconView.setVisibility(View.GONE);
        }

        val container = ((LinearLayout)findViewById(R.id.vacancyContainer));

        container.removeAllViews();

        ServerRepository serverRepository = new RealServerRepository();
        VacancyRequest vacancyRequest = new VacancyRequest();
        vacancyRequest.setEmailFilter(instance.getEmail());
        serverRepository.getVacancies(vacancyRequest, new Consumer<VacancyResult>() {
            @Override
            public void accept(VacancyResult vacancyResult) {

                for (val note: vacancyResult.getNotes()) {
                    val view = LayoutInflater.from(CompanyProfileActivity.this).inflate(R.layout.advert_item, container, false);
                    container.addView(view);
                    ((TextView)view.findViewById(R.id.advert_name)).setText(note.getHeader());
                    ((TextView)view.findViewById(R.id.company_name)).setText(note.getCompany());
                    ((TextView)view.findViewById(R.id.salary_text)).setText(note.getSalary());
                    ((TextView)view.findViewById(R.id.advert_description)).setText(note.getContent());

                    val tagsField = (FlowLayout)view.findViewById(R.id.tags_field);
                    tagsField.removeAllViews();
                    for (String tag: note.getTags()) {
                        val tempTag = LayoutInflater.from(CompanyProfileActivity.this).inflate(R.layout.tag_item, tagsField, false);
                        tagsField.addView(tempTag);
                        ((TextView) tempTag.findViewById(R.id.tag)).setText(tag);
                    }
                    //((TextView) view.findViewById(R.id.tag)).setText(tag);
                }

            }
        });

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
