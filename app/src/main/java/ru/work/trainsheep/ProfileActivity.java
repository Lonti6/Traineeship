package ru.work.trainsheep;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import org.apmem.tools.layouts.FlowLayout;

import java.io.IOException;

import lombok.val;

public class ProfileActivity extends AppCompatActivity {

    static final int GALLERY_REQUEST = 1;

    FlowLayout flowLayout;
    DataGenerator generator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left_panel);
        final FlowingDrawer mDrawer = Util.connectActivityLayout(this, R.layout.activity_profile);
        generator = new DataGenerator();

        findViewById(R.id.scroller).setOnScrollChangeListener(new ScrollListeners.MyScrollListener(findViewById(R.id.header),
                getDrawable(R.drawable.bg_header)));

        findViewById(R.id.editBut).setOnClickListener(v -> LoadActivity(mDrawer));

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
        for (int i = 0; i <10000; i++)
        {
            q+="a";
        }
        ((TextView)findViewById(R.id.user_description)).setText(q);

        (findViewById(R.id.close_but_profile)).setOnClickListener(v -> {
            if (BottomSheetBehavior.from(findViewById(R.id.sheet)).getState() == Integer.parseInt("3")) {
                BottomSheetBehavior.from(findViewById(R.id.sheet)).setState(BottomSheetBehavior.STATE_COLLAPSED);
                BottomSheetBehavior.from(findViewById(R.id.sheet)).setPeekHeight(0, true);
                BottomSheetBehavior.from(findViewById(R.id.sheet)).setState(BottomSheetBehavior.STATE_COLLAPSED);
                BottomSheetBehavior.from(findViewById(R.id.sheet)).setPeekHeight(0, true);
            }
            else
                {
                    BottomSheetBehavior.from(findViewById(R.id.sheet)).setState(BottomSheetBehavior.STATE_COLLAPSED);
                    BottomSheetBehavior.from(findViewById(R.id.sheet)).setPeekHeight(0, true);
                }
        });

        findViewById(R.id.changeIconBut).setOnClickListener(v -> {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
        });

    }

    private void LoadActivity(FlowingDrawer drawer)
    {
        Intent intent = new Intent(this, EditUserDataActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        this.startActivity(intent);
        drawer.closeMenu(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        Bitmap bitmap = null;
        ImageView imageView = findViewById(R.id.icon_user);

        switch(requestCode) {
            case GALLERY_REQUEST:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    imageView.setImageBitmap(bitmap);
                }
        }
    }

    class MyListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            if (v.getTag().equals("tags"))
            {
                ((TextView)findViewById(R.id.category_text)).setText(((Button)v).getText());
                flowLayout.removeAllViews();
                BottomSheetBehavior.from(findViewById(R.id.sheet)).setPeekHeight(600, true);
                for (int i = 0; i<((int)(Math.random()*20)+2); i++)
                {
                    LayoutInflater.from(ProfileActivity.this).inflate(R.layout.tag_item, flowLayout, true);
                    ((TextView)((ConstraintLayout)(flowLayout.getChildAt(flowLayout.getChildCount()-1))).getChildAt(0)).setText(generator.getRandom(generator.tags));
                }
                return;
            }
            if (v.getTag().equals("stages"))
            {
                ((TextView)findViewById(R.id.category_text)).setText(((Button)v).getText());
                flowLayout.removeAllViews();
                for (int i = 0; i<((int)(Math.random()*20)+2); i++)
                {
                    LayoutInflater.from(ProfileActivity.this).inflate(R.layout.stage_title, flowLayout, true);
                    ((TextView)((LinearLayout)(flowLayout.getChildAt(flowLayout.getChildCount()-1))).getChildAt(0)).setText(generator.getRandom(generator.tags));;
                }
                BottomSheetBehavior.from(findViewById(R.id.sheet)).setPeekHeight(600, true);
            }
        }
    }
}