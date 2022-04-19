package ru.work.trainsheep;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import lombok.val;
import org.apmem.tools.layouts.FlowLayout;
import ru.work.trainsheep.adapters.holders.FooterViewHolder;
import ru.work.trainsheep.data.ServerRepositoryFactory;
import ru.work.trainsheep.send.CompanyNote;
import ru.work.trainsheep.send.SetFavoriteVacancyRequest;
import ru.work.trainsheep.send.VacancyNote;

import java.util.ArrayList;
import java.util.List;

public class Adapters {


    public static class CompanyItemViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView imageField;
        TextView description;
        ImageView favoriteIcon;

        public CompanyItemViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.company_name);
            imageField = itemView.findViewById(R.id.company_image);
            description = itemView.findViewById(R.id.company_description);
            favoriteIcon = itemView.findViewById(R.id.favorite_but);
        }

        public Context getContext() {
            return name.getContext();
        }

        public void setFavoriteIcon(boolean favorite, long id) {
            favoriteIcon.setTag(favorite ? "fill" : "hollow");
            favoriteIcon.setOnClickListener(v -> {
                if (v.getTag().toString().equals("hollow")) {
                    favoriteIcon.setImageResource(R.drawable.fill_star_color_icon);
                    v.setTag("fill");
                } else {
                    favoriteIcon.setImageResource(R.drawable.hollow_star_color_icon);
                    v.setTag("hollow");
                }
            });
        }
    }


    static final class CompanyItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private final ArrayList<CompanyNote> notes;


        public CompanyItemAdapter(ArrayList<CompanyNote> notes) {
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

        public void addAll(List<CompanyNote> next) {
            notes.addAll(next);
            notes.add(null);
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
                return new CompanyItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.company_item, parent, false)) {
                };
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof CompanyItemViewHolder) {
                val item = (CompanyItemViewHolder) holder;
                CompanyNote note = notes.get(position);

                item.name.setText(note.getHeader());
                //item.imageField(getResource())
                item.description.setText(note.getContent());
                Glide.with(item.getContext()).load(note.getCompanyImage()).into(item.imageField);
                item.setFavoriteIcon(note.isFavorite(), note.getId());

            }
        }

        @Override
        public int getItemCount() {
            return this.notes.size();
        }
    }
}
