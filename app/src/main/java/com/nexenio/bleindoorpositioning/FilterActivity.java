package com.nexenio.bleindoorpositioning;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.nexenio.bleindoorpositioningdemo.R;
import com.nexenio.bleindoorpositioningdemo.database.FilterModel;
import com.nexenio.bleindoorpositioningdemo.database.FilterModelDAO;
import com.nexenio.bleindoorpositioningdemo.database.SqliteDbHelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class FilterActivity extends AppCompatActivity {

    SqliteDbHelper db;
    RecyclerView recyclerView;
    FilterListAdapter adapter;
    FilterModelDAO filterDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = new SqliteDbHelper(this);
        filterDao = new FilterModelDAO(db);

        recyclerView = findViewById(R.id.recyclerView);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        adapter = new FilterListAdapter(filterDao.getFilters());
        recyclerView.setAdapter(adapter);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(onAddClickListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateList();
    }

    void updateList() {
        adapter.items = filterDao.getFilters();
        adapter.notifyDataSetChanged();
    }

    View.OnClickListener onAddClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(v.getContext(), AddFilterActivity.class));

//            Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show();
//            db.addFilter("123456");
        }
    };

    private class FilterListAdapter extends RecyclerView.Adapter<FilterListAdapter.FilterViewHolder> {

        List<FilterModel> items;

        public FilterListAdapter(List<FilterModel> items) {
            this.items = items;
        }

        @NonNull
        @Override
        public FilterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            TextView v = (TextView) LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.item_filter, parent, false);
            return new FilterViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_filter, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull FilterViewHolder holder, int position) {
            final FilterModel filter = items.get(position);
            System.out.println(filter.name);
            holder.edUuid.setText("UUID: " + (filter.uuid == null ? "" : filter.uuid));
            holder.edMajor.setText("major: " + (filter.major == null ? "" : filter.major));
            holder.edMinor.setText("minor: " + (filter.minor == null ? "" : filter.minor));
            holder.edName.setText("identifier: " + (filter.name == null ? "" : filter.name));

            holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    filterDao.deleteFilter(filter.id);
                    updateList();
                }
            });
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        private class FilterViewHolder extends RecyclerView.ViewHolder {
            TextView edUuid, edMajor, edMinor, edName;
            Button deleteButton;

            public FilterViewHolder(@NonNull View itemView) {
                super(itemView);
                edUuid = itemView.findViewById(R.id.tvUuid);
                edMajor = itemView.findViewById(R.id.tvMajor);
                edMinor = itemView.findViewById(R.id.tvMinor);
                edName = itemView.findViewById(R.id.tvName);
                deleteButton = itemView.findViewById(R.id.deleteButton);
            }
        }
    }
}