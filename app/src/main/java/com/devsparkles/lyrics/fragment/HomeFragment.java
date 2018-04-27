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
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements OnSearchListener {
    private List<Song> songs = new ArrayList<Song>();
    private RecyclerView recyclerView;
    private TextView emptyView;
    private SongsAdapter mAdapter;
    private int currentPosition;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        emptyView = (TextView) view.findViewById(R.id.empty_view);
        mAdapter = new SongsAdapter(songs);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        registerForContextMenu(recyclerView);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent songIntent = new Intent(getContext(), SongScreenActivity.class);
                songIntent.putExtra("song", songs.get(position));
                songIntent.putExtra("position", position);
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


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        String id = songs.get(currentPosition).getId();
        Set<String> fav = PrefUtil.getFavorites(getContext());
        if (fav!=null && fav.contains(id)) {
            menu.add(Menu.NONE, v.getId(), 0, "Remove Favorites");
        } else {
            menu.add(Menu.NONE, v.getId(), 0, "Add to Favorites");
        }
        menu.add(Menu.NONE, v.getId(), 0, "Slide Show");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        String id = songs.get(currentPosition).getId();
        if (item.getTitle().equals("Add to Favorites")) {
            FavUtil.addFavorite(getContext(), id);
        } else if (item.getTitle().equals("Remove Favorites")) {
            FavUtil.removeFavorite(getContext(), id);
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

    public void parseJSONObject() {
        Songs songs = SongUtil.getSongs(getContext());
        Collections.sort(songs.getSongs(),new SongComparator());
        this.songs.clear();
        this.songs.addAll(songs.getSongs());
        mAdapter = new SongsAdapter(this.songs);
        mAdapter.notifyDataSetChanged();
        recyclerView.setVisibility(View.VISIBLE);
        checkEmpty();
    }

    private void checkEmpty() {
        if (songs.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
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
        this.songs.clear();
        ArrayList<Song> songList = SongUtil.searchSong(getContext(),query);
        Collections.sort(songList,new SongComparator());
        this.songs.addAll(songList);
        mAdapter = new SongsAdapter(this.songs);
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
