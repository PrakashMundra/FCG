package com.fcgtask.ui.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fcgtask.R;
import com.fcgtask.data.model.ProfileDetails;
import com.fcgtask.databinding.FragmentDetailsBinding;
import com.fcgtask.ui.activity.DetailsActivity;
import com.fcgtask.viewmodel.DetailsModel;

public class DetailsFragment extends Fragment {
    public static final String TAG = DetailsFragment.class.getSimpleName();
    private FragmentDetailsBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            DetailsModel.Factory factory = new DetailsModel.Factory(getActivity().getApplication());
            DetailsModel model = ViewModelProviders.of(this, factory).get(DetailsModel.class);
            mBinding.setViewModel(model);
            final ProfileDetails details = (ProfileDetails) getActivity().getIntent().
                    getSerializableExtra(DetailsActivity.EXTRAS_DETAILS);
            getActivity().setTitle(details.first_name + " " + details.last_name);
            mBinding.setDetails(details);
            model.isFav.set(details.isFav);
            mBinding.addFav.setOnClickListener(v -> {
                model.isFav.set(true);
                model.favourite(true, details.id);
            });
            mBinding.removeFav.setOnClickListener(v -> {
                model.isFav.set(false);
                model.favourite(false, details.id);
            });
        }
    }
}
