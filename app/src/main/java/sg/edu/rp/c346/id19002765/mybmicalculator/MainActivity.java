package sg.edu.rp.c346.id19002765.mybmicalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etWeight, etHeight;
    Button btnCalc, btnReset;
    TextView tvDate, tvBMI, tvDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.editTextWeight);
        etHeight = findViewById(R.id.editTextHeight);
        btnCalc = findViewById(R.id.buttonCalc);
        btnReset = findViewById(R.id.buttonReset);
        tvDate = findViewById(R.id.textViewDate);
        tvBMI = findViewById(R.id.textViewBMI);
        tvDisplay = findViewById(R.id.textViewOutcome);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etWeight.setText(" ");
                etHeight.setText("");
            }
        });

        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float weight = Float.parseFloat(etWeight.getText().toString());
                float height = Float.parseFloat(etHeight.getText().toString());
                Float BMI = weight/(height * height);
                Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
                String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);

                if(BMI < 18.5){
                    tvDisplay.setText("You are underweight");
                }
                else if(BMI >= 18.5 && BMI <=24.9){
                    tvDisplay.setText("Your BMI is average");
                }
                else if(BMI>= 25 && BMI<= 29.9){
                    tvDisplay.setText("You are overweight");
                }
                else{
                    tvDisplay.setText("You are obese");
                }

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor prefEdit = prefs.edit();
                prefEdit.putString("Last Calculated Date: ", datetime);
                prefEdit.putFloat("Last Calculate BMI: ", BMI);
                prefEdit.commit();
                tvBMI.setText(BMI + "");
                tvDate.setText(datetime);
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        String datetime = tvDate.getText().toString();
        Float BMI = Float.parseFloat(tvBMI.getText().toString());
        String lastOutcome = tvDisplay.getText().toString();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor prefEdit = prefs.edit();
        prefEdit.putString("Last Calculated Date: ", datetime);
        prefEdit.putFloat("Last Calculate BMI: ", BMI);
        prefEdit.putString("Last Outcome", lastOutcome);
        prefEdit.commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String date = prefs.getString("Date", "");
        float BMI = prefs.getFloat("Last BMI", 0);
        String outcome = prefs.getString("Last outcome", "");
        tvBMI.setText("Last calculated BMI: " + BMI);
        tvDate.setText("Last Calculated Date: " + date);
        tvDisplay.setText("Last outcome: " + outcome);
    }
}
