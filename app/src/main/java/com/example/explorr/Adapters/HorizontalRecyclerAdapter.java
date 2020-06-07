package com.example.explorr.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.explorr.Model.DestinationPhotos;
import com.example.explorr.Model.Destinations;
import com.example.explorr.R;

import java.util.List;

public class HorizontalRecyclerAdapter  extends
        RecyclerView.Adapter<HorizontalRecyclerAdapter.HorizontalViewHolder> {

    private List<Destinations> destinationsList;
    private DestinationClickHandler mDestinationClickHandler;


    public HorizontalRecyclerAdapter(List<Destinations> destinationsList,
                                     DestinationClickHandler destinationClickHandler) {

        this.destinationsList = destinationsList;
        this.mDestinationClickHandler = destinationClickHandler;
    }

    @NonNull
    @Override
    public HorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View ItemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.horizontal_list_item_general_destination,parent,false);
        return new HorizontalViewHolder(ItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalViewHolder holder, int position) {
        holder.bind(destinationsList.get(position));
    }

    @Override
    public int getItemCount() {
        return destinationsList==null?0: destinationsList.size();
    }

    public class  HorizontalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView locationThumbnail;
        private TextView locationName;
        private String imageUrl;

        public HorizontalViewHolder(@NonNull View itemView) {
            super(itemView);
            locationName = itemView.findViewById(R.id.text_location_name);
            locationThumbnail = itemView.findViewById(R.id.location_image);

            itemView.setOnClickListener(this);
        }

        public void bind(Destinations destinations){
            locationName.setText(destinations.getDestinationName());
            if(destinations.getPhotos()!=null){
            DestinationPhotos photos = destinations.getPhotos();
            if(photos.getImages()!=null) {
                imageUrl = photos.getImages().getSmall().getUrl();

                Glide.with(itemView)
                        .load(imageUrl)
                        .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
                        .into(locationThumbnail);

            }
        }
            //handle situations where the image is not available
            else{
               locationThumbnail.setImageResource(R.color.colorPrimary);
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

