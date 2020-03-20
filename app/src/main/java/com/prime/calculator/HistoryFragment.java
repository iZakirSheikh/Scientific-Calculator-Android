package com.prime.calculator;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.prime.calculator.Utility.DatabaseHelper;
import com.prime.calculator.Utility.History;
import com.prime.calculator.Utility.HistoryAdapter;
import com.prime.calculator.Utility.Utils;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class HistoryFragment extends Fragment {
    public static final String TAG = "HistoryFragment";

    private Context activityContext;
    private List<History> historyList = new ArrayList<>();
    private DatabaseHelper mDatabaseHelper;
    private RecyclerView mRecyclerView;
    private TextView noHistoryView;
    private String tableName;
    private ImageView noHistoryViewIcon;

    private Toolbar mToolbar;
    private FloatingActionButton fab;
    private HistoryAdapter mAdapter;


    private HistoryFragmentListener mListener;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*This is A key Used to Know Which Activity Actually Launched the Fragment*/
        tableName = getArguments().getString("TableName");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        initializeViews(view);
        setUpRecyclerView();
        setUpToolbar();
        toggleEmptyHistory();
        return view;
    }

    private void setUpRecyclerView() {
        historyList.addAll(mDatabaseHelper.getAllHistory(tableName));
        HistoryAdapter.RecyclerViewClickListener recyclerViewClickListener = new HistoryAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                History item = historyList.get(position);
                switch (view.getId()) {
                    case R.id.expression:
                        mListener.onHistoryItemSelected(item.getExpression());
                        break;
                    case R.id.result:
                        mListener.onHistoryItemSelected(item.getResult());
                        break;
                }
                getActivity().onBackPressed();
            }
        };
        mAdapter = new HistoryAdapter(activityContext, recyclerViewClickListener, historyList);
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activityContext);
        layoutManager.setReverseLayout(true);
        mRecyclerView.setLayoutManager(layoutManager);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performDeletion();
            }
        });
    }

    private void performDeletion() {
        int count = mDatabaseHelper.getHistoryCount(tableName);
        if(count > 0){
            final Dialog dialog = new Dialog(getContext());
            // Include dialog.xml file
            dialog.setContentView(R.layout.custom_alert_dialogue);
            dialog.show();
            Button positiveButton = dialog.findViewById(R.id.positive_button);
            Button negativeButton = dialog.findViewById(R.id.negative_button);
            TextView message = dialog.findViewById(R.id.message);
            message.setText("Are you sure, you want to clear all records?");
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    historyList.clear();
                    mDatabaseHelper.deleteHistory(tableName);
                    mAdapter.notifyDataSetChanged();
                    noHistoryView.setVisibility(View.VISIBLE);
                    noHistoryViewIcon.setVisibility(View.VISIBLE);
                    dialog.dismiss();
                    Utils.showToast(getContext(), "History Cleared");
                }
            });

            negativeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }

    }

    private void initializeViews(View view) {
        noHistoryViewIcon = view.findViewById(R.id.empty_history_view_icon);
        noHistoryView = view.findViewById(R.id.empty_history_view);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        fab = view.findViewById(R.id.fab);
        mDatabaseHelper = new DatabaseHelper(activityContext);
        mListener.setDrawerEnabled(false);
    }

    private void setUpToolbar() {
        mToolbar = getActivity().findViewById(R.id.toolbar);
        mToolbar.findViewById(R.id.toggle_mode).setVisibility(View.GONE);
        mToolbar.findViewById(R.id.btn_history).setVisibility(View.GONE);
        mToolbar.setTitle(R.string.title_fragment_history);
    }

    private void toggleEmptyHistory() {
        if (mDatabaseHelper.getHistoryCount(tableName) > 0) {
            noHistoryView.setVisibility(View.GONE);
            noHistoryViewIcon.setVisibility(View.GONE);
        } else {
            noHistoryView.setVisibility(View.VISIBLE);
            noHistoryViewIcon.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*Getting Application Context*/
        this.activityContext = context;
        if (context instanceof HistoryFragmentListener) {
            mListener = (HistoryFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement HistoryFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener.setDrawerEnabled(true);
        mListener = null;
        /*This Reverts Backs Toolbar Changes on Detach*/
        switch (tableName) {
            case StandardActivity.TAG:
                mToolbar.setTitle(R.string.title_activity_standard);
                mToolbar.findViewById(R.id.btn_history).setVisibility(View.VISIBLE);
                break;
            case ScientificActivity.TAG:
                mToolbar.setTitle(R.string.title_activity_scientific);
                mToolbar.findViewById(R.id.btn_history).setVisibility(View.VISIBLE);
                mToolbar.findViewById(R.id.toggle_mode).setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface HistoryFragmentListener {
        void onHistoryItemSelected(String expression);

        void setDrawerEnabled(boolean enabled);
    }
}