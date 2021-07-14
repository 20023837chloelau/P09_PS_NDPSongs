package sg.edu.rp.c346.id20023837.p09psndpsongs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    TextView tvTitle, tvSingers, tvYear, tvStars;
    EditText etTitle, etSingers, etYear;
    RadioButton one, two, three, four, five;
    RadioGroup radioGroup;
    Button btnInsert, btnShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTitle = findViewById(R.id.tvTitle);
        tvSingers = findViewById(R.id.tvSinger);
        tvYear = findViewById(R.id.tvYear);
        tvStars = findViewById(R.id.tvStar);
        etTitle = findViewById(R.id.etName);
        etSingers = findViewById(R.id.etSinger);
        etYear = findViewById(R.id.etYear);
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        radioGroup = findViewById(R.id.radioGrp);
        btnInsert = findViewById(R.id.btnInsert);
        btnShow = findViewById(R.id.btnShow);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString().trim();
                String name = etSingers.getText().toString().trim();

                if (title.length() == 0 || name.length() == 0) {
                    Toast.makeText(MainActivity.this, "Missing input", Toast.LENGTH_SHORT).show();
                    return;
                }

                String yearStr = etYear.getText().toString();
                int year = 0;
                try {
                    year = Integer.valueOf(yearStr);
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Invalid year input", Toast.LENGTH_SHORT).show();
                    return;
                }

                DBHelper dbh = new DBHelper(MainActivity.this);

                int stars = getStars();
                dbh.insertSong(title, name, year, stars);
                dbh.close();
                Toast.makeText(MainActivity.this, "Insert successful", Toast.LENGTH_SHORT).show();

                etTitle.setText("");
                etSingers.setText("");
                etYear.setText("");
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, listActivity.class);
                startActivity(i);
            }
        });
    }

    private int getStars() {
        int stars = 1;
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.one:
                stars = 1;
                break;

            case R.id.two:
                stars = 2;
                break;

            case R.id.three:
                stars = 3;
                break;

            case R.id.four:
                stars = 4;
                break;

            case R.id.five:
                stars = 5;
                break;
        }
        return stars;
    }
}