package com.afflyas.isearchalbums.ui.search;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.afflyas.isearchalbums.R;
import com.afflyas.isearchalbums.api.ITunesApiService;
import com.afflyas.isearchalbums.core.App;
import com.afflyas.isearchalbums.core.MainActivity;
import com.afflyas.isearchalbums.databinding.FragmentSearchBinding;
import com.afflyas.isearchalbums.di.ViewModelFactory;
import com.afflyas.isearchalbums.ui.common.ItemSearchClickCallback;
import com.afflyas.isearchalbums.ui.common.RetryCallback;
import com.afflyas.isearchalbums.vo.Album;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import dagger.android.support.AndroidSupportInjection;

/**
 * A fragment for searching for albums by title
 */
public class SearchFragment extends Fragment implements RetryCallback, ItemSearchClickCallback {

    private FragmentSearchBinding fragmentBinding;

    @Inject
    MainActivity mainActivity;

    @Inject
    ITunesApiService iTunesApiService;

    /**
     * Custom factory to inject into ViewModel's
     */
    @Inject
    ViewModelFactory viewModelFactory;

    private SearchViewModel mViewModel;

    private SearchAdapter searchAdapter;

    /**
     * Enable injections
     */
    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    /**
     * Create view with data binding and set {@link RetryCallback} to it
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        fragmentBinding.setCallback(this);
        return fragmentBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel.class);
        subscribeUI();
    }

    /**
     * Expand appBar when fragment resumes
     */
    @Override
    public void onResume() {
        super.onResume();
        fragmentBinding.appBar.setExpanded(true);
    }

    /**
     * set adapter to recyclerView
     *
     * subscribe searchView to text submit. Call new search request after it is happen
     *
     * subscribe observer for {@link SearchViewModel#searchResult}
     * to change data in the view's binding and searchAdapter
     *
     */
    private void subscribeUI(){

        searchAdapter = new SearchAdapter(this);
        fragmentBinding.recyclerView.setAdapter(searchAdapter);

        fragmentBinding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mViewModel.search(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        mViewModel.searchResult.observe(this, listResponse -> {
            Log.d(App.DEV_TAG, getClass().getSimpleName() + ": searchResult onChanged");

            fragmentBinding.setSearchResponse(listResponse);
            searchAdapter.setAlbumsList(listResponse.getData());
            fragmentBinding.recyclerView.scrollToPosition(0);
        });
    }

    /**
     * {@link RetryCallback} call to repeat search request
     *
     * Retry button displayed only when api request was failed
     */
    @Override
    public void retry() {
        mViewModel.refresh();
    }

    /**
     * Navigate to {@link com.afflyas.isearchalbums.ui.album.AlbumFragment}
     * after clicking one of the RecyclerView's items
     *
     * {@link Album} object that represents clicked item is passed as an argument
     */
    @Override
    public void onItemClick(Album album) {
        Log.d(App.DEV_TAG, getClass().getSimpleName() + ": search item click");
        SearchFragmentDirections.ActionSearchFragmentToAlbumFragment action = SearchFragmentDirections.actionSearchFragmentToAlbumFragment(album);
        NavHostFragment.findNavController(this).navigate(action);
    }

}
