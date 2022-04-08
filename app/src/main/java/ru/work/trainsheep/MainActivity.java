package ru.work.trainsheep;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.heinrichreimersoftware.materialdrawer.DrawerActivity;
import com.heinrichreimersoftware.materialdrawer.structure.DrawerItem;
import com.heinrichreimersoftware.materialdrawer.structure.DrawerProfile;
import com.heinrichreimersoftware.materialdrawer.theme.DrawerTheme;
import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import lombok.val;
import org.apmem.tools.layouts.FlowLayout;
import ru.work.trainsheep.data.ServerRepository;
import ru.work.trainsheep.data.ServerRepositoryFactory;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String IS_LOGIN = "is_login";
    ArrayList<EditText> list = new ArrayList<>();
    FlowingDrawer mDrawer;
    //LeftPanel leftPanel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ServerRepository server = ServerRepositoryFactory.getInstance();

        if (!server.isLogin()) {
            val intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            setContentView(R.layout.activity_search);
            //leftPanel = LeftPanel.createFor(this);

            val adapter = new ItemAdapter(new ArrayList<>());
            RecyclerView recyclerView = findViewById(R.id.rv);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
            recyclerView.addItemDecoration(new SpaceItemDecoration(90));
            ((ImageButton)findViewById(R.id.listButton)).setOnClickListener(v ->
                    ((RecyclerView)findViewById(R.id.rv)).smoothScrollToPosition(0));
            //((ImageButton)findViewById(R.id.user_button)).setOnClickListener(v -> leftPanel.open());

            server.getAdverts(new AdvertRequest(new ArrayList<>(), 1, 10), (result) -> {
                if (result.isSuccess()) {
                    adapter.addAll(result.getResult().getNotes());
                } else {
                    result.getException().printStackTrace();
                }
            });
            Util.setEditTextFocusListener(this, R.id.search_field);
            mDrawer = (FlowingDrawer) findViewById(R.id.drawerlayout);
            mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);
            mDrawer.setOnDrawerStateChangeListener(new ElasticDrawer.OnDrawerStateChangeListener() {
                @Override
                public void onDrawerStateChange(int oldState, int newState) {
                    if (newState == ElasticDrawer.STATE_CLOSED) {
                        Log.i("MainActivity", "Drawer STATE_CLOSED");
                    }
                }

                @Override
                public void onDrawerSlide(float openRatio, int offsetPixels) {
                    Log.i("MainActivity", "openRatio=" + openRatio + " ,offsetPixels=" + offsetPixels);
                }
            });
            prepareLeftPanel();
        }
    }

    public void prepareLeftPanel()
    {
        ((LinearLayout)findViewById(R.id.search_line)).setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
        });

        ((LinearLayout)findViewById(R.id.favorite_line)).setOnClickListener(v -> {
            Log.e("Поля \"Избранное\" ещё не существует", "Поля \"Избранное\" ещё не существует");
        });

        ((LinearLayout)findViewById(R.id.message_line)).setOnClickListener(v -> {
            Intent intent = new Intent(this, AllChatsActivity.class);
            this.startActivity(intent);
        });

        ((LinearLayout)findViewById(R.id.profile_line)).setOnClickListener(v -> {
            Intent intent = new Intent(this, ProfileActivity.class);
            this.startActivity(intent);
        });

        ((LinearLayout)findViewById(R.id.settings_line)).setOnClickListener(v -> {
            Log.e("Поля \"Настройки\" ещё не существует", "Поля \"Настройки\" ещё не существует");
        });

        ((LinearLayout)findViewById(R.id.settings_line)).setOnClickListener(v -> {
            Log.e("Поля \"Наши контакты\" ещё не существует", "Поля \"Наши контакты\" ещё не существует");
        });
    }

    static final class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private final ArrayList<Note> notes;


        public ItemAdapter(ArrayList<Note> notes) {
            this.notes = notes;
        }


        private boolean isPositionFooter(int position) {
            return position > notes.size();
        }

        @Override
        public int getItemViewType(int position) {
            if (isPositionFooter(position)) {
                return notes.size() - 1;
            }
            return position;
        }

        public void addAll(List<Note> next) {
            notes.addAll(next);
            notifyItemRangeInserted(notes.size(), next.size());
        }


        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            if (viewType == notes.size() - 1) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_search,
                        parent, false);
                return new FooterViewHolder(view);
            } else
                return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.advert_item, parent, false)) {
                };
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ItemViewHolder) {
                val item = (ItemViewHolder) holder;
                Note note = notes.get(position);

                item.name.setText(note.getHeader());
                item.tagsField.removeAllViews();
                item.description.setText(note.getContent());
                item.companyName.setText(note.getCompany());
                item.salaryText.setText(splitSalary((int)(Math.random()*200000))+" ₽/Секунда");
                item.addTags(note.tags);
            }
        }

        @Override
        public int getItemCount() {
            return this.notes.size();
        }

        public String splitSalary(int salaryNum)
        {
            String salary = String.valueOf(salaryNum);
            StringBuilder builder = new StringBuilder();
            int q = 0;
            for (int i = salary.length()-1; i>-1; i--)
            {
                if (q == 2)
                {
                    if (i != 0)
                        builder.insert(0, " "+salary.charAt(i));
                    q = 0;
                }
                else
                {
                    builder.insert(0, salary.charAt(i));
                    q++;
                }
            }
            return builder.toString();
        }
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        FlowLayout tagsField;
        TextView description;
        TextView companyName;
        TextView salaryText;

        public ItemViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.advert_name);
            tagsField = itemView.findViewById(R.id.tags_field);
            description = itemView.findViewById(R.id.advert_description);
            companyName = itemView.findViewById(R.id.company_name);
            salaryText = itemView.findViewById(R.id.salary_text);
        }

        public void addTags(List<String> tags) {
            for (String tag : tags) {
                View layout = LayoutInflater.from(itemView.getContext()).inflate(R.layout.tag_item, tagsField, true);
                ((TextView) layout.findViewById(R.id.tag)).setText(tag);
            }
        }
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View v) {
            super(v);
            // Добавьте компоненты пользовательского интерфейса здесь.
        }
    }
}