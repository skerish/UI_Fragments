package com.example.ui_fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 *  To add a Fragment dynamically to an Activity so that the Activity can add, replace,
 *  or remove it, specify a ViewGroup inside the layout file for the Activity such as a FrameLayout.
 */

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener{

    private Button button;

    // For tracking the state of the fragment
    private boolean isFragmentDisplayed = false;

    // key for retrieving data
    private static final String FRAGMENT_STATE = "state_for_fragment";

    // Default choice is NONE
    private int mRadioButtonChoice = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFragmentDisplayed){
                    displayFragment();
                }
                else{
                    closeFragment();
                }
            }
        });

        if (savedInstanceState != null){
            isFragmentDisplayed = savedInstanceState.getBoolean(FRAGMENT_STATE);
            if (isFragmentDisplayed){
                button.setText(R.string.close);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(FRAGMENT_STATE, isFragmentDisplayed);
    }

    /**
     *  Fragment operations are wrapped into a transaction (similar to a bank transaction)
     *  so that all the operations finish before the transaction is committed for the final result.
     */

    public void displayFragment(){
        SimpleFragment simpleFragment = SimpleFragment.newInstance(mRadioButtonChoice);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //This back stack is managed by the Activity. It allows the user to return to the
        // previous Fragment state by pressing the Back button.
        fragmentTransaction.add(R.id.fragment_container,simpleFragment).addToBackStack(null).commit();

        button.setText(R.string.close);
        isFragmentDisplayed = true;
    }

    public void closeFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        SimpleFragment simpleFragment = (SimpleFragment) fragmentManager.findFragmentById(R.id.fragment_container);
        if (simpleFragment != null){
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(simpleFragment).commit();
        }
        button.setText(R.string.open);
        isFragmentDisplayed = false;
    }


    // Retrieving the value from the interface, whose value is set by the Fragment.
    @Override
    public void onRadioButtonChoice(int choice) {
        mRadioButtonChoice = choice;
        Toast.makeText(this, "Choice is " + Integer.toString(choice), Toast.LENGTH_SHORT).show();
    }
}
