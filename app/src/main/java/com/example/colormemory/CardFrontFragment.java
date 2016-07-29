package com.example.colormemory;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * A fragment representing the front of the card.
 */
public class CardFrontFragment extends Fragment implements View.OnTouchListener {
    private CardFrontFragmentListener mListener;

    public CardFrontFragment() {

    }
    public void setListener(Fragment parent){
        if (parent instanceof CardFrontFragmentListener) {
            mListener = (CardFrontFragmentListener) parent;
        } else {
            throw new RuntimeException(parent.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_front, container, false);
        view.setOnTouchListener(this);
        return view;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        mListener.onCardFrontTouched(this);
        return false;
    }

    public interface CardFrontFragmentListener {
        void onCardFrontTouched(Fragment fragment);
    }
}
