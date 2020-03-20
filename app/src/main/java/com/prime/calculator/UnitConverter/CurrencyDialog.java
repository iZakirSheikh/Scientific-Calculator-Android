package com.prime.calculator.UnitConverter;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.prime.calculator.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CurrencyDialog extends DialogFragment{

    //private DialogListener listener;
    private CurrencyAdapter currencyAdapter;
    private List<Unit> items = new ArrayList<>();
    private CurrencySelectionListener mListener;
    private int viewId;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null)
            viewId = getArguments().getInt("viewId");
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.DialogStyle);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.currency_dialog, null);

        builder.setView(view);

        SearchView searchView = view.findViewById(R.id.currency_search);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                currencyAdapter.getFilter().filter(newText);
                return false;
            }
        });
        Conversions mConversions = Conversions.getInstance(getActivity());
        items.clear();
        items.addAll(mConversions.getUnits(Converter.CURRENCY));
        setUpRecyclerView(view);
        return builder.create();
    }

    private void setUpRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.currency_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        CurrencyAdapter.CurrencyAdapterClickListener currencyAdapterClickListener = new CurrencyAdapter.CurrencyAdapterClickListener() {
            @Override
            public void onClick(Unit unitItem) {
                mListener.onCurrencySelected(unitItem, viewId);
                dismiss();

            }
        };
        currencyAdapter = new CurrencyAdapter(getActivity(), items, currencyAdapterClickListener);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(currencyAdapter);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mListener = (CurrencySelectionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must Implement on Currency Listener");
        }
    }



    public interface CurrencySelectionListener {
        void onCurrencySelected(Unit unitItem, int viewId);
    }
}
