package ru.work.trainsheep.ui.search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.apmem.tools.layouts.FlowLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.work.trainsheep.AdvertRequest;
import ru.work.trainsheep.AdvertResult;
import ru.work.trainsheep.Note;
import ru.work.trainsheep.R;
import ru.work.trainsheep.data.FakeServerRepository;
import ru.work.trainsheep.data.ServerRepository;
import ru.work.trainsheep.data.ServerRepositoryFactory;
import ru.work.trainsheep.databinding.FragmentSearchBinding;

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;
    private FragmentSearchBinding binding;
    private RecyclerView.Adapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel =
                new ViewModelProvider(this).get(SearchViewModel.class);

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        root.findViewById(R.id.search_field).setOnFocusChangeListener((v, hasFocus) -> {
            if (v.hasFocus())
                ((RelativeLayout)(v.getParent())).setBackgroundResource(R.drawable.border_style_color);
            else
                ((RelativeLayout)(v.getParent())).setBackgroundResource(R.drawable.border_style_gray);
        });

        root.findViewById(R.id.listButton).setOnClickListener(v ->
                ((RecyclerView)root.findViewById(R.id.rv)).smoothScrollToPosition(0));
        ArrayList<Note> notes = new ArrayList<>();
        ServerRepository server = ServerRepositoryFactory.getInstance();
        server.getAdverts(new AdvertRequest(new ArrayList<String>(), 3, 11), (result) ->{
            if (result.isSuccess()){
                notes.addAll(result.getResult().getNotes());
                adapter.notifyDataSetChanged();
            } else {
                result.getException().printStackTrace();
            }
        });
        adapter = new ItemAdapter(notes);

        RecyclerView recyclerView = root.findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
final class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private final ArrayList<Note> notes;


    public ItemAdapter(ArrayList<Note> notes) {
        this.notes = notes;
    }


    private boolean isPositionFooter(int position) {
        return position > notes.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        if (isPositionFooter(position)) {
            return notes.size()-1;
        }
        return position;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == notes.size()-1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_search,
                    parent, false);
            return new FooterViewHolder(view);
        }
        else
            return new RecyclerView.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.advert_item, parent, false)){};
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FooterViewHolder){}
        else {
            Note note = notes.get(position);
            TextView name = holder.itemView.findViewById(R.id.advert_name);
            name.setText(note.getHeader());

            FlowLayout tagsField = holder.itemView.findViewById(R.id.tags_field);
            tagsField.removeAllViews();

            for (String tag : note.getTags()) {
                LinearLayout layout = (LinearLayout) LayoutInflater.from(holder.itemView.getContext()).inflate(R.layout.tag_item, null, false);
                ((TextView) ((RelativeLayout) layout.getChildAt(0)).getChildAt(0)).setText(tag);
                tagsField.addView(layout);
            }

            TextView description = holder.itemView.findViewById(R.id.advert_description);
            description.setText(note.getContent());

            TextView companyName = holder.itemView.findViewById(R.id.company_name);
            companyName.setText(note.getCompany());
        }
    }

    @Override
    public int getItemCount() {
        return this.notes.size();
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        public View View;
        public FooterViewHolder(View v) {
            super(v);
            View = v;
            // Добавьте компоненты пользовательского интерфейса здесь.
        }
    }
}