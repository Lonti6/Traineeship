package ru.work.trainsheep.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import lombok.val;
import ru.work.trainsheep.R;
import ru.work.trainsheep.adapters.holders.FooterViewHolder;
import ru.work.trainsheep.adapters.holders.VacancyItemHolder;
import ru.work.trainsheep.data.ServerRepository;
import ru.work.trainsheep.data.ServerRepositoryFactory;
import ru.work.trainsheep.send.VacancyNote;
import ru.work.trainsheep.send.VacancyRequest;

import java.util.ArrayList;
import java.util.List;

public class VacancyItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final ArrayList<VacancyNote> notes;
    private boolean onlyFavorite = false;
    private ServerRepository server = ServerRepositoryFactory.getInstance();
    private final int pageSize = 10;
    private  int currentPage = 0;
    private String searchText = "";
    private List<String> tags = new ArrayList<>();
    private RecyclerView rv;

    public VacancyItemAdapter(RecyclerView rv) {
        this.rv = rv;
        this.notes = new ArrayList<>();
    }

    public VacancyItemAdapter(RecyclerView rv, boolean onlyFavorite) {
        this.rv = rv;
        this.onlyFavorite = onlyFavorite;
        this.notes = new ArrayList<>();
    }

    private boolean isPositionFooter(int position) {
        return position >= notes.size();
    }


    @Override
    public int getItemViewType(int position) {
        if (isPositionFooter(position)) {
            return 2;
        }
        return 1;
    }


    public void addAll(List<VacancyNote> next) {
        val start = notes.size();
        notes.addAll(next);
        notifyItemRangeInserted(start, next.size());
        if (start == 0)
            rv.smoothScrollToPosition(0);
    }

    public void clear() {
        currentPage = 0;
        notes.clear();
        notifyDataSetChanged();
    }

    public void clearAndSearch(String text, List<String> tags){
        clear();
        serverUpdateSearch(text, tags);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == 2) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_search, parent, false);
            return new FooterViewHolder(view);
        } else
            return new VacancyItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.advert_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof VacancyItemHolder && position < notes.size()) {
            val item = (VacancyItemHolder) holder;
            VacancyNote note = notes.get(position);

            item.name.setText(note.getHeader());
            item.tagsField.removeAllViews();
            item.description.setText(note.getContent());
            item.companyName.setText(note.getCompany());
            item.salaryText.setText(note.getSalary());
            item.addTags(note.getTags());
            item.setSrc();
            item.setFavoriteIcon(note.isFavorite(), note.getId());
        }

        if (position >= getItemCount() - 1){
            serverUpdateSearch(searchText, tags);
        }
    }
    public void serverUpdateSearch(){
        serverUpdateSearch(searchText, tags);
    }
    public void serverUpdateSearch(String text, List<String> tags){
        searchText = text;
        this.tags = tags;
        if (onlyFavorite){
            server.getFavoriteVacancies(new VacancyRequest(tags, searchText, currentPage, pageSize), (result) -> {
                addAll(result.getNotes());
            });
        } else {
            server.getVacancies(new VacancyRequest(tags, searchText, currentPage, pageSize), (result) -> {
                addAll(result.getNotes());
            });
        }

        currentPage++;
    }

    @Override
    public int getItemCount() {
        return notes.size() + 1;
    }
}
