package com.example.explorr.ui.favorites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.explorr.Adapters.FavoritesAdapter;
import com.example.explorr.Model.Destinations;
import com.example.explorr.R;
import com.example.explorr.ui.MainActivity;
import com.example.explorr.ViewModel.MainActivityViewModel;
import com.example.explorr.ViewModel.MainActivityViewModelFactory;
import com.example.explorr.ui.PlacesDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.example.explorr.ui.PlacesDetailsActivity.PARCELED_DESTINATION;

public class FavoritesFragment extends Fragment implements FavoritesAdapter.DestinationClickHandler {

    @Inject
    MainActivityViewModelFactory viewModelFactory;
    private MainActivityViewModel mainActivityViewModel;
    private FavoritesAdapter favoritesAdapter;
    private List<Destinations> favoriteDestinationsList;
    private RecyclerView verticalRecyclerView;
    private TextView emptyFavoriteTextView;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        //obtain the dependency graph from the MainActivity and instantiate the @inject fields
        ((MainActivity) requireActivity()).mainActivityComponent.inject(this);

    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mainActivityViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel.class);



        return inflater.inflate(R.layout.fragment_favorites, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        favoritesAdapter = new FavoritesAdapter(new ArrayList<>(), this);
        emptyFavoriteTextView = view.findViewById(R.id.empty_favorite_text);
        verticalRecyclerView =
                view.findViewById(R.id.favorite_recycler);
        verticalRecyclerView.setHasFixedSize(true);
        verticalRecyclerView.setLayoutManager
                (new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        verticalRecyclerView.setAdapter(favoritesAdapter);

            getAllfavorites();
    }

    private void getAllfavorites() {

        mainActivityViewModel.getFavorites().observe(getViewLifecycleOwner(), destinationsList  -> {

            if(destinationsList.size()==0){
                verticalRecyclerView.setVisibility(View.GONE);
                emptyFavoriteTextView.setVisibility(View.VISIBLE);
            }
        favoritesAdapter.setFavoritesList(destinationsList);
        favoriteDestinationsList = destinationsList;

        });

    }

    @Override
    public void onDestinationClickListener(int position) {

        Intent destinationDetailIntent = new Intent(requireContext(), PlacesDetailsActivity.class);
        destinationDetailIntent.putExtra(PARCELED_DESTINATION, favoriteDestinationsList.get(position));
        requireContext().startActivity(destinationDetailIntent);

    }
}
