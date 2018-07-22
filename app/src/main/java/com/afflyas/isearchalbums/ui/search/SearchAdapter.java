package com.afflyas.isearchalbums.ui.search;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.afflyas.isearchalbums.R;
import com.afflyas.isearchalbums.databinding.ItemSearchBinding;
import com.afflyas.isearchalbums.ui.common.ItemSearchClickCallback;
import com.afflyas.isearchalbums.vo.Album;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 *
 * Adapter for RecyclerView in {@link SearchFragment}
 *
 */
public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public SearchAdapter(@Nullable ItemSearchClickCallback onItemClickCallback){
        this.onItemClickCallback = onItemClickCallback;
    }

    @Nullable
    private final ItemSearchClickCallback onItemClickCallback;

    @Nullable
    private List<Album> albums;


    /**
     *
     * Set items data set or destroy all items if new list is empty/null
     *
     * @param newAlbums - new list of {@link Album} to display in the view
     */
    public void setAlbumsList(@Nullable List<Album> newAlbums){
        if (newAlbums == null || newAlbums.isEmpty()) {
            albums = null;
            notifyItemRangeInserted(0, 0);
        }else{
            albums = newAlbums;
            notifyDataSetChanged();
        }
    }

    /**
     * set corresponding {@link Album} object to item
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(albums != null){
            final Album album = albums.get(position);
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.binding.setAlbum(album);
            itemViewHolder.binding.executePendingBindings();
        }
    }

    /**
     *
     * Set item count bases on the size of a list
     *
     * set 0 if it is null
     *
     * @return size of a albums list or 0
     */
    @Override
    public int getItemCount() {
        return albums == null ? 0 : albums.size();
    }

    /**
     * create ViewHolder with data binding
     * and set callback
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSearchBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),R.layout.item_search,parent,false);
        binding.setCallback(onItemClickCallback);
        return new ItemViewHolder(binding);
    }

    /**
     * ViewHolder that contains binding with `item_search.xml` layout
     */
    static class ItemViewHolder extends RecyclerView.ViewHolder {

        final ItemSearchBinding binding;

        ItemViewHolder(ItemSearchBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
