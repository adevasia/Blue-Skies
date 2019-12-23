

package com.example.a6_hw3;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//This class controls the view flow of the MainActivity
//RecyclerViewAdapter code is base off of Zybooks BandDatabase V3
public class ListOfCitiesFragment extends Fragment {

    // For the activity to implement
    public interface PickedCityListner {
        void CityPicked(int cityId);
    }

    // Reference to the activity
    private PickedCityListner cListener;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_of_cities, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.city_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Send cities to recycler view
        CityAdapter adapter = new CityAdapter(CityDatabase.get(getContext()).getCities());
        recyclerView.setAdapter(adapter);

        return view;
    }

    //Subclass that describes an item view in a RecyclerView and binds an individual item to the view
    private class CityHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private City mCity;

        private TextView mNameTextView;

        //Tells the RecyclerView which layout to use
        public CityHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_city_name, parent, false));
            itemView.setOnClickListener(this);
            mNameTextView = (TextView) itemView.findViewById(R.id.cityName);
        }

        //Binds the row and the city name together
        public void bind(City city) {
            mCity = city;
            mNameTextView.setText(mCity.getName());
        }

        //Listens for a click
        @Override
        public void onClick(View view) {
            // Tell ListActivity what city was clicked
            cListener.CityPicked(mCity.getId());
        }
    }

    //Subclass that binds the data to be scrolled with views that are displayed by a RecyclerView
    private class CityAdapter extends RecyclerView.Adapter<CityHolder> {

        private List<City> CityList;

        public CityAdapter(List<City> cities) {
            CityList = cities;
        }

        @Override
        public CityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new CityHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(CityHolder holder, int position) {
            City city = CityList.get(position);
            holder.bind(city);
        }

        @Override
        public int getItemCount() {
            return CityList.size();
        }
    }

    //Default function made by fragments
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PickedCityListner) {
            cListener = (PickedCityListner) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " Picked City not set correctly");
        }
    }

    //Default function made by fragments
    @Override
    public void onDetach() {
        super.onDetach();
        cListener = null;
    }


}
