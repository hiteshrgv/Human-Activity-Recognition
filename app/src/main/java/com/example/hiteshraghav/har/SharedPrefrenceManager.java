package com.example.hiteshraghav.har;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPrefrenceManager {
    Context context;
    public static String doctorPrefName="doctorDetailsPref";
    public static String zoomPrefName="zoomTokenPref";
    public static final String firstName = "firstNameKey";
    public static final String lastName = "lastNameKey";
    public static final String phone = "phoneKey";
    public static final String email = "emailKey";
    public static final String password = "passwordKey";
    public static final String age = "ageKey";
    public static final String gender = "genderKey";
    public static final String activationStatus="activationStatusKey";
    public static final String pid="pid";
    public static final String add="address";
    int flag=0;
    public SharedPrefrenceManager(Context context){
        this.context=context;
    }



    public void storePatientDetails(String firstName, String lastName, String age, String gender, String phone, String email, String password, String pid){
        flag=0;
        SharedPreferences sharedpreferences = context.getSharedPreferences(doctorPrefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(this.firstName,firstName);
        editor.putString(this.lastName,lastName);
        editor.putString(this.age,age);
        editor.putString(this.gender,gender);
        editor.putString(this.phone,phone);
        editor.putString(this.email,email);
        editor.putString(this.password,password);
        editor.putString(this.pid,pid);

        editor.commit();
    }

    public String getRegisterdPid(){
        SharedPreferences sharedpreferences = context.getSharedPreferences(doctorPrefName, Context.MODE_PRIVATE);
        return sharedpreferences.getString(pid,null);
    }
    public String getRegisterdGender(){
        SharedPreferences sharedpreferences = context.getSharedPreferences(doctorPrefName, Context.MODE_PRIVATE);
        return sharedpreferences.getString(gender,null);
    }
    public String getRegisterdEmailId(){
        SharedPreferences sharedpreferences = context.getSharedPreferences(doctorPrefName, Context.MODE_PRIVATE);
        return sharedpreferences.getString(email,null);
    }

    public String getRegisterdPassword(){
        SharedPreferences sharedpreferences = context.getSharedPreferences(doctorPrefName, Context.MODE_PRIVATE);
        return sharedpreferences.getString(password,null);
    }
    public String getRegisterdPhone(){
        SharedPreferences sharedpreferences = context.getSharedPreferences(doctorPrefName, Context.MODE_PRIVATE);
        return sharedpreferences.getString(phone,null);
    }
    public String getRegisterdAge(){
        SharedPreferences sharedpreferences = context.getSharedPreferences(doctorPrefName, Context.MODE_PRIVATE);
        return sharedpreferences.getString(age,null);
    }

    public String getFirstName(){
        SharedPreferences sharedpreferences = context.getSharedPreferences(doctorPrefName, Context.MODE_PRIVATE);
        return sharedpreferences.getString(firstName,null);
    }

    public String getLastName(){
        SharedPreferences sharedpreferences = context.getSharedPreferences(doctorPrefName, Context.MODE_PRIVATE);
        return sharedpreferences.getString(lastName,null);
    }



}
