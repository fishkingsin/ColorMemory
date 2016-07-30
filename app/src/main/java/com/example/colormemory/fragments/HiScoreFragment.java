package com.example.colormemory.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.colormemory.R;
import com.example.colormemory.data.DatabaseHandler;
import com.example.colormemory.data.ListDataAdapter;
import com.example.colormemory.data.ScoreObject;

import java.util.ArrayList;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link HiScoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HiScoreFragment extends Fragment {

    ArrayList<ScoreObject> items = new ArrayList();
    private ListDataAdapter listAdapter;
    private Random random = new Random();
    public HiScoreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HiScoreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HiScoreFragment newInstance() {
        HiScoreFragment fragment = new HiScoreFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_hi_score, container, false);
        DatabaseHandler db = DatabaseHandler.getInstance(getActivity());
        items = db.getScores();
        ListView listView = (ListView) view.findViewById(R.id.list);
        listAdapter = new ListDataAdapter(getActivity(), items);
        listView.setAdapter(listAdapter);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
