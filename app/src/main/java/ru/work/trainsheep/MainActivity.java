package ru.work.trainsheep;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import lombok.val;
import org.apmem.tools.layouts.FlowLayout;
import ru.work.trainsheep.data.ServerRepository;
import ru.work.trainsheep.data.ServerRepositoryFactory;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String IS_LOGIN = "is_login";
    ArrayList<EditText> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ServerRepository server = ServerRepositoryFactory.getInstance();

        if (!server.isLogin()) {
            val intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        } else {
            setContentView(R.layout.activity_search);

            val adapter = new ItemAdapter(new ArrayList<>());
            RecyclerView recyclerView = findViewById(R.id.rv);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);

            server.getAdverts(new AdvertRequest(new ArrayList<>(), 1, 10), (result) -> {
                if (result.isSuccess()) {
                    adapter.addAll(result.getResult().getNotes());
                } else {
                    result.getException().printStackTrace();
                }
            });

            Util.setEditTextFocusListener(this, R.id.search_field);


            new Drawer()
                    .withActivity(this)
                    .withHeader(R.layout.drawer_header)
                    .addDrawerItems(
                            new SectionDrawerItem().withName("Взаимодействие"),
                            new PrimaryDrawerItem().withName("Поиск").withIcon(getDrawable(R.drawable.search_icon)).withIdentifier(1),
                            new PrimaryDrawerItem().withName("Сообщения").withIcon(getDrawable(R.drawable.ic_message)).withIdentifier(2),
                            new SectionDrawerItem().withName("Пользователь"),
                            new SecondaryDrawerItem().withName("Профиль").withIcon(getDrawable(R.drawable.people_icon)).withIdentifier(3),
                            new SecondaryDrawerItem().withName("Настройки").withIcon(getDrawable(R.drawable.settings_icon)).withIdentifier(4),
                            new DividerDrawerItem(),
                            new SecondaryDrawerItem().withName("Наши контакты").withIcon(FontAwesome.Icon.faw_github).withBadge("12+").withIdentifier(5)
                    )
                    .withOnDrawerItemClickListener((parent, view, position, id, drawerItem) -> {
                        if (drawerItem != null) {
                            if (drawerItem.getIdentifier() == 1) {
                                Intent intent = new Intent(this, MainActivity.class);
                                startActivity(intent);
                            } else if (drawerItem.getIdentifier() == 2) {
                                Intent intent = new Intent(this, AllChatsActivity.class);
                                startActivity(intent);
                            } else if (drawerItem.getIdentifier() == 3) {
                                Intent intent = new Intent(this, ProfileActivity.class);
                                startActivity(intent);
                            }
                        }
                    })
                    .build();
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