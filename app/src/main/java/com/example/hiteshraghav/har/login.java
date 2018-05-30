

package com.example.hiteshraghav.har;

        import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class login extends AppCompatActivity {

    //Button button_login ;
    private Button btn_login,btn_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
       // button_login= (Button) findViewById(R.id.btn_login);
        btn_login= (Button) findViewById(R.id.btn_login);
       /* button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/Activity Recognition");
                if (!folder.exists()){
                    Toast.makeText(getApplicationContext(), "Activity Recognition folder not found.", Toast.LENGTH_LONG).show();
                    return;
                }
                String filename = folder.toString() + "/current-user" ;

                try{
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
                    String line;
                    while((line = bufferedReader.readLine()) != null){
                        String[] tokens = line.split("$");
                        // Check Tokens first, how they are stored in registration activity
                        String username = tokens[0];
                        String password = tokens[1];
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }



            }



        });*/

        btn_login.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login.this, MainActivity.class));
                finish();
                //      Button button_login=button_login.setOnClickListener();


            }
        });
    }
}
