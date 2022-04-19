package ru.work.trainsheep;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;
import lombok.val;
import ru.work.trainsheep.adapters.VacancyItemAdapter;
import ru.work.trainsheep.data.ServerRepository;
import ru.work.trainsheep.data.ServerRepositoryFactory;
import ru.work.trainsheep.send.VacancyRequest;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    VacancyItemAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_left_panel);
        final FlowingDrawer drawer = Util.connectActivityLayout(this, R.layout.activity_search);

        findViewById(R.id.menuBut).setOnClickListener(v -> drawer.openMenu(true));

        adapter = new VacancyItemAdapter();
        RecyclerView recyclerView = findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SpaceItemDecoration(90));
        (findViewById(R.id.listButton)).setOnClickListener(v ->
                ((RecyclerView) findViewById(R.id.rv)).smoothScrollToPosition(0));
        Util.setEditTextFocusListener(this, R.id.search_field);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.clear();
        adapter.serverUpdateSearch();
    }
}