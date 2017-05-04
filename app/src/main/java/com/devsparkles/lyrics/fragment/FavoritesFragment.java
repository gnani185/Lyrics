package com.devsparkles.lyrics.fragment;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devsparkles.lyrics.FullscreenLyricActivity;
import com.devsparkles.lyrics.R;
import com.devsparkles.lyrics.SongScreenActivity;
import com.devsparkles.lyrics.adapter.SongsAdapter;
import com.devsparkles.lyrics.beans.Song;
import com.devsparkles.lyrics.beans.Songs;
import com.devsparkles.lyrics.comparator.SongComparator;
import com.devsparkles.lyrics.listener.ClickListener;
import com.devsparkles.lyrics.listener.OnSearchListener;
import com.devsparkles.lyrics.listener.RecyclerTouchListener;
import com.devsparkles.lyrics.utils.FavUtil;
import com.devsparkles.lyrics.utils.PrefUtil;
import com.devsparkles.lyrics.utils.SongUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FavoritesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FavoritesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoritesFragment extends Fragment implements OnSearchListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private List<Song> favorites = new ArrayList<Song>();
    private Set<String> favList = null;
    private RecyclerView recyclerView;
    private TextView emptyView;
    private SongsAdapter mAdapter;
    private int currentPosition;
    private OnFragmentInteractionListener mListener;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FavoritesFragment newInstance(String param1, String param2) {
        FavoritesFragment fragment = new FavoritesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        emptyView = (TextView) view.findViewById(R.id.empty_view);
        mAdapter = new SongsAdapter(favorites);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        registerForContextMenu(recyclerView);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent songIntent = new Intent(getContext(), SongScreenActivity.class);
                songIntent.putExtra("song", favorites.get(position));
                startActivity(songIntent);
            }

            @Override
            public void onLongClick(View view, int position) {
                currentPosition = position;
                recyclerView.showContextMenuForChild(view);
            }
        }));
        parseJSONObject();
        return view;
    }

    public void parseJSONObject() {
        Songs songs = SongUtil.getSongs(getContext());
        Collections.sort(songs.getSongs(),new SongComparator());
        this.favorites.clear();
        favList = PrefUtil.getFavorites(getContext());
        if (favList != null) {
            for (Song song : songs.getSongs()) {
                if (favList.contains(song.getId())) {
                    favorites.add(song);
                }
            }
        }
        mAdapter = new SongsAdapter(this.favorites);
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        recyclerView.setVisibility(View.VISIBLE);
        checkEmpty();
    }

    private void checkEmpty() {
        if (favorites.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        Log.i("POSITION", "" + currentPosition);
        String id = favorites.get(currentPosition).getId();
        menu.add(Menu.NONE, v.getId(), 0, "Remove Favorites");
        menu.add(Menu.NONE, v.getId(), 0, "Slide Show");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        String id = favorites.get(currentPosition).getId();
        if (item.getTitle().equals("Remove Favorites")) {
            FavUtil.removeFavorite(getContext(), id);
            parseJSONObject();
        } else {
            Intent i = new Intent(getContext(), FullscreenLyricActivity.class);
            i.putExtra("position", id);
            startActivity(i);
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        SearchManager searchManager =
                (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchableInfo searchableInfo =
                searchManager.getSearchableInfo(getActivity().getComponentName());
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setSearchableInfo(searchableInfo);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onSearch(String query) {
        this.favorites.clear();
        ArrayList<Song> songList = SongUtil.searchFavoriteSong(getContext(),query);
        Collections.sort(songList,new SongComparator());
        this.favorites.addAll(songList);
        mAdapter = new SongsAdapter(this.favorites);
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        recyclerView.setVisibility(View.VISIBLE);
        checkEmpty();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
