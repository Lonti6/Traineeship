package ru.work.trainsheep;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left_panel);
        final FlowingDrawer mDrawer = Util.connectActivityLayout(this, R.layout.activity_favorite);

        val server = ServerRepositoryFactory.getInstance();

        findViewById(R.id.user_button).setOnClickListener(v -> mDrawer.openMenu(true));

        val vacancyAdapter = new Adapters.VacancyItemAdapter(new ArrayList<>());
        val companyAdapter = new Adapters.CompanyItemAdapter(new ArrayList<>());

        server.getVacancies(new VacancyRequest(new ArrayList<>(), 1, 10), (result) -> {
            vacancyAdapter.addAll(result.getNotes());
        });
        server.getCompanies(new CompanyRequest(1, 10), (result) -> {
            companyAdapter.addAll(result.getNotes());
        });

        final RecyclerView rvVacancies = findViewById(R.id.rvVacancies);
        rvVacancies.setHasFixedSize(true);
        rvVacancies.setLayoutManager(new LinearLayoutManager(this));
        rvVacancies.setAdapter(vacancyAdapter);
        rvVacancies.addItemDecoration(new SpaceItemDecoration(90));

        final RecyclerView rvCompanies = findViewById(R.id.rvCompanies);
        rvCompanies.setHasFixedSize(true);
        rvCompanies.setLayoutManager(new LinearLayoutManager(this));
        rvCompanies.setAdapter(companyAdapter);
        rvCompanies.addItemDecoration(new SpaceItemDecoration(90));

        rvCompanies.setVisibility(View.GONE);

        findViewById(R.id.vacancyBut).setOnClickListener((v) -> {
            rvCompanies.setVisibility(View.GONE);
            rvVacancies.setVisibility(View.VISIBLE);
        });
        findViewById(R.id.companyBut).setOnClickListener((v) -> {
            rvCompanies.setVisibility(View.VISIBLE);
            rvVacancies.setVisibility(View.GONE);
        });

    }

//    class ButListener implements View.OnClickListener {
//        @Override
//        public void onClick(View v) {
//            if (v.getId() == R.id.vacancyBut) {
//                ((MaterialButton)findViewById(R.id.vacancyBut)).setChecked(true);
//                val vacancyAdapter = new Adapters.VacancyItemAdapter(new ArrayList<>());
//                rvCompanies.setAdapter(vacancyAdapter);
//                server.getVacancies(new VacancyRequest(new ArrayList<>(), 1, 10), (result) -> {
//                    vacancyAdapter.addAll(result.getNotes());
//                });
//                Log.e("vacancyBut", "vacancyBut");
//                return;
//            }
//            if (v.getId() == R.id.companyBut) {
//                ((MaterialButton)findViewById(R.id.companyBut)).setChecked(true);
//                val companyAdapter = new Adapters.CompanyItemAdapter(new ArrayList<>());
//                rvCompanies.setAdapter(companyAdapter);
//                server.getCompanies(new CompanyRequest(1, 10), (result) -> {
//                    Log.e("ButListener", "" + result.getNotes());
//                    companyAdapter.addAll(result.getNotes());
//                });
//
//                Log.e("companyBut", "companyBut");
//            }
//        }
//    }
}
