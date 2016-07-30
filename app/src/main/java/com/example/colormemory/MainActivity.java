package com.example.colormemory;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.colormemory.data.DatabaseHandler;
import com.example.colormemory.data.ScoreObject;
import com.example.colormemory.fragments.CardsFragment;
import com.example.colormemory.fragments.NameInputFragment;

/**
 *
 *
 */
public class MainActivity extends Activity implements CardsFragment.ScordListener,
        NameInputFragment.OnFragmentInteractionListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String TAG_NAMEINPUTFRAGMENT = "NameInputFragment";
    TextView titleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new CardsFragment())
                .commit();
        try {
            getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            LayoutInflater inflater = LayoutInflater.from(this);
            View v = inflater.inflate(R.layout.abs_layout, null);

            titleTextView = (TextView) v.findViewById(R.id.mytext);
            titleTextView.setText(this.getTitle());


            getActionBar().setCustomView(v);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }

    }

    /**
     * The High Scores button must be visible to the top right of the window.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.highscore_menu, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if (item.getItemId() == R.id.high_score) {
            /**
             * when TAG_NAMEINPUTFRAGMENT not found , menu able to click
             */
            if (getFragmentManager().findFragmentByTag(TAG_NAMEINPUTFRAGMENT) == null) {
                showHiScore();
            }

        }
        return false;
    }

    /**
     * show {@link HiScoreActivity}
     */
    public void showHiScore() {
        Intent i = new Intent(MainActivity.this, HiScoreActivity.class);
        startActivity(i);
    }

    /**
     * receive score from {@link CardsFragment} during game
     * @param mScore
     */
    @Override
    public void onScore(int mScore) {
        try {
            titleTextView.setText(Integer.toString(mScore));
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    /**
     * receive score from {@link CardsFragment} when game end
     * @param mScore
     */
    @Override
    public void onGameEnded(int mScore) {
        Log.d(TAG, "onGameEnded");
        showNameInput(mScore);


    }

    /**
     * pass the score to {@link NameInputFragment}
     * @param mScore
     */
    private void showNameInput(int mScore) {
        /**
         * The game board must be displayed below the logo and high score button
         * */
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.animator.enter_from_bottom, R.animator.exit_to_top)
                .replace(R.id.overlay_fragment_container, NameInputFragment.newInstance(mScore), TAG_NAMEINPUTFRAGMENT)
                .commit();
    }

    /**
     * when user inputed name , remove name input , showHiScore , reset game
     * @param score
     * @param name
     */
    @Override
    public void onNameChanged(int score, String name) {
        DatabaseHandler db = DatabaseHandler.getInstance(this);
        db.addScore(new ScoreObject(name, score));

        getFragmentManager().beginTransaction().
                remove(getFragmentManager().findFragmentById(R.id.overlay_fragment_container)).commit();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new CardsFragment())
                .commit();
        titleTextView.setText(Integer.toString(0));
        showHiScore();
    }
}
