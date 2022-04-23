package ru.work.trainsheep;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import lombok.val;
import ru.work.trainsheep.data.ServerRepositoryFactory;
import ru.work.trainsheep.data.UserInfo;
import ru.work.trainsheep.send.UserRegistrationData;

import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        findViewById(R.id.registrationButton).setOnClickListener((v) -> checkFormAndSend());
        findViewById(R.id.backButton).setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            this.startActivity(intent);
        });

        CheckBox checkBox = findViewById(R.id.companyCheck);
        checkBox.setOnClickListener(v -> {
            if (checkBox.isChecked()) {
                ((EditText) findViewById(R.id.reg_name_field)).setHint("Наименование компании");
                ((EditText) findViewById(R.id.reg_last_name_field)).setText("");
                (findViewById(R.id.relativeLayout)).setVisibility(View.GONE);
            }
            else
            {
                ((EditText)findViewById(R.id.reg_name_field)).setHint("Имя");
                (findViewById(R.id.relativeLayout)).setVisibility(View.VISIBLE);
            }
        });
    }


    private void checkFormAndSend(){
        val errorView = (TextView) findViewById(R.id.error_info);
        val name = ((EditText) findViewById(R.id.reg_name_field)).getText().toString().trim();
        if (!Util.validName(name)){
            errorView.setText(R.string.no_correct_name);
            errorView.setVisibility(View.VISIBLE);
            return;
        }
        val lastname = ((EditText) findViewById(R.id.reg_last_name_field)).getText().toString().trim();

        val email = ((EditText) findViewById(R.id.reg_mail_field)).getText().toString().trim();
        if (!Util.validEmail(email)){
            errorView.setText(R.string.no_correct_email);
            errorView.setVisibility(View.VISIBLE);
            return;
        }

        val pass = ((EditText) findViewById(R.id.reg_password_field)).getText().toString().trim();
        if (!Util.validPassword(pass)){
            errorView.setText(R.string.no_correct_password);
            errorView.setVisibility(View.VISIBLE);
            return;
        }
        if (!pass.equals( ((EditText) findViewById(R.id.reg_conf_password_field)).getText().toString())){
            errorView.setText(R.string.no_equals_password);
            errorView.setVisibility(View.VISIBLE);
            return;
        }

        val info = UserInfo.getInstance();
        info.setName(name);
        info.setLastName(lastname);
        info.setEmail(email);
        info.setPassword(pass);
        info.getRegistrationData().setCompany(((CheckBox)findViewById(R.id.companyCheck)).isChecked());

        info.save(this);

        sendRegisterRequest();
    }

    private void sendRegisterRequest(){
        val errorView = (TextView) findViewById(R.id.error_info);
        val server = ServerRepositoryFactory.getInstance();
        server.register(UserInfo.getInstance().getRegistrationData(), (name) -> {
            UserInfo.getInstance().setLogin(true);
            UserInfo.getInstance().save(this);
            Toast.makeText(getApplicationContext(), "Здравствуйте, " + name, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }, (exc) -> {
            errorView.setText(R.string.email_already_exist);
            errorView.setVisibility(View.VISIBLE);
        });
    }


}