package ru.work.trainsheep.adapters.holders;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import lombok.val;
import org.apmem.tools.layouts.FlowLayout;
import ru.work.trainsheep.CompanyProfileActivity;
import ru.work.trainsheep.FullVacancyActivity;
import ru.work.trainsheep.MessagesActivity;
import ru.work.trainsheep.R;
import ru.work.trainsheep.data.ServerRepositoryFactory;
import ru.work.trainsheep.send.CompanyNote;
import ru.work.trainsheep.send.SetFavoriteVacancyRequest;
import ru.work.trainsheep.send.UserDataRequest;
import ru.work.trainsheep.send.VacancyNote;

import java.util.List;

public class CompanyItemHolder extends RecyclerView.ViewHolder {
    public TextView name;
    public ImageView imageField;
    public TextView description;
    public Button send;
    public TextView openCompany;


    public CompanyItemHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.company_name);
        imageField = itemView.findViewById(R.id.company_image);
        description = itemView.findViewById(R.id.company_description);
        send = itemView.findViewById(R.id.send_but);
        openCompany = itemView.findViewById(R.id.about_text);
    }

    public void setOnClickListener(CompanyNote note)
    {
        Log.i(getClass().getSimpleName(), "setOnClickListener: " + note);
        send.setOnClickListener(v -> {

            val intent = new Intent(itemView.getContext(), MessagesActivity.class);
            intent.putExtra("name", note.getHeader());
            intent.putExtra("email", note.getEmail());
            intent.putExtra("image", note.getCompanyImage());
            itemView.getContext().startActivity(intent);

        });

        openCompany.setOnClickListener(v -> {
            ServerRepositoryFactory.getInstance().getUser(new UserDataRequest(note.getEmail()), (user) -> {
                val intent = new Intent(itemView.getContext(), CompanyProfileActivity.class);
                intent.putExtra("company", user);
                itemView.getContext().startActivity(intent);
            });

        });
    }
}
