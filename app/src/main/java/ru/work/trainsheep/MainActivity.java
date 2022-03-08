package ru.work.trainsheep;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import lombok.val;
import org.apmem.tools.layouts.FlowLayout;
import ru.work.trainsheep.data.ServerRepository;
import ru.work.trainsheep.data.ServerRepositoryFactory;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String IS_LOGIN = "is_login";
    ArrayList<EditText> list = new ArrayList<>();
    LeftPanel leftPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ServerRepository server = ServerRepositoryFactory.getInstance();

        if (!server.isLogin()) {
            val intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        } else {
            setContentView(R.layout.activity_search);
            leftPanel = LeftPanel.createFor(this);

            val adapter = new ItemAdapter(new ArrayList<>());
            RecyclerView recyclerView = findViewById(R.id.rv);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
            recyclerView.addItemDecoration(new SpaceItemDecoration(70));
            ((ImageButton)findViewById(R.id.listButton)).setOnClickListener(v ->
                    ((RecyclerView)findViewById(R.id.rv)).smoothScrollToPosition(0));
            ((ImageButton)findViewById(R.id.user_button)).setOnClickListener(v -> leftPanel.open());

            server.getAdverts(new AdvertRequest(new ArrayList<>(), 1, 10), (result) -> {
                if (result.isSuccess()) {
                    adapter.addAll(result.getResult().getNotes());
                } else {
                    result.getException().printStackTrace();
                }
            });

            Util.setEditTextFocusListener(this, R.id.search_field);

        }


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
                item.addTags(note.tags);
            }
        }

        @Override
        public int getItemCount() {
            return this.notes.size();
        }


    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        FlowLayout tagsField;
        TextView description;
        TextView companyName;

        public ItemViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.advert_name);
            tagsField = itemView.findViewById(R.id.tags_field);
            description = itemView.findViewById(R.id.advert_description);
            companyName = itemView.findViewById(R.id.company_name);
        }

        public void addTags(List<String> tags) {
            for (String tag : tags) {
                LinearLayout layout = (LinearLayout) LayoutInflater.from(itemView.getContext()).inflate(R.layout.tag_item, null, false);
                ((TextView) layout.findViewById(R.id.tag)).setText(tag);
                tagsField.addView(layout);
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