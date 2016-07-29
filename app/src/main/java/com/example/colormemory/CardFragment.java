package com.example.colormemory;

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
 * Use the {@link CardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardFragment extends Fragment implements CardFrontFragment.CardFrontFragmentListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = CardsFragment.class.getSimpleName();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    boolean isFlipped = false;

    public CardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CardFragment newInstance(String param1, String param2) {
        CardFragment fragment = new CardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_card, container, false);
        CardFrontFragment frontFragment = new CardFrontFragment();
        frontFragment.setListener(this);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.card, frontFragment)
                .commit();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    public void setListener(Fragment fragment) {

    }

    @Override
    public void onCardFrontTouched(Fragment fragment) {
        Log.d(TAG, "CardFrontFragment onCardTouched:" + fragment.getTag());
        if (isFlipped) {

        } else {
            getFragmentManager()
                    .beginTransaction()

                    // Replace the default fragment animations with animator resources representing
                    // rotations when switching to the back of the card, as well as animator
                    // resources representing rotations when flipping back to the front (e.g. when
                    // the system Back button is pressed).
                    .setCustomAnimations(
                            R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                            R.animator.card_flip_left_in, R.animator.card_flip_left_out)
                    // Replace any fragments currently in the container view with a fragment
                    // representing the next page (indicated by the just-incremented currentPage
                    // variable).
                    .replace(R.id.card, new CardBackFragment())

                    // Add this transaction to the back stack, allowing users to press Back
                    // to get to the front of the card.
                    .addToBackStack(null)

                    // Commit the transaction.
                    .commit();
        }
    }
}
