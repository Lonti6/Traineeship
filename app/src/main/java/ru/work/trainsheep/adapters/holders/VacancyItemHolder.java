package ru.work.trainsheep.adapters.holders;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;

import lombok.val;
import org.apmem.tools.layouts.FlowLayout;

import ru.work.trainsheep.FullVacancyActivity;
import ru.work.trainsheep.R;
import ru.work.trainsheep.data.ServerRepositoryFactory;
import ru.work.trainsheep.send.SetFavoriteVacancyRequest;
import ru.work.trainsheep.send.VacancyNote;

import java.util.List;

public class VacancyItemHolder extends RecyclerView.ViewHolder {
    public TextView name;
    public FlowLayout tagsField;
    public TextView description;
    public TextView companyName;
    public TextView salaryText;

    public ImageView favoriteIcon;

    public VacancyItemHolder(View itemView) {
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

    public void setOnClickListener(VacancyNote note)
    {
        itemView.setOnClickListener(v -> {

            Intent intent = new Intent(itemView.getContext(), FullVacancyActivity.class);
            intent.putExtra("note", note);
            itemView.getContext().startActivity(intent);

        });
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
