package ru.work.trainsheep;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class RegisterActivity extends AppCompatActivity {

    EditText login;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        login = findViewById(R.id.edit_login);
        password = findViewById(R.id.edit_password);
        Button button = findViewById(R.id.button_register);
        button.setOnClickListener(this::onClickRegisterButton);
    }

    public void onClickRegisterButton(View view){
        //TODO send quest login and password on server
    }
}