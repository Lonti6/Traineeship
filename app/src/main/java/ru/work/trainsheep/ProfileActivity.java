package ru.work.trainsheep;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import org.apmem.tools.layouts.FlowLayout;

import lombok.val;
import ru.work.trainsheep.data.ServerRepository;
import ru.work.trainsheep.data.ServerRepositoryFactory;
import ru.work.trainsheep.data.UserInfo;
import ru.work.trainsheep.send.UserData;

public class ProfileActivity extends AppCompatActivity {

    UserData instance = UserInfo.getInstance().getData();

    FlowLayout flowLayout;
    DataGenerator generator;
    public ImageView icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left_panel);
        final FlowingDrawer mDrawer = Util.connectActivityLayout(this, R.layout.activity_profile);
        generator = new DataGenerator();

        findViewById(R.id.scroller).setOnScrollChangeListener(new ScrollListeners.MyScrollListener(findViewById(R.id.header),
                getDrawable(R.drawable.bg_header)));

        findViewById(R.id.editBut).setOnClickListener(v -> loadActivity(mDrawer));

        icon = findViewById(R.id.icon_user);

        val instance = UserInfo.getInstance().getData();
        ((TextView) findViewById(R.id.name_user_profile)).setText(instance.getFirstName() + " " + instance.getLastName());

        BottomSheetBehavior.from(findViewById(R.id.sheet)).setPeekHeight(0);
        Util.prepareLeftPanel(this);

        flowLayout = findViewById(R.id.fields_field_bottom_sheet);
        findViewById(R.id.tags_but).setTag("tags");
        findViewById(R.id.stages_but).setTag("stages");

        MyListener myListener = new MyListener();

        findViewById(R.id.tags_but).setOnClickListener(myListener);
        findViewById(R.id.stages_but).setOnClickListener(myListener);

        findViewById(R.id.menuBut).setOnClickListener(v -> mDrawer.openMenu(true));

        String q = "";
        for (int i = 0; i < 10000; i++) {
            q += "a";
        }
        ((TextView) findViewById(R.id.user_description)).setText(q);

        (findViewById(R.id.close_but_profile)).setOnClickListener(v -> {
            if (BottomSheetBehavior.from(findViewById(R.id.sheet)).getState() == Integer.parseInt("3")) {
                BottomSheetBehavior.from(findViewById(R.id.sheet)).setState(BottomSheetBehavior.STATE_COLLAPSED);
                BottomSheetBehavior.from(findViewById(R.id.sheet)).setPeekHeight(0, true);
                BottomSheetBehavior.from(findViewById(R.id.sheet)).setState(BottomSheetBehavior.STATE_COLLAPSED);
                BottomSheetBehavior.from(findViewById(R.id.sheet)).setPeekHeight(0, true);
            } else {
                BottomSheetBehavior.from(findViewById(R.id.sheet)).setState(BottomSheetBehavior.STATE_COLLAPSED);
                BottomSheetBehavior.from(findViewById(R.id.sheet)).setPeekHeight(0, true);
            }
        });

        findViewById(R.id.changeIconBut).setOnClickListener(v -> {
            val manager = getSupportFragmentManager();
            val myDialogFragment = new MyDialogFragment(this);
            myDialogFragment.show(manager, "Frag");
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        Util.prepareLeftIcon(this);
        Glide.with(this)
                .load(instance.getAvatarSrc())
                .placeholder(R.drawable.ic_zaticha)
                .error(R.drawable.ic_zaticha)
                .circleCrop()
                .into(icon);
    }

    public static class MyDialogFragment extends DialogFragment {
        ProfileActivity parentActivity;
        public MyDialogFragment(ProfileActivity parentActivity)
        {
            this.parentActivity = parentActivity;
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            getDialog().getWindow().setBackgroundDrawableResource(R.drawable.round_corner);
            View view = inflater.inflate(R.layout.dialog_change_image, container, true);
            view.findViewById(R.id.cancelBut).setOnClickListener(v1 -> getDialog().cancel());
            view.findViewById(R.id.saveBut).setOnClickListener(v ->
            {
                val instance = UserInfo.getInstance().getData();
                ServerRepository serverRepository = ServerRepositoryFactory.getInstance();
                instance.setAvatarSrc(((EditText)view.findViewById(R.id.srcField)).getText().toString());

                serverRepository.sendUser(instance, userData -> Toast.makeText(parentActivity.icon.getContext(),
                        "Аватар обновлён", Toast.LENGTH_SHORT).show());

                Glide.with(parentActivity)
                        .load(instance.getAvatarSrc())
                        .circleCrop()
                        .placeholder(R.drawable.ic_zaticha)
                        .error(R.drawable.ic_zaticha)
                        .into(parentActivity.icon);

                Util.prepareLeftIcon(parentActivity);



                getDialog().cancel();
            });
            return view;
        }

        @Override
        public void onStart() {
            super.onStart();
            int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.85);
            getDialog().getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    private void loadActivity(FlowingDrawer drawer) {
        Intent intent = new Intent(this, EditUserDataActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        this.startActivity(intent);
        drawer.closeMenu(false);
    }

    class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v.getTag().equals("tags")) {
                ((TextView) findViewById(R.id.category_text)).setText(((Button) v).getText());
                flowLayout.removeAllViews();
                BottomSheetBehavior.from(findViewById(R.id.sheet)).setPeekHeight(600, true);
                for (int i = 0; i < ((int) (Math.random() * 20) + 2); i++) {
                    LayoutInflater.from(ProfileActivity.this).inflate(R.layout.tag_item, flowLayout, true);
                    ((TextView) ((ConstraintLayout) (flowLayout.getChildAt(flowLayout.getChildCount() - 1))).getChildAt(0)).setText(generator.getRandom(generator.tags));
                }
                return;
            }
            if (v.getTag().equals("stages")) {
                ((TextView) findViewById(R.id.category_text)).setText(((Button) v).getText());
                flowLayout.removeAllViews();
                for (int i = 0; i < ((int) (Math.random() * 20) + 2); i++) {
                    LayoutInflater.from(ProfileActivity.this).inflate(R.layout.stage_title, flowLayout, true);
                    ((TextView) ((LinearLayout) (flowLayout.getChildAt(flowLayout.getChildCount() - 1))).getChildAt(0)).setText(generator.getRandom(generator.tags));
                    ;
                }
                BottomSheetBehavior.from(findViewById(R.id.sheet)).setPeekHeight(600, true);
            }
        }
    }
}