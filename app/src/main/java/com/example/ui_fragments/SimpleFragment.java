package com.example.ui_fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


/**
 *  Switching the device orientation after choosing "No" demonstrates that a Fragment can retain
 *  an instance of its data after a configuration change (such as changing the orientation).
 *  This feature makes a Fragment useful as a UI component, as compared to using separate Views.
 *  While an Activity is destroyed and recreated when a device's configuration changes, a Fragment
 *  is not destroyed.
 */


public class SimpleFragment extends Fragment {

    private static final int YES = 0;
    private static final int NO = 1;

    // If the user has not made any choice.
    private static final int NONE = 2;

    public int mRadioButtonChoice = NONE;

    private static final String CHOICE = "choice";

    OnFragmentInteractionListener mListener;

    // Compulsory no argument constructor.
    public SimpleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_simple, container, false);
        final RadioGroup radioGroup = rootView.findViewById(R.id.radio_group);
        final RatingBar ratingBar = rootView.findViewById(R.id.ratingBar);

        // getArguments() supplies the arguments pass when the Fragment was first instantiate.
         if (getArguments().containsKey(CHOICE)){
            mRadioButtonChoice = getArguments().getInt(CHOICE);
            // Check the radio button choice.
            if (mRadioButtonChoice != NONE){
                radioGroup.check(radioGroup.getChildAt(mRadioButtonChoice).getId());
            }
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
                View radioButton = radioGroup.findViewById(checkId);
                int index = radioGroup.indexOfChild(radioButton);
                TextView textView = rootView.findViewById(R.id.fragment_header);
                switch (index){
                    case YES:
                        textView.setText(R.string.yes_message);
                        mRadioButtonChoice = YES;
                        mListener.onRadioButtonChoice(YES);
                        break;
                    case NO:
                        textView.setText(R.string.no_message);
                        mRadioButtonChoice = NO;
                        mListener.onRadioButtonChoice(NO);
                        break;
                    default:
                        mRadioButtonChoice = NONE;
                        mListener.onRadioButtonChoice(NONE);
                        break;
                }
            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                String mString = getString(R.string.my_rating) + String.valueOf(ratingBar.getRating());
                Toast.makeText(getContext(), mString, Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    public static SimpleFragment newInstance(int choice){
        SimpleFragment fragment = new SimpleFragment();
        // Same as passing data through intents.
        Bundle bundle = new Bundle();
        bundle.putInt(CHOICE, choice);
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     *  The onAttach() method is called as soon as the Fragment is associated with the Activity.
     *  The code makes sure that the host Activity has implemented the callback interface.
     *  If not, it throws an exception.
     */

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof  OnFragmentInteractionListener){
            mListener = (OnFragmentInteractionListener) context;
        }
        else{
            throw new ClassCastException(context.toString() + getResources().getString(R.string.exception_message));
        }
    }

}
