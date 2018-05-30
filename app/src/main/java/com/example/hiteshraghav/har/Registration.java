package com.example.hiteshraghav.har;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Registration extends AppCompatActivity {

    private Button btn_login,btn_signup;
    private EditText edit_username,edit_emailid,edit_pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);



        initUI();
        getPermissions();

        btn_signup.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/Activity Recognition");
                boolean createdDirectories = false;
                if (!folder.exists())
                    createdDirectories = folder.mkdirs();

                Log.i("DIRECTORIES", "Directories create: " + createdDirectories);
                String filename = folder.toString() + "/current-user" ;
                String data = edit_emailid.getText() + "$" + edit_pass + "$" + edit_username + "\n";
                if (isExternalStorageWritable()){
                    try{
                        FileWriter fileWriter = new FileWriter(filename, true);
                        PrintWriter printWriter = new PrintWriter(fileWriter);
                        printWriter.append(data);
                        printWriter.flush();
                        fileWriter.close();
                        Log.i("DATA", "Written: " + data);
                    }catch (Exception e) {
                        Log.e("Exception", e.toString());
                    }
                }
                else {
                    Log.e("DIRECTORIES", "Media not accessible.");
                }
            }
        });

        btn_login.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), login.class));
                finish();
          //      Button button_login=button_login.setOnClickListener();


            }
        });

        btn_signup.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), login.class));
                finish();
                //      Button button_login=button_login.setOnClickListener();


            }
        });
    }

    private void initUI() {
        edit_username = findViewById(R.id.edit_username);
        edit_emailid = findViewById(R.id.edit_email);
        edit_pass = findViewById(R.id.edit_pass);
        btn_login = findViewById(R.id.btn_login);
        btn_signup = findViewById(R.id.btn_signup);
    }

    public void getPermissions() {
        if (ContextCompat.checkSelfPermission(Registration.this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (!ActivityCompat.shouldShowRequestPermissionRationale(Registration.this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(
                        Registration.this,
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1
                );
            }
        }
        Log.i("PERMISSIONS", "Storage permissions taken");
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return (Environment.MEDIA_MOUNTED.equals(state));
    }
}
