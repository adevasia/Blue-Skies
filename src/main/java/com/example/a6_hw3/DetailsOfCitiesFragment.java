
package com.example.a6_hw3;

// import statements
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

// puts information for each city into the fragment to be displayed
public class DetailsOfCitiesFragment extends Fragment {

    private City cityObject;

    //Initializes variables used throughout the class
    public static DetailsOfCitiesFragment newInstance(int cityId) {
        DetailsOfCitiesFragment fragment = new DetailsOfCitiesFragment();
        Bundle args = new Bundle();
        args.putInt("cityId", cityId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the city ID from the intent that started DetailsActivity
        int cityId = 1;
        if (getArguments() != null) {
            cityId = getArguments().getInt("cityId");
        }

        cityObject = CityDatabase.get(getContext()).getCity(cityId);
    }

    //Allows information to appear on the fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_details_of_cities, container, false);

        // get access to TextView variables from xml file through IDs
        TextView nameTV = (TextView) view.findViewById(R.id.cityName);
        TextView temperatureTV = (TextView) view.findViewById(R.id.cityTemperature);
        TextView summaryTV = (TextView) view.findViewById(R.id.summaryDescription);

        // set variables to corresponding name/temp/summary
        nameTV.setText(cityObject.getName());
        temperatureTV.setText(cityObject.getTemperature());
        summaryTV.setText(cityObject.getSummary());

        return view;
    }
}
