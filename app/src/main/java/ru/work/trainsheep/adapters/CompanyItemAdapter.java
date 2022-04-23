package ru.work.trainsheep.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import lombok.val;
import ru.work.trainsheep.R;
import ru.work.trainsheep.adapters.holders.CompanyItemHolder;
import ru.work.trainsheep.adapters.holders.FooterViewHolder;
import ru.work.trainsheep.adapters.holders.VacancyItemHolder;
import ru.work.trainsheep.data.ServerRepository;
import ru.work.trainsheep.data.ServerRepositoryFactory;
import ru.work.trainsheep.send.CompanyNote;
import ru.work.trainsheep.send.CompanyRequest;
import ru.work.trainsheep.send.VacancyRequest;

import java.util.ArrayList;
import java.util.List;

public class CompanyItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final ArrayList<CompanyNote> notes;
    private boolean onlyFavorite = false;
    private ServerRepository server = ServerRepositoryFactory.getInstance();
    private final int pageSize = 10;
    private int currentPage = 0;
    private RecyclerView rv;

    public CompanyItemAdapter(RecyclerView rv) {
        this.rv = rv;
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


    public void addAll(List<CompanyNote> next) {
        val start = notes.size();
        notes.addAll(next);
        notifyItemRangeInserted(start, next.size());
        if (start == 0)
            rv.scrollToPosition(0);
    }

    public void clear() {
        currentPage = 0;
        notes.clear();
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == 2) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_search, parent, false);
            return new FooterViewHolder(view);
        } else
            return new CompanyItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.company_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CompanyItemHolder && position < notes.size()) {
            val item = (CompanyItemHolder) holder;
            val note = notes.get(position);

            item.name.setText(note.getHeader());
            item.description.setText(note.getContent());
            item.setOnClickListener(note);
            Glide.with(item.itemView)
                    .load(note.getCompanyImage())
                    .placeholder(R.drawable.ic_zaticha)
                    .error(R.drawable.ic_zaticha)
                    .into(item.imageField);
        }

        if (position >= getItemCount() - 1) {
            serverUpdateSearch();
        }
    }

    public void serverUpdateSearch() {

        server.getCompanies(new CompanyRequest(currentPage, pageSize), (result) -> {
            addAll(result.getNotes());
        });

        currentPage++;
    }

    @Override
    public int getItemCount() {
        return notes.size() + 1;
    }
}
