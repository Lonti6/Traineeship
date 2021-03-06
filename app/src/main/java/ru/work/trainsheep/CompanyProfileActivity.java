package ru.work.trainsheep;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import ru.work.trainsheep.send.SetFavoriteVacancyRequest;
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

        val container = ((LinearLayout) findViewById(R.id.vacancyContainer));

        container.removeAllViews();

        ServerRepository serverRepository = new RealServerRepository();
        VacancyRequest vacancyRequest = new VacancyRequest();
        vacancyRequest.setEmailFilter(instance.getEmail());
        serverRepository.getVacancies(vacancyRequest, new Consumer<VacancyResult>() {
            @Override
            public void accept(VacancyResult vacancyResult) {

                for (val note : vacancyResult.getNotes()) {
                    val view = LayoutInflater.from(CompanyProfileActivity.this).inflate(R.layout.advert_item, container, false);
                    container.addView(view);
                    ((TextView) view.findViewById(R.id.advert_name)).setText(note.getHeader());
                    ((TextView) view.findViewById(R.id.company_name)).setText(note.getCompany());
                    ((TextView) view.findViewById(R.id.salary_text)).setText(note.getSalary());
                    ((TextView) view.findViewById(R.id.advert_description)).setText(note.getContent());

                    val tagsField = (FlowLayout) view.findViewById(R.id.tags_field);
                    tagsField.removeAllViews();
                    for (String tag : note.getTags()) {
                        val tempTag = LayoutInflater.from(CompanyProfileActivity.this).inflate(R.layout.tag_item, tagsField, false);
                        tagsField.addView(tempTag);
                        ((TextView) tempTag.findViewById(R.id.tag)).setText(tag);
                    }

                    view.setOnClickListener(v -> {
                        Intent intent = new Intent(view.getContext(), FullVacancyActivity.class);
                        intent.putExtra("note", note);
                        view.getContext().startActivity(intent);
                    });

                    if (note.getEmail().equals(UserInfo.getInstance().getData().getEmail()) || ServerRepositoryFactory.IS_ADMIN)
                        view.findViewById(R.id.removed).setVisibility(View.VISIBLE);
                    else
                        view.findViewById(R.id.removed).setVisibility(View.GONE);

                    view.findViewById(R.id.removed).setOnClickListener(v -> {
                        ServerRepositoryFactory.getInstance().removeVacancy(note, (status) -> {
                            container.removeView(view);
                        });
                    });

                    setFavoriteIcon(((ImageView) view.findViewById(R.id.favorite_but)), note.isFavorite(), note.getId());
                    //((TextView) view.findViewById(R.id.tag)).setText(tag);
                }

            }
        });

        Glide.with(this)
                .load(instance.getAvatarSrc())
                .placeholder(R.drawable.company_zaticha)
                .error(R.drawable.company_zaticha)
                .into((ImageView) findViewById(R.id.company_icon));

        ((TextView) findViewById(R.id.descriptionText)).setText(instance.getDescription());
        ((TextView) findViewById(R.id.numberText)).setText("?????????? ????????????????: " + instance.getPhoneNumber());
        ((TextView) findViewById(R.id.mailText)).setText("?????????????????????? ??????????: " + instance.getEmail());
    }

    public void setFavoriteIcon(ImageView favoriteIcon, boolean favorite, long id) {
        val server = ServerRepositoryFactory.getInstance();

        favoriteIcon.setTag(favorite ? "fill" : "hollow");

        if (favoriteIcon.getTag().toString().equals("hollow"))
            favoriteIcon.setImageResource(R.drawable.hollow_star_color_icon);
        else
            favoriteIcon.setImageResource(R.drawable.fill_star_color_icon);

        favoriteIcon.setOnClickListener(v -> {
            if (v.getTag().toString().equals("hollow")) {
                favoriteIcon.setImageResource(R.drawable.fill_star_color_icon);
                v.setTag("fill");
                server.setFavoriteVacancy(new SetFavoriteVacancyRequest(id, true), (note) -> {
                    Log.i(getClass().getSimpleName(), "setFavoriteIcon: server: " + note);
                }, (th) -> {
                    Toast.makeText(CompanyProfileActivity.this, "Error: " + th.getMessage(), Toast.LENGTH_SHORT).show();
                    favoriteIcon.setImageResource(R.drawable.hollow_star_color_icon);
                    v.setTag("hollow");
                });
            } else {
                favoriteIcon.setImageResource(R.drawable.hollow_star_color_icon);
                v.setTag("hollow");
                server.setFavoriteVacancy(new SetFavoriteVacancyRequest(id, false), (note) -> {
                    Log.i(getClass().getSimpleName(), "setFavoriteIcon: server: " + note);

                }, (th) -> {
                    Toast.makeText(CompanyProfileActivity.this, "Error: " + th.getMessage(), Toast.LENGTH_SHORT).show();
                    favoriteIcon.setImageResource(R.drawable.fill_star_color_icon);
                    v.setTag("fill");
                });
            }
        });
    }

    public static class MyDialogFragment extends DialogFragment {
        CompanyProfileActivity parentActivity;

        public MyDialogFragment(CompanyProfileActivity parentActivity) {
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
                instance.setAvatarSrc(((EditText) view.findViewById(R.id.srcField)).getText().toString());

                UserInfo.getInstance().save(parentActivity);

                serverRepository.sendUser(instance, userData -> Toast.makeText(parentActivity.icon.getContext(),
                        "???????????? ????????????????", Toast.LENGTH_SHORT).show());

                Glide.with(parentActivity)
                        .load(instance.getAvatarSrc())
                        .placeholder(R.drawable.company_zaticha)
                        .error(R.drawable.company_zaticha)
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
