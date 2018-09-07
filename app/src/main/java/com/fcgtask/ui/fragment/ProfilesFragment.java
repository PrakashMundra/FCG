package com.fcgtask.ui.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fcgtask.R;
import com.fcgtask.data.model.Favourites;
import com.fcgtask.data.model.Profile;
import com.fcgtask.databinding.FragmentProfilesBinding;
import com.fcgtask.interfaces.IProfileActivity;
import com.fcgtask.interfaces.IProfiles;
import com.fcgtask.ui.adapter.ProfilesAdapter;
import com.fcgtask.viewmodel.ProfilesModel;
import com.fcgtask.widget.GridItemDecoration;

import java.util.List;

public class ProfilesFragment extends Fragment implements IProfiles {
    public static final String TAG = ProfilesFragment.class.getSimpleName();
    private FragmentProfilesBinding mBinding;
    private ProfilesAdapter mAdapter;
    private ProfilesModel mModel;
    private IProfileActivity iProfileActivity;
    private List<Profile> mProfiles;
    private int mSelectedPosition = -1;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iProfileActivity = (IProfileActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        iProfileActivity = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profiles, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpRecyclerView();
        if (getActivity() != null) {
            ProfilesModel.Factory factory = new ProfilesModel.Factory(getActivity().getApplication());
            mModel = ViewModelProviders.of(this, factory).get(ProfilesModel.class);
            mBinding.setViewModel(mModel);
            loadProfiles();
        }
    }

    private void setUpRecyclerView() {
        if (getContext() != null) {
            int colSpan = (getContext().getResources().getConfiguration().orientation
                    == Configuration.ORIENTATION_PORTRAIT) ? 2 : 3;
            mBinding.recyclerView.setHasFixedSize(true);
            mBinding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), colSpan));
            int space = getResources().getDimensionPixelSize(R.dimen.grid_item_padding);
            mBinding.recyclerView.addItemDecoration(new GridItemDecoration(colSpan, space));
            mAdapter = new ProfilesAdapter(this);
            mBinding.recyclerView.setAdapter(mAdapter);
        }
    }

    private void loadProfiles() {
        mModel.getProfiles().observe(this, profiles -> {
                    if (profiles != null && profiles.size() > 0) {
                        this.mProfiles = profiles;
                        loadFavouriteProfiles();
                        mModel.isEmpty.set(false);
                    } else {
                        mModel.isEmpty.set(true);
                        mModel.loading.set(false);
                    }
                }
        );
    }

    private void loadFavouriteProfiles() {
        mModel.getFavouriteProfiles().observe(this, favourites -> {
            if (favourites != null && favourites.size() > 0) {
                for (Profile profile : mProfiles) {
                    profile.isFav = false;
                    for (Favourites favourite : favourites) {
                        if (favourite.fav_profile_id == profile.id)
                            profile.isFav = true;
                    }
                }
            } else if (mSelectedPosition != -1)
                mProfiles.get(mSelectedPosition).isFav = false;
            setData();
            mModel.loading.set(false);
        });
    }

    private void setData() {
        mAdapter.setData(mProfiles);
    }

    @Override
    public void onProfileClick(int position, Profile profile) {
        this.mSelectedPosition = position;
        loadProfileDetails(profile);
    }

    public void loadProfileDetails(Profile profile) {
        mModel.loading.set(true);
        mModel.getProfileDetails(profile.id).observe(this, details -> {
            mModel.loading.set(false);
            if (details != null) {
                details.isFav = profile.isFav;
                iProfileActivity.showProfileDetails(details);
            }
        });
    }
}
