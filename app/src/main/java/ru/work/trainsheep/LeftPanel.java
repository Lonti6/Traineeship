package ru.work.trainsheep;

import android.app.Activity;
import android.content.Intent;

import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;

public class LeftPanel {
    private Drawer.Result panel;

    public LeftPanel(Drawer.Result panel) {
        this.panel = panel;
    }

    public static LeftPanel createFor(Activity activity){
        LeftPanel l = new LeftPanel(new Drawer()
                .withActivity(activity)
                .withHeader(R.layout.drawer_header)
                .addDrawerItems(
                        new SectionDrawerItem().withName("Взаимодействие"),
                        new PrimaryDrawerItem().withName("Поиск").withIcon(activity.getDrawable(R.drawable.search_icon)).withIdentifier(1),
                        new PrimaryDrawerItem().withName("Сообщения").withIcon(activity.getDrawable(R.drawable.ic_message)).withIdentifier(2),
                        new SectionDrawerItem().withName("Пользователь"),
                        new SecondaryDrawerItem().withName("Профиль").withIcon(activity.getDrawable(R.drawable.people_icon)).withIdentifier(3),
                        new SecondaryDrawerItem().withName("Настройки").withIcon(activity.getDrawable(R.drawable.settings_icon)).withIdentifier(4),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName("Наши контакты").withIcon(FontAwesome.Icon.faw_github).withBadge("12+").withIdentifier(5)
                )
                .withOnDrawerItemClickListener((parent, view, position, id, drawerItem) -> {
                    if (drawerItem != null) {
                        if (drawerItem.getIdentifier() == 1) {
                            Intent intent = new Intent(activity, MainActivity.class);
                            activity.startActivity(intent);
                        } else if (drawerItem.getIdentifier() == 2) {
                            Intent intent = new Intent(activity, AllChatsActivity.class);
                            activity.startActivity(intent);
                        } else if (drawerItem.getIdentifier() == 3) {
                            Intent intent = new Intent(activity, ProfileActivity.class);
                            activity.startActivity(intent);
                        }
                    }
                }
                ).build());
        return l;
    }

    public void open(){
        panel.openDrawer();
    }

    public void close() {
        panel.closeDrawer();
    }
}
