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
import ru.work.trainsheep.data.ServerRepositoryFactory;
import ru.work.trainsheep.send.CompanyNote;
import ru.work.trainsheep.send.SetFavoriteVacancyRequest;
import ru.work.trainsheep.send.VacancyNote;

import java.util.ArrayList;
import java.util.List;

public class Adapters {

    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View v) {
            super(v);
            // Добавьте компоненты пользовательского интерфейса здесь.
        }
    }

    public static class VacancyItemViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        FlowLayout tagsField;
        TextView description;
        TextView companyName;
        TextView salaryText;

        ImageView favoriteIcon;

        public VacancyItemViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.advert_name);
            tagsField = itemView.findViewById(R.id.tags_field);
            description = itemView.findViewById(R.id.advert_description);
            companyName = itemView.findViewById(R.id.company_name);
            salaryText = itemView.findViewById(R.id.salary_text);
            favoriteIcon = itemView.findViewById(R.id.favorite_but);
        }

        public void addTags(List<String> tags) {
            for (String tag : tags) {
                val view = LayoutInflater.from(itemView.getContext()).inflate(R.layout.tag_item, tagsField, false);
                tagsField.addView(view);
                ((TextView) view.findViewById(R.id.tag)).setText(tag);
            }
        }

        public void setFavoriteIcon(boolean favorite, long id) {
            val server = ServerRepositoryFactory.getInstance();
            favoriteIcon.setTag(favorite ? "fill" : "hollow");

            if (favoriteIcon.getTag().toString().equals("hollow"))
                favoriteIcon.setImageResource(R.drawable.hollow_star_color_icon);
            else
                favoriteIcon.setImageResource(R.drawable.fill_star_color_icon);

            favoriteIcon.setOnClickListener(v -> {
                if (v.getTag().toString().equals("hollow")) {
                    favoriteIcon.setImageResource(R.drawable.fill_star_color_icon);
                    v.setTag("fill");
                    server.setFavoriteVacancy(new SetFavoriteVacancyRequest(id, true), (note) -> {
                        Log.i(getClass().getSimpleName(), "setFavoriteIcon: server: " + note);
                    }, (th) -> {
                        Toast.makeText(name.getContext(), "Error: " + th.getMessage(), Toast.LENGTH_SHORT).show();
                        favoriteIcon.setImageResource(R.drawable.hollow_star_color_icon);
                        v.setTag("hollow");
                    });
                } else {
                    favoriteIcon.setImageResource(R.drawable.hollow_star_color_icon);
                    v.setTag("hollow");
                    server.setFavoriteVacancy(new SetFavoriteVacancyRequest(id, false), (note) -> {
                        Log.i(getClass().getSimpleName(), "setFavoriteIcon: server: " + note);

                    }, (th) -> {
                        Toast.makeText(name.getContext(), "Error: " + th.getMessage(), Toast.LENGTH_SHORT).show();
                        favoriteIcon.setImageResource(R.drawable.fill_star_color_icon);
                        v.setTag("fill");
                    });
                }
            });
        }
    }

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

    static final class VacancyItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private final ArrayList<VacancyNote> notes;


        public VacancyItemAdapter(ArrayList<VacancyNote> notes) {
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


        public void addAll(List<VacancyNote> next) {
            if (notes.isEmpty()) {
                notes.add(null);
            }
            val start = notes.size() - 1;
            notes.addAll(start, next);
            notifyItemRangeInserted(start, next.size());
        }

        public void clearAndAddAll(List<VacancyNote> next) {
            notes.clear();
            notes.addAll(next);
            notes.add(null);
            notifyDataSetChanged();
        }


        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            if (viewType == notes.size() - 1) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_search, parent, false);
                return new Adapters.FooterViewHolder(view);
            } else
                return new VacancyItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.advert_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof VacancyItemViewHolder) {
                val item = (VacancyItemViewHolder) holder;
                VacancyNote note = notes.get(position);

                item.name.setText(note.getHeader());
                item.tagsField.removeAllViews();
                item.description.setText(note.getContent());
                item.companyName.setText(note.getCompany());
                item.salaryText.setText(note.getSalary());
                item.addTags(note.getTags());
                item.setFavoriteIcon(note.isFavorite(), note.getId());
            }
        }

        @Override
        public int getItemCount() {
            return this.notes.size();
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
                return new Adapters.FooterViewHolder(view);
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
