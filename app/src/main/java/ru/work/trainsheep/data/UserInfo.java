package ru.work.trainsheep.data;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;
import lombok.Value;
import lombok.val;
import ru.work.trainsheep.send.UserData;
import ru.work.trainsheep.send.UserRegistrationData;

@Value
public class UserInfo {

    UserData data;
    UserRegistrationData registrationData;

    private UserInfo(){
        data = new UserData();
        registrationData = new UserRegistrationData();
    }

    private static UserInfo instance;
    public static UserInfo getInstance(){
        if (instance == null)
            instance = new UserInfo();
        return instance;
    }

    public void setEmail(String email){
        data.setEmail(email);
        registrationData.setEmail(email);
    }

    public void setPassword(String password){
        registrationData.setPassword(password);
    }

    public void setName(String name){
        data.setFirstName(name);
        registrationData.setName(name);
    }

    public void save(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        val editor = sharedPreferences.edit();
        editor.putString("name", registrationData.getName());
        editor.putString("email", registrationData.getEmail());
        editor.putString("pass", registrationData.getPassword());
        editor.apply();
    }
    public void load(Context context){
        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(context);
        setName(shared.getString("name", ""));
        setPassword(shared.getString("pass", ""));
        setEmail(shared.getString("email", ""));
    }
}
