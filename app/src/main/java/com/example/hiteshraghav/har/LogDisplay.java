package  com.example.hiteshraghav.har.database;

import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.hiteshraghav.har.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;

public class LogDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_display);

        // iss textview kii kya zarurt hai??
        //TextView lg2 =(TextView)  findViewById(R.id.lg);

        TableLayout tableLayout=findViewById(R.id.table1);

        File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/Activity Recognition");

        String fileName = folder.toString() + "/Log.csv" ;

        BufferedReader br = null;
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(fileName));
            while ((sCurrentLine = br.readLine()) != null) {

                // yaha pe delimiter dekh lena jo tune file mai store krte time rakha hoga
                String[] tokens = sCurrentLine.split(",");
                // maine assume kiya hai ki file mai esa stored hai : 2018/05/18-15:34:55, still
                String time = tokens[0];
                String activity = tokens[2];
                System.out.print("----------------------------------------------");
                System.out.print(tokens[0]);
                System.out.print("------------------------------------------------");
                System.out.print(tokens[2]);


                // apne drawable mai cell_shape dekh lena ek baar hai ki nai
                Drawable cell = getDrawable(R.drawable.cell_shape);
                TableRow tableRow = new TableRow(getApplicationContext());
                tableRow.setGravity(Gravity.CENTER_HORIZONTAL);

                TextView timestampTV = new TextView(getApplicationContext());
                timestampTV.setText(time);
                timestampTV.setGravity(Gravity.CENTER_HORIZONTAL);
                timestampTV.setBackground(cell);

                TextView activityTV = new TextView(getApplicationContext());
                activityTV.setText(activity);
                activityTV.setGravity(Gravity.CENTER_HORIZONTAL);
                activityTV.setBackground(cell);

                tableRow.addView(timestampTV);
                tableRow.addView(activityTV);
                tableRow.setPadding(2, 2, 2, 2);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}