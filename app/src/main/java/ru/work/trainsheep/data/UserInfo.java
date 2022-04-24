package ru.work.trainsheep.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import androidx.preference.PreferenceManager;

import java.util.ArrayList;

import lombok.*;
import ru.work.trainsheep.send.UserData;
import ru.work.trainsheep.send.UserDataRequest;
import ru.work.trainsheep.send.UserRegistrationData;


public class UserInfo {

    @Getter
    UserData data;

    @Getter
    UserRegistrationData registrationData;

    @Getter
    @Setter
    boolean login = false;

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
        editor.putString("lastname", registrationData.getLastName());
        editor.putBoolean("login", login);
        editor.putString("image", data.getAvatarSrc());
        editor.putBoolean("company", registrationData.isCompany());
        editor.apply();
    }
    public void load(Context context){
        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(context);
        setName(shared.getString("name", getRegistrationData().getName()));
        setPassword(shared.getString("pass", getRegistrationData().getPassword()));
        setEmail(shared.getString("email", getRegistrationData().getEmail()));
        setLastName(shared.getString("lastname", getData().getLastName()));
        setLogin(shared.getBoolean("login", isLogin()));
        data.setAvatarSrc(shared.getString("image", getData().getAvatarSrc()));
        setCompany(shared.getBoolean("company", getData().isCompany()));

        ServerRepository serverRepository = ServerRepositoryFactory.getInstance();
        serverRepository.getUser(new UserDataRequest(getData().getEmail()), userData -> {
            data = userData;
            Log.i(getClass().getSimpleName(), "load: set Data" + data);
        });
    }

    public void setLastName(String lastname) {
        data.setLastName(lastname);
        registrationData.setLastName(lastname);
    }

    public void setCompany(Boolean isCompany)
    {
        data.setCompany(isCompany);
        registrationData.setCompany(isCompany);
    }
}
