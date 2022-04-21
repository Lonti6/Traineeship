package ru.work.trainsheep;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
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
import ru.work.trainsheep.send.VacancyRequest;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    VacancyItemAdapter adapter;
    EditText findText;
    RecyclerView recyclerView;
    UserData instance = UserInfo.getInstance().getData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        val info = UserInfo.getInstance();
        info.load(this);

        if (!info.isLogin()){
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            return;
        }

        setContentView(R.layout.activity_left_panel);
        final FlowingDrawer drawer = Util.connectActivityLayout(this, R.layout.activity_search);

        findViewById(R.id.tree_palka).setOnClickListener(v -> drawer.openMenu(true));

        recyclerView = findViewById(R.id.rv);
        adapter = new VacancyItemAdapter(recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SpaceItemDecoration(85));
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

    }

    public void sendSearch(){
        val text = findText.getText().toString();
        Log.i("TAG", "------------------ sendSearch: " + text);
        adapter.clearAndSearch(text, new ArrayList<>());
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
        adapter.clear();
        adapter.serverUpdateSearch();
        Glide.with(this)
                .load(instance.getAvatarSrc())
                .circleCrop()
                .into((ImageView) this.findViewById(R.id.left_icon_user));
        }
        catch (Exception e){e.printStackTrace();};
    }
}