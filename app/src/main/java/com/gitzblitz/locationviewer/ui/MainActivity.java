package com.gitzblitz.locationviewer.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gitzblitz.locationviewer.R;
import com.gitzblitz.locationviewer.viewmodel.LocationListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_LOCATION_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        LocationListViewModel locationListViewModel = ViewModelProviders.of(this).get(LocationListViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.locations_recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //get view model
        LocationListAdapter adapter = new LocationListAdapter(this);
        locationListViewModel.getAllLocations().observe(this, adapter::submitList);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddLocationActivity.class);
            startActivityForResult(intent, NEW_LOCATION_ACTIVITY_REQUEST_CODE);
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }
}
