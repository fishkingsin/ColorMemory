package com.example.colormemory.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.colormemory.BuildConfig;
import com.example.colormemory.MainActivity;
import com.example.colormemory.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class CardsFragment extends Fragment implements CardFragment.CardsFragmentListener {
    private static final String TAG = CardsFragment.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match

    /**
     * card view id
     */
    private static Integer[] cards = new Integer[]{
            R.id.card1, R.id.card2, R.id.card3, R.id.card4,
            R.id.card5, R.id.card6, R.id.card7, R.id.card8,
            R.id.card9, R.id.card10, R.id.card11, R.id.card12,
            R.id.card13, R.id.card14, R.id.card15, R.id.card16};

    /**
     * drawable id
     */
    private static Integer[] cardImages = new Integer[]{
            R.drawable.colour1, R.drawable.colour1, R.drawable.colour2, R.drawable.colour2,
            R.drawable.colour3, R.drawable.colour3, R.drawable.colour4, R.drawable.colour4,
            R.drawable.colour5, R.drawable.colour5, R.drawable.colour6, R.drawable.colour6,
            R.drawable.colour7, R.drawable.colour7, R.drawable.colour8, R.drawable.colour8};

    private AtomicInteger fisrtImageID = new AtomicInteger();
    private AtomicInteger secondImageID = new AtomicInteger();
    private String firstClickTag = "";
    private String secondClickTag = "";
    private Handler handler;
    private AtomicInteger mScore = new AtomicInteger();

    ScordListener mListener;

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
        handler = new Handler();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cards, container, false);
        initCards();
        return view;
    }

    /**
     * init card fragments
     */
    private void initCards() {
        ArrayList queue = new ArrayList<>(Arrays.asList(cardImages));
        Random randomGenerator = new Random();
        for (Integer i : cards) {
            int index = randomGenerator.nextInt(queue.size());
            Integer imageId = (Integer) queue.remove(index);
            CardFragment fragment = CardFragment.newInstance(imageId);
            fragment.setListener(this);
            getFragmentManager()
                    .beginTransaction()
                    .replace(i, fragment, i.toString())
                    .commit();


        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ScordListener) {
            mListener = (ScordListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onCardFlip(CardFragment cardFragment, Integer imageID) {

            mListener.onScore(mScore.get());


    }

    @Override
    public void onImageClicked(CardFragment cardFragment, int imageID) {
//        Log.d(TAG, "onImageClicked " + imageID);
        cardFragment.disableClick();

        if (fisrtImageID.get() == 0) {
            fisrtImageID.set(imageID);
            firstClickTag = cardFragment.getTag();
        } else if (secondImageID.get() == 0) {
            secondImageID.set(imageID);
            secondClickTag = cardFragment.getTag();
            for (Integer i : cards) {
                CardFragment _cardFragment = (CardFragment) getFragmentManager().findFragmentByTag(i.toString());
                _cardFragment.disableClick();
            }


            Integer value1 = fisrtImageID.get();
            Integer value2 = secondImageID.get();


            if (value1.equals(value2)) {


                mScore.set(mScore.get()+2);

                for (Integer i : cards) {
                    if (!i.toString().equals(firstClickTag) && !i.toString().equals(secondClickTag)) {
                        CardFragment _cardFragment = (CardFragment) getFragmentManager().findFragmentByTag(i.toString());
                        _cardFragment.enableClick();
                        _cardFragment.retoreCard(new CardFragment.CardsFragmentListener() {
                            @Override
                            public void onCardFlip(CardFragment cardFragment, Integer imageID) {
                                
                            }

                            @Override
                            public void onImageClicked(CardFragment cardFragment, int imageID) {

                            }
                        });
                    } else {
                        CardFragment _cardFragment = (CardFragment) getFragmentManager().findFragmentByTag(i.toString());
                        _cardFragment.setIsMatch(true);
                    }

                }
                boolean isGameEnd = false;
                for (Integer i : cards) {
                    CardFragment _cardFragment = (CardFragment) getFragmentManager().findFragmentByTag(i.toString());
                     if(!_cardFragment.isMatch()){
                         isGameEnd = false;
                         break;
                     }else{
                         isGameEnd = true;
                     }


                }
                if(isGameEnd){
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mListener.onGameEnded(mScore.get());
                        }
                    },1000);

                }

                firstClickTag = "";
                secondClickTag = "";
                fisrtImageID.set(0);
                secondImageID.set(0);
            } else {
//                Log.d(TAG, "onCardFlip NOT Match");
                /**
                 * After each round, a brief one (1) second pause should be implemented before scoring to allow the
                 * player to see what the second selected card is.
                 */
                mScore.set(mScore.get()-1);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        for (Integer i : cards) {
                            CardFragment _cardFragment = (CardFragment) getFragmentManager().findFragmentByTag(i.toString());

                            _cardFragment.retoreCard(new CardFragment.CardsFragmentListener() {

                                @Override
                                public void onCardFlip(CardFragment cardFragment, Integer imageID) {
                                    cardFragment.enableClick();
                                }

                                @Override
                                public void onImageClicked(CardFragment cardFragment, int imageID) {

                                }
                            });
                        }
                        fisrtImageID.set(0);
                        secondImageID.set(0);
                        firstClickTag = "";
                        secondClickTag = "";
                    }
                }, 1000);


            }

        }


    }

    public static CardsFragment getInstance(Context context) {
        CardsFragment fragment = new CardsFragment();
        fragment.mListener = (ScordListener) context;
        return fragment;

    }

    public interface ScordListener {
        void onScore(int mScore);
        void onGameEnded(int mScore);
    }

}
