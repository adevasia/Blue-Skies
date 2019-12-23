
package com.example.a6_hw3;

// Import Statements
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

// CityDatabase takes care of creating City Objects and
// Populating objects with information from values and API

//class inspired by zybooks band app from chapter 5 on fragments
public class CityDatabase {

    // creates a single database to add data too so no duplicates
    private static CityDatabase sCityDatabase;
    private List<City> CityList; // creates list of City Objects

    // creates new DB if one doesn't already exsist
    public static CityDatabase get(Context context) {

        if (sCityDatabase == null) {
            sCityDatabase = new CityDatabase(context);
        }
        return sCityDatabase;
    }

    // adds data to the DB from values and API function call
    private CityDatabase(Context context) {
        CityList = new ArrayList<>();
        Resources res = context.getResources();
        // get city names from value.strings
        String[] cities = res.getStringArray(R.array.Cities);

        // for every city, get data about it from API call using LongLat Coordinates
        for (int i = 0; i < cities.length; i++) {
            // call API function
            getInfoFromDarkSky(res.getStringArray(R.array.LongLat)[i],i,cities[i],context);
            // add information to DB List
            CityList.add(new City(i + 1, cities[i], "Waiting for Data to Load",""));
        }
    }

    // returns List of all cities and details along with it (temp/summary)
    public List<City> getCities() {
        return CityList;
    }

    // returns city object based on city ID, if ID doesn't exist then returns null
    public City getCity(int cityId) {
        // go through each city in the List
        for (City city : CityList) {
            // if ID exisits then return the city object
            if (city.getId() == cityId) {
                return city;
            }
        }
        return null;
    }

    // makes Jason Object Request from Dark Sky using API call
    //
    private void getInfoFromDarkSky(String LongLat, final int index, final String cityName, Context mContext){

        // initiate the queue
        RequestQueue queue = Volley.newRequestQueue(mContext);

        // create string of skyfall url and longlat at the end
        String url = mContext.getResources().getString(R.string.DarkSkyURL) + LongLat;

        // make a jason request to the Skyfall URL
        JsonObjectRequest ObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // create temporary strings for temperature and summary
                        String temp = "";
                        String summary = "";
                        // try to pull info from Jason variable
                        try{
                            // tempature data into string
                            temp = "Currently: " + response.getJSONObject("currently").getString("temperature") + "℉";
                            // summary data into string
                            summary = response.getJSONObject("daily").getString("summary");
                        }catch (JSONException e) {
                            // error statements if it didn't work
                            temp = "error loading data";
                            summary = "Still Waitng For Data";
                        }
                        // add information to City List after Json request
                        CityList.set(index,new City(index+1, cityName, temp,summary));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // if json request fails then add error message to display in app
                CityList.set(index,new City(index+1, cityName, "Error Getting Info From DarkSky","Make Sure Phone is Connected to Internet"));
            }
        });

        // put the request on the que
        queue.add(ObjectRequest);

    }

}

