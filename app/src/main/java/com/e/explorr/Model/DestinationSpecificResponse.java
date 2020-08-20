package com.e.explorr.Model;



import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Keep
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


    @Keep
    public class Paging {
        @SerializedName("results")
        private String results;


         public String getResults() {
             return results;
         }
     }
}
