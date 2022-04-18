package ru.work.trainsheep;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import lombok.val;
import ru.work.trainsheep.data.ServerRepository;
import ru.work.trainsheep.data.ServerRepositoryFactory;
import ru.work.trainsheep.send.VacancyRequest;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ServerRepository server = ServerRepositoryFactory.getInstance();

        if (!server.isLogin()) {
            val intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            setContentView(R.layout.activity_left_panel);
            final FlowingDrawer drawer = Util.connectActivityLayout(this, R.layout.activity_search);

            findViewById(R.id.menuBut).setOnClickListener(v -> drawer.openMenu(true));

            val adapter = new Adapters.VacancyItemAdapter(new ArrayList<>());
            RecyclerView recyclerView = findViewById(R.id.rv);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
            recyclerView.addItemDecoration(new SpaceItemDecoration(90));
            ((ImageButton)findViewById(R.id.listButton)).setOnClickListener(v ->
                    ((RecyclerView)findViewById(R.id.rv)).smoothScrollToPosition(0));


            server.getVacancies(new VacancyRequest(new ArrayList<>(), 1, 10), (result) -> {
                    adapter.addAll(result.getNotes());
            });
            Util.setEditTextFocusListener(this, R.id.search_field);
        }
    }

}