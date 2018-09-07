package com.fcgtask.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.fcgtask.R;
import com.fcgtask.ui.fragment.DetailsFragment;

public class DetailsActivity extends AppCompatActivity {
    public static final int REQ_CODE = 100;
    public static final String EXTRAS_DETAILS = "EXTRAS_DETAILS";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
        setUpActionBar();
        if (savedInstanceState == null)
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new DetailsFragment(), DetailsFragment.TAG)
                    .commit();
    }

    private void setUpActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
