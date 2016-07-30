package com.example.colormemory.fragments;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.colormemory.R;

import rx.Observable;
import rx.android.Events;
import rx.android.Properties;
import rx.util.functions.Action1;
import rx.util.functions.Func1;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NameInputFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NameInputFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NameInputFragment extends Fragment {
    private static final String ARG_PARAM1 = "Score";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private int mParam1;

    public NameInputFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.

     * @return A new instance of fragment NameInputFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NameInputFragment newInstance(int param1) {
        NameInputFragment fragment = new NameInputFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_name_input, container, false);
        TextView yourScoreTextview = (TextView) view.findViewById(R.id.yourScoreTextview);
        yourScoreTextview.setText(Integer.toString(mParam1));
        EditText editText = (EditText) view.findViewById(R.id.editText);
        final Observable<String> nameText = Events.text(editText);
        final Button submitButton = (Button) view.findViewById(R.id.submitButton);
        final Observable<Object> submitButtonClick = Events.click(submitButton);

        //auto button enable when text is empty
        nameText
                .map(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String text) {
                        return !text.trim().equals("");
                    }
                })
                .subscribe(Properties.enabledFrom(submitButton));

        submitButtonClick
                .flatMap(new Func1<Object, Observable<String>>() {
                    @Override
                    public Observable<String> call(Object o) {
                        return nameText;
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String name) {
                        if (mListener != null) {
                            mListener.onNameChanged(mParam1,name);
                        }
                    }
                });
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onNameChanged(int mParam1, String name);
    }
}
