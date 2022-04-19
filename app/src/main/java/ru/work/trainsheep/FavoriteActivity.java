package ru.work.trainsheep;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import java.util.ArrayList;

import lombok.val;
import ru.work.trainsheep.data.ServerRepository;
import ru.work.trainsheep.data.ServerRepositoryFactory;
import ru.work.trainsheep.send.CompanyRequest;
import ru.work.trainsheep.send.VacancyRequest;

public class FavoriteActivity extends AppCompatActivity {

    ServerRepository server;
    Adapters.VacancyItemAdapter vacancyAdapter;
    Adapters.CompanyItemAdapter companyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left_panel);
        final FlowingDrawer drawer = Util.connectActivityLayout(this, R.layout.activity_favorite);

        server = ServerRepositoryFactory.getInstance();

        findViewById(R.id.menuBut).setOnClickListener(v -> drawer.openMenu(true));
/*        ((RecyclerView)findViewById(R.id.rvVacancies)).setOnScrollChangeListener(new MyScrollListener(findViewById(R.id.header),
                getResources().getDrawable(R.drawable.bg_header)));*/

        vacancyAdapter = new Adapters.VacancyItemAdapter(new ArrayList<>());
        companyAdapter = new Adapters.CompanyItemAdapter(new ArrayList<>());



        final RecyclerView rvVacancies = findViewById(R.id.rvVacancies);
        rvVacancies.setHasFixedSize(true);
        rvVacancies.setLayoutManager(new LinearLayoutManager(this));
        rvVacancies.setAdapter(vacancyAdapter);
        rvVacancies.addItemDecoration(new SpaceItemDecoration(105));

        final RecyclerView rvCompanies = findViewById(R.id.rvCompanies);
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
        server.getFavoriteVacancies(new VacancyRequest(new ArrayList<>(), 0, 10), (result) -> {
            vacancyAdapter.clearAndAddAll(result.getNotes());
        });
        server.getCompanies(new CompanyRequest(0, 10), (result) -> {
            companyAdapter.addAll(result.getNotes());
        });
    }
}
