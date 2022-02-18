package ru.work.trainsheep;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import ru.work.trainsheep.data.Result;
import ru.work.trainsheep.data.ServerRepository;
import ru.work.trainsheep.data.ServerRepositoryFactory;
import ru.work.trainsheep.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public static final String IS_LOGIN = "is_login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        Log.d(getClass().getSimpleName(), "" + bundle);

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
            setContentView(R.layout.fragment_registration);
        }
    }

    public void authorizationUser(View view) {
        ServerRepository server = ServerRepositoryFactory.getInstance();
        EditText mail = findViewById(R.id.mail_field);
        EditText pass = findViewById(R.id.password_field);
        TextView error = findViewById(R.id.error_text_view);
        error.setVisibility(View.GONE);
        server.register(this, new UserRegistrationData(mail.getText().toString(), pass.getText().toString()), (result) -> {
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
}