package com.fcgtask.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.fcgtask.R;
import com.fcgtask.data.model.ProfileDetails;
import com.fcgtask.interfaces.IProfileActivity;
import com.fcgtask.ui.fragment.ProfilesFragment;

public class ProfilesActivity extends AppCompatActivity implements IProfileActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
        setTitle(R.string.profiles);
        if (savedInstanceState == null)
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new ProfilesFragment(), ProfilesFragment.TAG)
                    .commit();
    }

    @Override
    public void showProfileDetails(ProfileDetails details) {
        Intent i = new Intent(this, DetailsActivity.class);
        i.putExtra(DetailsActivity.EXTRAS_DETAILS, details);
        startActivityForResult(i, DetailsActivity.REQ_CODE);
    }
}
