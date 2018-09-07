package com.fcgtask.ui.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fcgtask.R;
import com.fcgtask.data.model.Profile;
import com.fcgtask.databinding.ItemGridBinding;
import com.fcgtask.interfaces.IProfiles;

import java.util.List;

public class ProfilesAdapter extends RecyclerView.Adapter<ProfilesAdapter.ViewHolder> implements View.OnClickListener {
    private List<Profile> mProfiles;
    private IProfiles mCallback;

    public ProfilesAdapter(IProfiles callback) {
        this.mCallback = callback;
    }

    public void setData(List<Profile> profiles) {
        this.mProfiles = profiles;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemGridBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_grid,
                        parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Profile profile = mProfiles.get(position);
        holder.mBinding.setProfile(profile);
        holder.mBinding.itemGrid.setTag(position);
        holder.mBinding.itemGrid.setOnClickListener(this);
        holder.mBinding.itemFav.setVisibility(profile.isFav ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return mProfiles != null ? mProfiles.size() : 0;
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        mCallback.onProfileClick(position, mProfiles.get(position));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemGridBinding mBinding;

        ViewHolder(ItemGridBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }
    }
}
