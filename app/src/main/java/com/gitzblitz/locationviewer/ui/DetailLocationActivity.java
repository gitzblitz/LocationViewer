package com.gitzblitz.locationviewer.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.gitzblitz.locationviewer.R;
import com.gitzblitz.locationviewer.databinding.ActivityDetailLocationBinding;
import com.gitzblitz.locationviewer.viewmodel.DetailLocationViewModel;

public class DetailLocationActivity extends AppCompatActivity {

    private static final String TAG = DetailLocationActivity.class.getName();

    int locationID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_detail_location);

        Intent intent = getIntent();

        if (intent.hasExtra("LocationID")){
            try {
                locationID = intent.getExtras().getInt("LocationID");
                Log.d(TAG, "Extra in bundle " + locationID);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            Log.d(TAG, "No extra in bundle");
        }

        DetailLocationViewModel detailLocationViewModel = ViewModelProviders.of(this)
                .get(DetailLocationViewModel.class);

        ActivityDetailLocationBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_location);
        binding.setLifecycleOwner(this);
        binding.setViewmodel(detailLocationViewModel);

        if (locationID != -1) {
            detailLocationViewModel.start(locationID);
        } else {
            Log.d(TAG, "No location found");
//            throw new RuntimeException("No location found");
            finish();
        }

        setupActionBar();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            return;
        } else  {
            actionBar.setDefaultDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }


            actionBar.setTitle(R.string.location_details);

    }
}
