package ru.work.trainsheep;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import lombok.val;
import ru.work.trainsheep.data.ServerRepositoryFactory;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        findViewById(R.id.registrationButton).setOnClickListener(this::register);
        findViewById(R.id.backButton).setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            this.startActivity(intent);
        });
    }

    private void register(View view) {
        Toast.makeText(getApplicationContext(), "Регистрация...", Toast.LENGTH_SHORT).show();
        ServerRepositoryFactory.getInstance().register(new UserRegistrationData("Login", "pass"), (res) -> {
            val intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }
}