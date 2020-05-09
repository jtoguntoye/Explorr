package com.example.explorr.Model;

import com.google.gson.annotations.SerializedName;

public class DestinationPhotos {

    @SerializedName("images")
    private ImagesClass images;

    public ImagesClass getImages() {
        return images;
    }


    public class ImagesClass {

        @SerializedName("small")
        private SmallImage small;

        @SerializedName("original")
        private OriginalImage originalImage;


        public SmallImage getSmall() {
            return small;
        }

        public OriginalImage getOriginalImage() {
            return originalImage;
        }


        public void setSmall(SmallImage small) {
            this.small = small;
        }

        public void setOriginalImage(OriginalImage originalImage) {
            this.originalImage = originalImage;
        }
    }

    public class SmallImage{
        @SerializedName("width")
        private String Width;

        @SerializedName("height")
        private String height;

        @SerializedName("url")
        private String Url;



        public void setWidth(String width) {
            Width = width;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public void setUrl(String url) {
            Url = url;
        }


        public String getWidth() {
            return Width;
        }

        public String getHeight() {
            return height;
        }

        public String getUrl() {
            return Url;
        }



    }

    public class OriginalImage {

        @SerializedName("width")
        private String Width;


        @SerializedName("height")
        private String height;

        @SerializedName("url")
        private String Url;


        public void setWidth(String width) {
            Width = width;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public void setUrl(String url) {
            Url = url;
        }


        public String getWidth() {
            return Width;
        }

        public String getHeight() {
            return height;
        }

        public String getUrl() {
            return Url;
        }

    }


}
