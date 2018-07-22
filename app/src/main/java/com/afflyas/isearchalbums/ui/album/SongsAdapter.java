package com.afflyas.isearchalbums.ui.album;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.afflyas.isearchalbums.R;
import com.afflyas.isearchalbums.databinding.ItemSongBinding;
import com.afflyas.isearchalbums.vo.Song;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 *
 * Adapter for RecyclerView in {@link AlbumFragment}
 *
 */
public class SongsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @Nullable
    private List<Song> songs;

    /**
     *
     * Set items data set or destroy all items if new list is empty/null
     *
     * @param newSongs - new list of {@link Song} to display in the view
     */
    public void setSongsList(@Nullable List<Song> newSongs){
        if (newSongs == null || newSongs.isEmpty()) {
            songs = null;
            notifyItemRangeInserted(0, 0);
        }else{
            songs = newSongs;
            notifyDataSetChanged();
        }
    }

    /**
     * set corresponding {@link Song} object to item
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(songs != null){
            final Song song = songs.get(position);
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.binding.setSong(song);
            itemViewHolder.binding.executePendingBindings();
        }
    }

    /**
     *
     * Set item count bases on the size of a list
     *
     * set 0 if it is null
     *
     * @return size of a songs list or 0
     */
    @Override
    public int getItemCount() {
        return songs == null ? 0 : songs.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSongBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),R.layout.item_song,parent,false);
        return new ItemViewHolder(binding);
    }

    /**
     * ViewHolder that contains binding with `item_song.xml` layout
     */
    static class ItemViewHolder extends RecyclerView.ViewHolder {

        final ItemSongBinding binding;

        ItemViewHolder(ItemSongBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
