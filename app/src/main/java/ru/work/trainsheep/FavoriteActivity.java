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
    ServerRepository server;;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left_panel);
        final FlowingDrawer mDrawer = Util.connectActivityLayout(this, R.layout.activity_favorite);

        server = ServerRepositoryFactory.getInstance();

        findViewById(R.id.user_button).setOnClickListener(v -> mDrawer.openMenu(true));

        ButListener listener = new ButListener();
        findViewById(R.id.vacancyBut).setOnClickListener(listener);
        findViewById(R.id.companyBut).setOnClickListener(listener);

        val vacancyAdapter = new Adapters.VacancyItemAdapter(new ArrayList<>());
        recyclerView = findViewById(R.id.rv);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(vacancyAdapter);
        recyclerView.addItemDecoration(new SpaceItemDecoration(90));
        server.getVacancys(new VacancyRequest(new ArrayList<>(), 1, 10), (result) -> {
            vacancyAdapter.addAll(result.getNotes());
        });

    }

    class ButListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.vacancyBut) {
                ((MaterialButton)findViewById(R.id.vacancyBut)).setChecked(true);
                val vacancyAdapter = new Adapters.VacancyItemAdapter(new ArrayList<>());
                recyclerView.setAdapter(vacancyAdapter);
                server.getVacancys(new VacancyRequest(new ArrayList<>(), 1, 10), (result) -> {
                    vacancyAdapter.addAll(result.getNotes());
                });
                Log.e("vacancyBut", "vacancyBut");
                return;
            }
            if (v.getId() == R.id.companyBut) {
                ((MaterialButton)findViewById(R.id.companyBut)).setChecked(true);
                val companyAdapter = new Adapters.CompanyItemAdapter(new ArrayList<>());
                recyclerView.setAdapter(companyAdapter);
                server.getCompanys(new CompanyRequest(1, 10), (result) -> {
                    companyAdapter.addAll(result.getNotes());
                });

                Log.e("companyBut", "companyBut");
            }
        }
    }
}
