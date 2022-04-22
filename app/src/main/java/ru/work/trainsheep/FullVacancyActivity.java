package ru.work.trainsheep;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import org.apmem.tools.layouts.FlowLayout;

import lombok.val;
import ru.work.trainsheep.data.UserInfo;
import ru.work.trainsheep.data.getting.CompanyResultGetting;
import ru.work.trainsheep.send.CompanyNote;
import ru.work.trainsheep.send.CompanyRequest;
import ru.work.trainsheep.send.CompanyResult;
import ru.work.trainsheep.send.UserData;
import ru.work.trainsheep.send.VacancyNote;

public class FullVacancyActivity extends AppCompatActivity {
    UserData instance = UserInfo.getInstance().getData();
    VacancyNote note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left_panel);
        final FlowingDrawer drawer = Util.connectActivityLayout(this, R.layout.activity_full_vacancy);
        findViewById(R.id.scroller).setOnScrollChangeListener(new ScrollListeners.MyScrollListener(findViewById(R.id.header),
                getDrawable(R.drawable.bg_header)));

        findViewById(R.id.menuBut).setOnClickListener(v -> Util.loadActivity(drawer, this, SearchActivity.class));

        uploadData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        uploadData();
        Glide.with(this)
                .load(instance.getAvatarSrc())
                .circleCrop()
                .into((ImageView) this.findViewById(R.id.left_icon_user));
    }

    private void uploadData()
    {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            note = (VacancyNote) extras.getSerializable("note");
            ((TextView)findViewById(R.id.companyNameHeader)).setText(note.getCompany());
            ((TextView)findViewById(R.id.vacancyNameText)).setText(note.getHeader());
            ((TextView)findViewById(R.id.cityText)).setText("Населённый пункт: "+note.getCity());
            ((TextView)findViewById(R.id.zpText)).setText("Оплата в месяц: "+note.getSalary());
            ((TextView)findViewById(R.id.workTimeText)).setText("План работы: "+note.getWorkingHours() + " часов в неделю");
            ((TextView)findViewById(R.id.descriptionText)).setText(note.getContent());
            ((TextView)findViewById(R.id.name_top)).setText(note.getHeader());

            ((CheckBox)findViewById(R.id.isFurtherCooperationBox)).setChecked(note.isFurtherCooperation());
            ((CheckBox)findViewById(R.id.contractualSalaryBox)).setChecked(note.isFurtherCooperation());
            ((CheckBox)findViewById(R.id.distanceWorkBox)).setChecked(note.isDistanceWork());

            FlowLayout flowLayout = findViewById(R.id.tags_field);
            flowLayout.removeAllViews();
            for (String tag: note.getTags())
            {
                val view = LayoutInflater.from(this).inflate(R.layout.tag_item, flowLayout, false);
                flowLayout.addView(view);
                ((TextView) view.findViewById(R.id.tag)).setText(tag);
            }

            Glide.with(this)
                    .load(note.getImageSrc())
                    .into(((ImageView)findViewById(R.id.imageView)));
            //((CheckBox)findViewById(R.id.contractualSalary)).setChecked(note.get);
            // если правильно открыть активити, то в note будут данные о вакансии
        }
    }
}
