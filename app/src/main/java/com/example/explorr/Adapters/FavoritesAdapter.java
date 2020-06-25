package com.example.explorr.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.explorr.Model.DestinationPhotos;
import com.example.explorr.Model.Destinations;
import com.example.explorr.R;
import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {


    private List<Destinations> favoritesList;
    private DestinationClickHandler mDestinationClickHandler;

    public FavoritesAdapter(List<Destinations> favoritesList,
                            DestinationClickHandler destinationClickHandler) {

        this.favoritesList = favoritesList;
        mDestinationClickHandler = destinationClickHandler;
    }

    public void setFavoritesList(List<Destinations> favoritesList) {
        this.favoritesList = favoritesList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_favorites, parent, false);

        return new FavoritesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesViewHolder holder, int position) {
        holder.bind(favoritesList.get(position));
    }

    @Override
    public int getItemCount() {
        return favoritesList == null ? 0 : favoritesList.size();
    }

    public class FavoritesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView destinationName;
        private ImageView destinationImage;
        private String imageUrl;


        public FavoritesViewHolder(@NonNull View itemView) {
            super(itemView);

            destinationName = itemView.findViewById(R.id.text_location_name);
            destinationImage = itemView.findViewById(R.id.favorite_image);
        }

        public void bind(Destinations destinations){
                destinationName.setText(destinations.getDestinationName());


            if(destinations.getPhotos()!=null){
                DestinationPhotos photos = destinations.getPhotos();
                if(photos.getImages()!=null) {
                    imageUrl = photos.getImages().getSmall().getUrl();

                    Glide.with(itemView)
                            .load(imageUrl)
                            .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
                            .into(destinationImage);

                }
            }
            //handle situations where the image is not available
            else{
                    destinationImage.setImageResource(R.mipmap.default_thumbnail);
            }

        }

        @Override
        public void onClick(View v) {
            mDestinationClickHandler.onDestinationClickListener(getAdapterPosition());
        }
    }

    //create an interface to handle item click on the recycler view items
    public  interface  DestinationClickHandler{
        void onDestinationClickListener(int position);

    }
}
