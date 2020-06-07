package com.example.explorr.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DestinationSpecificResponse {

    @SerializedName("data")
    private List<Destinations> DestinationsList;

    @SerializedName("paging")
    private Paging pagingInfo;

    public List<Destinations> getDestinationsList() {
        return DestinationsList;
    }

    public Paging getPagingInfo() {
        return pagingInfo;
    }


    public class Paging {
        @SerializedName("results")
        private String results;


         public String getResults() {
             return results;
         }
     }
}
