
package com.example.a6_hw3;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


//Activity that controls the Detail Fragment
public class DetailsOfCityActivity extends AppCompatActivity {

    public static String cityID = "cityId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // remove extra fragment if both are showing on rotation to portrait
        boolean bothFragments = getResources().getBoolean(R.bool.show);
        if (bothFragments) {
            finish();
            return;
        }

        setContentView(R.layout.activity_details_of_cities);

        //Sets up the control for the Details Fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.details_fragment_container);


        if (fragment == null) {
            // Use city ID from ListFragment to instantiate DetailsFragment
            int cityId = getIntent().getIntExtra(cityID, 1);
            fragment = DetailsOfCitiesFragment.newInstance(cityId);
            fragmentManager.beginTransaction()
                    .add(R.id.details_fragment_container, fragment)
                    .commit();
        }


    }
}
