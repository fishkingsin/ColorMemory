package com.example.colormemory;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class CardsFragment extends Fragment  {
    private static final String TAG = CardsFragment.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static Integer[] cards = new Integer[]{
            R.id.card1, R.id.card2, R.id.card3, R.id.card4,
            R.id.card5, R.id.card6, R.id.card7, R.id.card8,
            R.id.card9, R.id.card10, R.id.card11, R.id.card12,
            R.id.card13, R.id.card14, R.id.card15, R.id.card16};

    private static Integer[] cardImages = new Integer[]{
            R.drawable.colour1, R.drawable.colour1, R.drawable.colour2, R.drawable.colour2,
            R.drawable.colour3, R.drawable.colour3, R.drawable.colour4, R.drawable.colour4,
            R.drawable.colour4, R.drawable.colour4, R.drawable.colour6, R.drawable.colour6,
            R.drawable.colour7, R.drawable.colour7, R.drawable.colour8, R.drawable.colour8};

    public CardsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cards, container, false);

        for (Integer i : cards) {
            CardFragment fragment = new CardFragment();
            fragment.setListener(this);
            getFragmentManager()
                    .beginTransaction()
                    .replace(i, fragment, i.toString())
                    .commit();


        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        while(getFragmentManager().getBackStackEntryCount()>0){
            getFragmentManager().popBackStackImmediate();
        }
    }




}
