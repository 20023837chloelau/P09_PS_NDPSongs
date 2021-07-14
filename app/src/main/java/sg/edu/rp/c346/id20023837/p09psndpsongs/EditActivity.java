package sg.edu.rp.c346.id20023837.p09psndpsongs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {

    RadioButton one, two, three, four, five;
    RadioGroup rg;
    Button btnCancel, btnDelete, btnUpdate;
    EditText etID, etTitle, etSingers, etYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etSingers = findViewById(R.id.etSinger);
        etYear = findViewById(R.id.etYear);
        etID = findViewById(R.id.value);
        etYear = findViewById(R.id.etYear);
        btnCancel = findViewById(R.id.btnCancel);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        rg = findViewById(R.id.radioGrp);
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);

        Intent i = getIntent();
        final Song currentSong = (Song) i.getSerializableExtra("song");

        etID.setText(currentSong.get_id() + " ");
        etTitle.setText(currentSong.getTitle() + " ");
        etSingers.setText(currentSong.getSingers() + " ");
        etYear.setText(currentSong.getYear() + " ");
        switch (currentSong.getStar()) {
            case 5:
                five.setChecked(true);
                break;
            case 4:
                four.setChecked(true);
                break;
            case 3:
                three.setChecked(true);
                break;
            case 2:
                two.setChecked(true);
                break;
            case 1:
                one.setChecked(true);
                break;
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(EditActivity.this);
                currentSong.setTitle(etTitle.getText().toString().trim());
                currentSong.setSingers(etSingers.getText().toString().trim());

                int year = 0;
                try {
                    year = Integer.valueOf(etYear.getText().toString().trim());
                } catch (Exception e) {
                    Toast.makeText(EditActivity.this, "Invalid year", Toast.LENGTH_SHORT).show();
                    return;
                }
                currentSong.setYear(year);

                int selectedRB = rg.getCheckedRadioButtonId();
                RadioButton rb = findViewById(selectedRB);

                currentSong.setStars(Integer.parseInt(rb.getText().toString()));

                int result = dbh.updateSong(currentSong);
                if (result > 0) {
                    Toast.makeText(EditActivity.this, "Update successful", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent();
                    setResult(RESULT_OK);
                    finish();

                } else {
                    Toast.makeText(EditActivity.this, "Failed to update", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(EditActivity.this);
                int result = dbh.deleteSong(currentSong.get_id());
                if (result > 0) {
                    Toast.makeText(EditActivity.this, "Song deleted", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent();
                    setResult(RESULT_OK);
                    finish();

                } else {
                    Toast.makeText(EditActivity.this, "Failed to delete", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
