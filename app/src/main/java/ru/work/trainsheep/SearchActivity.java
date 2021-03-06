package ru.work.trainsheep;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mxn.soul.flowingdrawer_core.FlowingDrawer;
import lombok.val;
import ru.work.trainsheep.adapters.VacancyItemAdapter;
import ru.work.trainsheep.data.ServerRepositoryFactory;
import ru.work.trainsheep.data.UserInfo;
import ru.work.trainsheep.send.UserData;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    VacancyItemAdapter adapter;
    EditText findText;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        val info = UserInfo.getInstance();
        info.load(this);

        if (!info.isLogin()){
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            finish();
            return;
        }
        Intent service = new Intent(this, ServiceForNotifi.class);
        startService(service);

        val server = ServerRepositoryFactory.getInstance();
        server.login(info.getRegistrationData(), name -> {
            Toast.makeText(getApplicationContext(), "Здравствуйте, " + name, Toast.LENGTH_SHORT).show();
        }, err -> {
            Toast.makeText(getApplicationContext(), "Ошибка авторизации", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            finish();
        });

        setContentView(R.layout.activity_left_panel);
        final FlowingDrawer drawer = Util.connectActivityLayout(this, R.layout.activity_search);

        findViewById(R.id.tree_palka).setOnClickListener(v -> drawer.openMenu(true));

        val updateButton = findViewById(R.id.updateBut);
        updateButton.setOnClickListener(v -> {
            adapter.clear();
            adapter.serverUpdateSearch();
        });

        recyclerView = findViewById(R.id.rv);
        adapter = new VacancyItemAdapter(recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SpaceItemDecoration(85));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager manager = (LinearLayoutManager)recyclerView.getLayoutManager();
                if (manager.findFirstVisibleItemPosition() < 1
                        && manager.findFirstCompletelyVisibleItemPosition() < 1)
                {
                    updateButton.setVisibility(View.VISIBLE);
                }
                else {updateButton.setVisibility(View.GONE);}
            }
        });
        (findViewById(R.id.listButton)).setOnClickListener(v ->
                recyclerView.smoothScrollToPosition(0));
        Util.setEditTextFocusListener(this, R.id.search_field);
        findText = findViewById(R.id.search_field);
        Log.i(getClass().getSimpleName(), "onCreate: " + findText.getText().toString() + " " + findText.getId());

        findText.setOnEditorActionListener((v, actionId, event) -> {
            Log.i("TAG", "onEditorAction: " + event);
            if (actionId == EditorInfo.IME_ACTION_SEARCH){
                sendSearch();
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                findText.clearFocus();
                return true;
            }
            return false;
        });

        findViewById(R.id.filterBut).setOnClickListener(v ->
                Util.loadActivity(drawer, SearchActivity.this, FullSearchActivity.class));
    }

    public void sendSearch(){
        val text = findText.getText().toString();
        Log.i("TAG", "------------------ sendSearch: " + text);
        adapter.clearAndSearch(text, new ArrayList<>());
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.clear();
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("header")) {
            val header = extras.getString("header");
            val tags = (List<String>)extras.getSerializable("tags");
            adapter.serverUpdateSearch(header, tags);
        }
        else
            adapter.serverUpdateSearch();
        Util.prepareLeftData(this);
    }
}