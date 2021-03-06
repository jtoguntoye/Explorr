package com.e.explorr.Adapters;

import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.explorr.Model.Destinations;
import com.e.explorr.ui.PlacesDetailsActivity;
import com.e.explorr.R;

import java.util.List;

import static com.e.explorr.ui.PlacesDetailsActivity.PARCELED_DESTINATION;

public class GeneralDestinationsVerticalAdapter extends
        RecyclerView.Adapter<GeneralDestinationsVerticalAdapter.VerticalViewHolder> {

    private Context context;
    private List<List<Destinations>> destinationGroupList;

    public GeneralDestinationsVerticalAdapter
            (Context context, List<List<Destinations>> GroupedList) {
        this.context = context;
        this.destinationGroupList = GroupedList;
    }

    public void setAdapterGroupedList(List<List<Destinations>> adapterGroupedList){
        destinationGroupList = adapterGroupedList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VerticalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

    View ItemView =LayoutInflater.from(parent.getContext())
            .inflate(R.layout.vertical_list_item_general_destinations,parent,false);
        return new VerticalViewHolder(ItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VerticalViewHolder holder, int position) {
        if(destinationGroupList.size()!=0) {
            holder.bind(destinationGroupList.get(position));
        }
        else{}
    }

    @Override
    public int getItemCount() {
        return destinationGroupList==null?0:destinationGroupList.size();
    }



            public  class VerticalViewHolder extends RecyclerView.ViewHolder implements
            HorizontalRecyclerAdapter.DestinationClickHandler{

        private TextView destinationCategory;
        private RecyclerView horizontalRecyclerView;
        private List<Destinations> destinationsSpecificList;


        public VerticalViewHolder(@NonNull View itemView) {
            super(itemView);
            destinationCategory =itemView.findViewById(R.id.destinations_category);
            horizontalRecyclerView = itemView.findViewById(R.id.horizontal_recycler_view_item);
        }


        public void bind(List<Destinations> destinationSpecificList) {
            this.destinationsSpecificList = destinationSpecificList;
            if (destinationSpecificList.size() != 0) {
                if (destinationSpecificList.get(0).getDestinationType() != null)
                    destinationCategory.setText(destinationSpecificList.get(0)
                            .getDestinationType());
                else destinationCategory.setText(R.string.default_category);


                //Create an instance of the adapter for the horizontal recyclerview that
                //displays list of destinations for each category
                HorizontalRecyclerAdapter horizontalRecyclerAdapter =
                        new HorizontalRecyclerAdapter(destinationSpecificList, this);
                horizontalRecyclerView.setHasFixedSize(true);
                horizontalRecyclerView.setLayoutManager(new
                        LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));

                horizontalRecyclerView.setAdapter(horizontalRecyclerAdapter);
            }
        }
                @Override
                public void onDestinationClickListener(int position) {
                 Intent destinationDetailIntent = new Intent(context, PlacesDetailsActivity.class);
                 destinationDetailIntent.putExtra(PARCELED_DESTINATION, destinationsSpecificList.get(position));
                 context.startActivity(destinationDetailIntent);





                }
    }
}
