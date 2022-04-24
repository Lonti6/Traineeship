package ru.work.trainsheep;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import java.util.function.Consumer;
import java.util.regex.Pattern;

import lombok.val;
import ru.work.trainsheep.data.ServerRepository;
import ru.work.trainsheep.data.ServerRepositoryFactory;
import ru.work.trainsheep.data.UserInfo;
import ru.work.trainsheep.send.UserData;

public class Util {

    static UserData instance = UserInfo.getInstance().getData();

    static void setEditTextFocusListener(Activity activity, int... ids) {
        for (int id : ids) {
            View view = activity.findViewById(id);
            view.setOnFocusChangeListener((v, hasFocus) -> {
                if (v.hasFocus())
                    ((ConstraintLayout) (v.getParent())).setBackgroundResource(R.drawable.border_style_color);
                else
                    ((ConstraintLayout) (v.getParent())).setBackgroundResource(R.drawable.border_style_gray);
            });
        }
    }

    static void setEditTextCreateFocusListener(Activity activity, int... ids) {
        for (int id : ids) {
            View view = activity.findViewById(id);
            view.setOnFocusChangeListener((v, hasFocus) -> {
                if (v.hasFocus())
                    ((ConstraintLayout) (v.getParent())).setBackgroundResource(R.drawable.half_round_border_style_color);
                else
                    ((ConstraintLayout) (v.getParent())).setBackgroundResource(R.drawable.half_round_border_style_color);
            });
        }
    }

    public static FlowingDrawer connectActivityLayout(Activity activity, int id) {
        RelativeLayout drawer = activity.findViewById(R.id.content_drawer);
        View view = LayoutInflater.from(activity).inflate(id, drawer, false);
        drawer.addView(view);

        final FlowingDrawer mDrawer = activity.findViewById(R.id.drawerlayout);
        mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_FULLSCREEN);
        prepareLeftPanel(activity);
        return mDrawer;
    }

    public static void prepareLeftPanel(Activity activity) {
        final FlowingDrawer drawer = activity.findViewById(R.id.drawerlayout);

        instance = UserInfo.getInstance().getData();

        Glide.with(activity)
                .load(instance.getAvatarSrc())
                .circleCrop()
                .into((ImageView) activity.findViewById(R.id.left_icon_user));

        ((TextView) activity.findViewById(R.id.name_user)).setText(instance.getFirstName() + " " + instance.getLastName());

        val admins = activity.findViewById(R.id.admins);

        if (ServerRepositoryFactory.IS_ADMIN){
            admins.setVisibility(View.VISIBLE);
            admins.setOnClickListener((v) -> {
                Intent intent = new Intent(activity, AdminActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                activity.startActivity(intent);
                drawer.closeMenu(false);
            });
        }

        (activity.findViewById(R.id.search_line)).setOnClickListener(v -> {
            Intent intent = new Intent(activity, SearchActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            activity.startActivity(intent);
            drawer.closeMenu(false);
        });

        (activity.findViewById(R.id.favorite_line)).setOnClickListener(v -> {
            Intent intent = new Intent(activity, FavoriteActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            activity.startActivity(intent);
            drawer.closeMenu(false);
        });

        (activity.findViewById(R.id.create_line)).setOnClickListener(v -> {
            Intent intent = new Intent(activity, CreateActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            activity.startActivity(intent);
            drawer.closeMenu(false);
        });

        (activity.findViewById(R.id.message_line)).setOnClickListener(v -> {
            Intent intent = new Intent(activity, AllChatsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            activity.startActivity(intent);
            drawer.closeMenu(false);
        });

        (activity.findViewById(R.id.profile_line)).setOnClickListener(v -> {
            Intent intent;
            if (!UserInfo.getInstance().getData().isCompany())
                intent = new Intent(activity, ProfileActivity.class);
            else
                intent = new Intent(activity, CompanyProfileActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            activity.startActivity(intent);
            drawer.closeMenu(false);
        });

        (activity.findViewById(R.id.settings_line)).setOnClickListener(v -> {
            Intent intent = new Intent(activity, SettingsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            activity.startActivity(intent);
            drawer.closeMenu(false);
        });

        (activity.findViewById(R.id.contacts_line)).setOnClickListener(v -> {
            Intent intent = new Intent(activity, ContactsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            activity.startActivity(intent);
            drawer.closeMenu(false);
        });
    }

    public static void loadActivity(FlowingDrawer drawer, Activity activity, Class c) {
        Intent intent = new Intent(activity, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        activity.startActivity(intent);
        drawer.closeMenu(false);
    }

    public static void prepareLeftData(Activity activity) {
        instance = UserInfo.getInstance().getData();
        if (!instance.isCompany())
            activity.findViewById(R.id.create_line).setVisibility(View.GONE);
        else
            activity.findViewById(R.id.create_line).setVisibility(View.VISIBLE);
        Log.e("//////////////////////////////////////////////", String.valueOf(instance.isCompany()));

        val data = UserInfo.getInstance();
        //val imageSrc = extras.getString("image");
        ((TextView) activity.findViewById(R.id.name_user)).setText(data.getRegistrationData().getName() +
                                            " " + data.getRegistrationData().getLastName());
        Glide.with(activity)
                .load(data.getData().getAvatarSrc())
                .circleCrop()
                .placeholder(R.drawable.ic_zaticha)
                .error(R.drawable.ic_zaticha)
                .into((ImageView) activity.findViewById(R.id.left_icon_user));
    }



    private static final Pattern VALID_NAME = Pattern.compile("^[A-ZА-Я]+$", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);

    private static final Pattern VALID_EMAIL_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validEmail(String email) {
        return VALID_EMAIL_REGEX.matcher(email).find();
    }

    public static boolean validName(String name) {
        return VALID_NAME.matcher(name).find();
    }

    public static boolean validPassword(String pass1) {
        return pass1.length() > 6;
    }
}
