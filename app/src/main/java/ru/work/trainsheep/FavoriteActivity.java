package ru.work.trainsheep;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mxn.soul.flowingdrawer_core.FlowingDrawer;
import lombok.val;
import ru.work.trainsheep.adapters.CompanyItemAdapter;
import ru.work.trainsheep.adapters.VacancyItemAdapter;
import ru.work.trainsheep.data.ServerRepository;
import ru.work.trainsheep.data.ServerRepositoryFactory;
import ru.work.trainsheep.data.UserInfo;
import ru.work.trainsheep.send.UserData;

public class FavoriteActivity extends AppCompatActivity {

    ServerRepository server;
    VacancyItemAdapter vacancyAdapter;
    CompanyItemAdapter companyAdapter;
    UserData instance = UserInfo.getInstance().getData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left_panel);
        final FlowingDrawer drawer = Util.connectActivityLayout(this, R.layout.activity_favorite);

        server = ServerRepositoryFactory.getInstance();

        findViewById(R.id.menuBut).setOnClickListener(v -> drawer.openMenu(true));
/*        ((RecyclerView)findViewById(R.id.rvVacancies)).setOnScrollChangeListener(new MyScrollListener(findViewById(R.id.header),
                getResources().getDrawable(R.drawable.bg_header)));*/
        final RecyclerView rvVacancies = findViewById(R.id.rvVacancies);
        final RecyclerView rvCompanies = findViewById(R.id.rvCompanies);
        vacancyAdapter = new VacancyItemAdapter(rvVacancies, true);
        companyAdapter = new CompanyItemAdapter(rvCompanies);



        rvVacancies.setHasFixedSize(true);
        rvVacancies.setLayoutManager(new LinearLayoutManager(this));
        rvVacancies.setAdapter(vacancyAdapter);
        rvVacancies.addItemDecoration(new SpaceItemDecoration(105));


        rvCompanies.setHasFixedSize(true);
        rvCompanies.setLayoutManager(new LinearLayoutManager(this));
        rvCompanies.setAdapter(companyAdapter);
        rvCompanies.addItemDecoration(new SpaceItemDecoration(105));

        rvCompanies.setVisibility(View.GONE);

        findViewById(R.id.vacancyBut).setOnClickListener((v) -> {
            rvCompanies.setVisibility(View.GONE);
            rvVacancies.setVisibility(View.VISIBLE);
        });
        findViewById(R.id.companyBut).setOnClickListener((v) -> {
            rvCompanies.setVisibility(View.VISIBLE);
            rvVacancies.setVisibility(View.GONE);
        });

        val listener = new ScrollListeners.CustomScrollListener(
                findViewById(R.id.header),
                getResources().getDrawable(R.drawable.bg_header),
                findViewById(R.id.toggleGroup));
        rvVacancies.addOnScrollListener(listener);
        rvCompanies.addOnScrollListener(listener);
    }

    @Override
    protected void onStart() {
        super.onStart();

        vacancyAdapter.clear();
        vacancyAdapter.serverUpdateSearch();

        companyAdapter.clear();
        companyAdapter.serverUpdateSearch();

        Util.prepareLeftData(this);
    }
}
