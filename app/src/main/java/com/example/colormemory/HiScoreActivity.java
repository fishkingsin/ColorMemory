package com.example.colormemory;

import android.app.Activity;
import android.os.Bundle;

import com.example.colormemory.fragments.HiScoreFragment;

public class HiScoreActivity extends Activity {

    /**
     * Activity to manage {@link HiScoreFragment}
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hi_score);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, HiScoreFragment.newInstance())
                .commit();
    }
}
