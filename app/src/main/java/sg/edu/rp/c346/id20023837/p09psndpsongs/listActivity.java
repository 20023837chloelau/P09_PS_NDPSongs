package sg.edu.rp.c346.id20023837.p09psndpsongs;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class listActivity extends AppCompatActivity {

    Button btnFilter;
    Spinner snYear;
    ListView lv;

    int code = 9;

    ArrayList<Song> al;
    ArrayAdapter aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        setTitle(getTitle().toString() + " ~ " + "Show Song");

        btnFilter = findViewById(R.id.filter);
        snYear = findViewById(R.id.spinner);
        lv = findViewById(R.id.lv);

        DBHelper dbh = new DBHelper(listActivity.this);
        al = dbh.getAllSongs();
        dbh.close();

        aa = new ArrayAdapter(this, android.R.layout.simple_list_item_1, al);
        lv.setAdapter(aa);

        lv.setOnItemClickListener((parent, view, position, id) -> {
            Intent i = new Intent(listActivity.this, EditActivity.class);
            i.putExtra("song", al.get(position));
            startActivityForResult(i, code);
        });

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(listActivity.this);

                al.clear();
                al.addAll(dbh.getAllSongsByStars(5));
                aa.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == this.code && resultCode == RESULT_OK){
            DBHelper dbh = new DBHelper(this);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
