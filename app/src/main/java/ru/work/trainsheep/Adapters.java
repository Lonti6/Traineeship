package ru.work.trainsheep;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.apmem.tools.layouts.FlowLayout;

import java.util.ArrayList;
import java.util.List;

import lombok.val;
import ru.work.trainsheep.send.CompanyNote;
import ru.work.trainsheep.send.VacancyNote;

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

        public VacancyItemViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.advert_name);
            tagsField = itemView.findViewById(R.id.tags_field);
            description = itemView.findViewById(R.id.advert_description);
            companyName = itemView.findViewById(R.id.company_name);
            salaryText = itemView.findViewById(R.id.salary_text);
        }

        public void addTags(List<String> tags) {
            for (String tag : tags) {
                val view = LayoutInflater.from(itemView.getContext()).inflate(R.layout.tag_item, tagsField, false);
                tagsField.addView(view);
                ((TextView) view.findViewById(R.id.tag)).setText(tag);
            }
        }
    }

    public static class CompanyItemViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView imageField;
        TextView description;

        public CompanyItemViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.company_name);
            imageField = itemView.findViewById(R.id.company_image);
            description = itemView.findViewById(R.id.company_description);
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
            notes.addAll(next);
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
                return new VacancyItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.advert_item, parent, false)) {
                };
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
                return new VacancyItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.company_item, parent, false)) {
                };
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof CompanyItemViewHolder) {
                val item = (CompanyItemViewHolder) holder;
                CompanyNote note = notes.get(position);

                Log.e("note.getHeader()", "note.getHeader()");

                item.name.setText(note.getHeader());
                //item.imageField(getResource())
                item.description.setText(note.getContent());
            }
        }

        @Override
        public int getItemCount() {
            return this.notes.size();
        }
    }
}
