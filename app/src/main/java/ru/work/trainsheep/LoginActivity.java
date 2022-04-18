package ru.work.trainsheep;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import lombok.val;
import ru.work.trainsheep.data.ServerRepository;
import ru.work.trainsheep.data.ServerRepositoryFactory;
import ru.work.trainsheep.data.UserInfo;
import ru.work.trainsheep.send.UserRegistrationData;

public class LoginActivity extends AppCompatActivity {
    EditText emailView;
    EditText passView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autorization);
        findViewById(R.id.enter).setOnClickListener(this::checkValidEmailAndPassword);
        findViewById(R.id.open_register).setOnClickListener(this::openRegistrationActivity);

        Util.setEditTextFocusListener(this, R.id.mail_field, R.id.password_field);
        emailView = findViewById(R.id.mail_field);
        passView = findViewById(R.id.password_field);
        val info = UserInfo.getInstance();
        info.load(this);
        emailView.setText(info.getRegistrationData().getEmail());
        passView.setText(info.getRegistrationData().getPassword());
    }

    public void checkValidEmailAndPassword(View view) {
        ServerRepository server = ServerRepositoryFactory.getInstance();
        TextView error = findViewById(R.id.error_text_view);
        val email = emailView.getText().toString().trim();
        if (!Util.validEmail(email)){
            error.setText(R.string.no_correct_email);
            error.setVisibility(View.VISIBLE);
            return;
        }

        val password = passView.getText().toString().trim();
        if (!Util.validPassword(password)){
            error.setText(R.string.no_correct_password);
            error.setVisibility(View.VISIBLE);
            return;
        }


        error.setVisibility(View.GONE);
        server.login(new UserRegistrationData("", email, password), (login) -> {
            val info = UserInfo.getInstance().getRegistrationData();
            info.setEmail(email);
            info.setPassword(password);
            UserInfo.getInstance().save(this);
            Toast.makeText(getApplicationContext(), "Здравствуйте, " + login, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }, (ex) -> {
            error.setText("Ошибка!\n" +ex.getMessage());
            error.setVisibility(View.VISIBLE);
        });
    }

    public void openRegistrationActivity(View view) {
        Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
        startActivity(intent);
    }
}