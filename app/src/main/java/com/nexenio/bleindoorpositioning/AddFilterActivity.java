package com.nexenio.bleindoorpositioning;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nexenio.bleindoorpositioningdemo.R;
import com.nexenio.bleindoorpositioningdemo.database.FilterModel;
import com.nexenio.bleindoorpositioningdemo.database.FilterModelDAO;
import com.nexenio.bleindoorpositioningdemo.database.SqliteDbHelper;

import java.util.UUID;

public class AddFilterActivity extends AppCompatActivity {

    SqliteDbHelper db;
    EditText edUuid, edMinor, edMajor, edName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_filter);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        edUuid = findViewById(R.id.edUuid);
        edMinor = findViewById(R.id.edMInor);
        edMajor = findViewById(R.id.edMajor);
        edName = findViewById(R.id.edName);

        db = new SqliteDbHelper(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(onAddClickListener);
    }

    View.OnClickListener onAddClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String uuid = edUuid.getText().toString().trim();
            String minor = edMinor.getText().toString().trim();
            String major = edMajor.getText().toString().trim();

            try {
                if (!uuid.isEmpty()) {
                    UUID.fromString(uuid);
                }
            } catch (Exception e) {
                Toast.makeText(v.getContext(), "UUID is Invalid", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                if (!minor.isEmpty()) {
                    Integer.parseInt(minor);
                }
            } catch (Exception e) {
                Toast.makeText(v.getContext(), "Minor is Invalid", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                if (!major.isEmpty()) {
                    Integer.parseInt(major);
                }
            } catch (Exception e) {
                Toast.makeText(v.getContext(), "Major is Invalid", Toast.LENGTH_SHORT).show();
                return;
            }

            if (uuid.isEmpty() && minor.isEmpty() && major.isEmpty()) {
                Toast.makeText(v.getContext(), "UUID, Minor and Major can't be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            FilterModel filter = new FilterModel();
            filter.uuid = uuid;
            filter.minor = minor;
            filter.major = major;
            filter.name = edName.getText().toString();

            new FilterModelDAO(db).addNewFilter(filter);
            finish();
        }
    };
}