package com.example.colormemory;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link CardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardFragment extends Fragment implements Animation.AnimationListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String TAG = CardsFragment.class.getSimpleName();

    // TODO: Rename and change types of parameters
    private int imageID;

    boolean isFlipped = false;
    private ImageView image1;
    private ImageView image2;
    private CardsFragmentListener mListener;

    public boolean isMatch() {
        return isMatch;
    }

    private boolean isMatch = false;

    public CardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment CardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CardFragment newInstance(int param1) {
        CardFragment fragment = new CardFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageID = getArguments().getInt(ARG_PARAM1);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_card, container, false);

        image1 = (ImageView) view.findViewById(R.id.ImageView01);
        image2 = (ImageView) view.findViewById(R.id.ImageView02);
        image2.setImageResource(imageID);
        image2.setVisibility(View.GONE);
        enableClick();
        return view;
    }

    public void disableClick() {
        image1.setOnClickListener(null);
    }

    public void enableClick() {
        if (!isMatch) {
            image1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    mListener.onImageClicked(CardFragment.this, imageID);
                    image1.setOnClickListener(null);
                    if (isFlipped) {
                        isFlipped = false;
                        applyRotation(0, 90, null);


                    } else {
                        isFlipped = true;
                        applyRotation(0, -90, null);

                    }
                }
            });
        }

    }

    @Override
    public void onResume() {

        super.onResume();
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

    public void setListener(CardsFragmentListener listener) {
        mListener = listener;
    }

    private void applyRotation(float start, float end, final CardsFragmentListener callback) {
// Find the center of image
        final float centerX = image1.getWidth() / 2.0f;
        final float centerY = image1.getHeight() / 2.0f;

// Create a new 3D rotation with the supplied parameter
// The animation listener is used to trigger the next animation
        final Flip3dAnimation rotation =
                new Flip3dAnimation(start, end, centerX, centerY);
        rotation.setDuration(100);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new AccelerateInterpolator());


        if (isFlipped) {
            rotation.setAnimationListener(new DisplayNextView(isFlipped, image1, image2, this));
            image1.startAnimation(rotation);
        } else {
            rotation.setAnimationListener(new DisplayNextView(isFlipped, image1, image2, new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (callback != null) {
                        callback.onCardFlip(CardFragment.this, imageID);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            }));
            image2.startAnimation(rotation);
        }

    }


    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
//        Log.d(TAG,"onAnimationEnd "+animation);
        mListener.onCardFlip(this, imageID);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }


    public void retoreCard(CardsFragmentListener callback) {
        if (isFlipped && !isMatch) {
            Log.d(TAG, "retoreCard " + imageID);
            isFlipped = false;
            applyRotation(0, 90, callback);
        } else {
            callback.onCardFlip(this, imageID);
        }
    }

    public void setIsMatch(boolean isMatch) {
        this.isMatch = isMatch;
    }


    public interface CardsFragmentListener {
        void onCardFlip(CardFragment cardFragment, Integer imageID);

        void onImageClicked(CardFragment cardFragment, int imageID);
    }
}
