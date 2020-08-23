# Explorr
An app for travelers who love to explore the world, their immediate environment and visit interesting places
To compile this repo:
You will use the TravelAdvisor API  to fetch the data for Attractions, Hotels and Restaurants around a given location.
To access the TravelAdvisor API you need to request for an API key at https://developer-tripadvisor.com/content-api/request-api-access/ and fill the API access form.

Once you obtain your key, you append it to your HTTP request as a URL Header. For a tutorial on using the TravelAdvisor API go to https://rapidapi.com/apidojo/api/tripadvisor1/endpoints for more info.
To fetch nearby Attractions, hotels and restaurants you will request data from the https://tripAdvisor1.rapidapi.com endpoint attaching different URL query parameters for each data required.
 E.g  to get  the list of restaurants related to a location by location_id you will use the restaurants/list  endpoint

You can place your API key in your gradle.properties file and make sure you do add the file to .gitignore so you do not post the API key on github or any other publicly available code repository
