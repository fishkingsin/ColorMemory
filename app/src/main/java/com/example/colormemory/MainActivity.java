package com.example.colormemory;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MainActivity extends Activity implements CardsFragment.ScordListener {


    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String TAG_HISCOREFRAGMENT = "HiScoreFragment";
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.highscore_menu, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item)  {
        if(item.getItemId() == R.id.high_score){
            if(getFragmentManager().findFragmentByTag(TAG_HISCOREFRAGMENT)==null) {
                showHiScore();
            }else{
                getFragmentManager().popBackStack();
            }
        }
        //How to determine which menu clicked?
        return false;
    }

    private void showHiScore() {
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.animator.enter_from_bottom, R.animator.exit_to_top)
                .replace(R.id.overlay_fragment_container, HiScoreFragment.newInstance(" "," "),TAG_HISCOREFRAGMENT)
                .commit();
    }

    @Override
    public void onScore(int mScore) {
        try {
            titleTextView.setText(Integer.toString(mScore));
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    @Override
    public void onGameEnded() {
        Log.d(TAG, "onGameEnded");
        //show finish
        //show imput name
            // show high score
                //
        showNameInput();


    }

    private void showNameInput() {
        /*The game board must be displayed below the logo and high score button*/
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.animator.enter_from_bottom, R.animator.exit_to_top)
                .replace(R.id.overlay_fragment_container, NameInputFragment.newInstance(" "," "),TAG_NAMEINPUTFRAGMENT)
                .commit();
    }
}
