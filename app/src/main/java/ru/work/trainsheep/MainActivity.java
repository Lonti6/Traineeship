package ru.work.trainsheep;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;

import ru.work.trainsheep.data.ServerRepository;
import ru.work.trainsheep.data.ServerRepositoryFactory;
import ru.work.trainsheep.databinding.ActivityMainBinding;

import javax.xml.transform.Result;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public static final String IS_LOGIN = "is_login";
    private static EditTextActivitys activitys = new EditTextActivitys();
    ArrayList<EditText> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        Log.d(getClass().getSimpleName(), "" + bundle);

        // пример получения объявлений, пустой массив тегов означает поиск всех
        ServerRepository server = ServerRepositoryFactory.getInstance();
        server.getAdverts(new AdvertRequest(new ArrayList<String>(), 3, 11), (result) ->{
            if (result.isSuccess()){
                System.out.println(result.getResult());
            } else {
                result.getException().printStackTrace();
            }
        });

        if (bundle != null && bundle.containsKey(IS_LOGIN)){
            binding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            BottomNavigationView navView = findViewById(R.id.nav_view);
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navigation_search, R.id.navigation_favorite, R.id.navigation_messages, R.id.navigation_profile, R.id.navigation_advert)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
            //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(binding.navView, navController);
        } else {
            setContentView(R.layout.fragment_autorization);
            list.add(findViewById(R.id.mail_field));
            list.add(findViewById(R.id.password_field));
            for (EditText field: list)
            {
                field.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (v.hasFocus())
                            ((RelativeLayout)(v.getParent())).setBackgroundResource(R.drawable.border_style_color);
                        else
                            ((RelativeLayout)(v.getParent())).setBackgroundResource(R.drawable.border_style_gray);
                    }
                });
            }
        }

    }

    public void authorizationUser(View view) {
        ServerRepository server = ServerRepositoryFactory.getInstance();
        EditText mail = findViewById(R.id.mail_field);
        EditText pass = findViewById(R.id.password_field);
        TextView error = findViewById(R.id.error_text_view);
        error.setVisibility(View.GONE);
        server.register(new UserRegistrationData(mail.getText().toString(), pass.getText().toString()), (result) -> {
            if(result.isError()){
                error.setVisibility(View.VISIBLE);
            }else{
                String login = result.getResult().getUsername();
                Toast.makeText(getApplicationContext(), "Здравствуйте, " + login, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra(IS_LOGIN, "true");
                startActivity(intent);
            }
        });
    }

    public void setRegistrationScreen(View view)
    {
        setContentView(R.layout.fragment_registration);
        list.add(findViewById(R.id.reg_name_field));
        list.add(findViewById(R.id.reg_mail_field));
        list.add(findViewById(R.id.reg_password_field));
        list.add(findViewById(R.id.reg_conf_password_field));
        for (EditText field: list)
        {
            field.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (v.hasFocus())
                        ((RelativeLayout)(v.getParent())).setBackgroundResource(R.drawable.border_style_color);
                    else
                        ((RelativeLayout)(v.getParent())).setBackgroundResource(R.drawable.border_style_gray);
                }
            });
        }
    }

    public void setAutorizationScreen(View view)
    {
        setContentView(R.layout.fragment_autorization);
        list.add(findViewById(R.id.mail_field));
        list.add(findViewById(R.id.password_field));
        for (EditText field: list)
        {
            field.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (v.hasFocus())
                        ((RelativeLayout)(v.getParent())).setBackgroundResource(R.drawable.border_style_color);
                    else
                        ((RelativeLayout)(v.getParent())).setBackgroundResource(R.drawable.border_style_gray);
                }
            });
        }
    }
}