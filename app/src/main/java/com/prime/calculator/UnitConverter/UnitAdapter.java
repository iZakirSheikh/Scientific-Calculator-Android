package com.prime.calculator.UnitConverter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class UnitAdapter extends ArrayAdapter<Unit> {

    private Context context;
    public UnitAdapter(Context context, ArrayList<Unit> items) {
        super(context, 0, items);
        this.context = context;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    android.R.layout.simple_list_item_1, parent, false);
        }
        TextView title = convertView.findViewById(android.R.id.text1);
        Unit currentItem = getItem(position);
        if (currentItem != null) {
            title.setText(context.getString(currentItem.getLabelResId()));
        }
        return convertView;
    }
}
