

package com.example.a6_hw3;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

//Displays the list of cities using Fragments and RecyclerViewAdapater
public class MainActivity extends AppCompatActivity implements ListOfCitiesFragment.PickedCityListner {


    private static final String KEY_City_ID = "CityId";
    private int mCityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCityId = -1;

        //Sets up the control for the List Fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.list_fragment_container);

        if (fragment == null) {
            fragment = new ListOfCitiesFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.list_fragment_container, fragment)
                    .commit();
        }

        // Replace DetailsFragment if state saved when going from portrait to landscape
        if (savedInstanceState != null && savedInstanceState.getInt(KEY_City_ID) != 0
                && getResources().getBoolean(R.bool.show)) {
            mCityId = savedInstanceState.getInt(KEY_City_ID);
            Fragment CityFragment = DetailsOfCitiesFragment.newInstance(mCityId);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.details_fragment_container, CityFragment)
                    .commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        // Save state when something is selected
        if (mCityId != -1) {
            savedInstanceState.putInt(KEY_City_ID, mCityId);
        }
    }

    @Override
    public void CityPicked(int CityId) {

        mCityId = CityId;

        if (findViewById(R.id.details_fragment_container) == null) {
            // Must be in portrait, so start activity
            Intent intent = new Intent(this, DetailsOfCityActivity.class);
            intent.putExtra(DetailsOfCityActivity.cityID, CityId);
            startActivity(intent);
        } else {
            // Replace previous fragment (if one exists) with a new fragment
            Fragment CityFragment = DetailsOfCitiesFragment.newInstance(CityId);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.details_fragment_container, CityFragment)
                    .commit();
        }
    }
}
