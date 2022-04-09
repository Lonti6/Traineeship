package ru.work.trainsheep;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import ru.work.trainsheep.data.ServerRepository;
import ru.work.trainsheep.data.ServerRepositoryFactory;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autorization);
        findViewById(R.id.enter).setOnClickListener(this::checkValidEmailAndPassword);
        findViewById(R.id.open_register).setOnClickListener(this::openRegistrationActivity);

        Util.setEditTextFocusListener(this, R.id.mail_field, R.id.password_field);
    }

    public void checkValidEmailAndPassword(View view) {
        ServerRepository server = ServerRepositoryFactory.getInstance();
        EditText mail = findViewById(R.id.mail_field);
        EditText pass = findViewById(R.id.password_field);
        TextView error = findViewById(R.id.error_text_view);
        error.setVisibility(View.GONE);
        server.register(new UserRegistrationData(mail.getText().toString(), pass.getText().toString()), (result) -> {
            String login = result.getEmail();
            Toast.makeText(getApplicationContext(), "Здравствуйте, " + login, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }, (ex) -> error.setVisibility(View.VISIBLE));
    }

    public void openRegistrationActivity(View view) {
        Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
        startActivity(intent);
    }
}