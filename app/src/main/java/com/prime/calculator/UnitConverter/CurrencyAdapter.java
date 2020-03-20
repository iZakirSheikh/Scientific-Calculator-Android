package com.prime.calculator.UnitConverter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.prime.calculator.R;
import com.prime.calculator.Utility.Utils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>
        implements Filterable {
    private List<Unit> tempList;
    private List<Unit> items;
    private CurrencyAdapterClickListener mListener;
    private Context context;

    CurrencyAdapter(Context context, List<Unit> items, CurrencyAdapterClickListener mListener){
        this.context = context;
        this.items = items;
        tempList = new ArrayList<>(items); // so that it points to diff array
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public CurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.currency_item,
                parent, false);
        return new CurrencyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyViewHolder holder, int position) {
        Unit item = tempList.get(position);
        holder.cName.setText(item.getCurrency());
        holder.cCode.setText(item.getCode());
        holder.flag.setImageDrawable(Utils.getDrawableFromAssets(item.getCode(), context));
        holder.cRate.setText(Utils.formatDecimal(item.getBaseInValue(), 3));
    }



    @Override
    public int getItemCount() {
        return tempList.size(); // current adapter size
    }

    @Override
    public Filter getFilter() {
        return unitFilter;
    }

    class CurrencyViewHolder extends RecyclerView.ViewHolder {
        ImageView flag;
        TextView cName;
        TextView cCode, cRate;
        CurrencyViewHolder(@NonNull final View itemView) {
            super(itemView);
            flag = itemView.findViewById(R.id.currency_flag);
            cName = itemView.findViewById(R.id.currency_name);
            cCode = itemView.findViewById(R.id.currency_code);
            cRate = itemView.findViewById(R.id.currency_rate);
            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if(pos != RecyclerView.NO_POSITION)
                    mListener.onClick(tempList.get(getAdapterPosition()));
            });
        }
    }

    private Filter unitFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Unit> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(items);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Unit item : items) {
                    if (item.getCurrency().toLowerCase().contains(filterPattern) || filterPattern.isEmpty()) {
                        filteredList.add(item);
                    }
                }
                if(filterPattern.isEmpty())
                    filteredList.addAll(items);
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            tempList.clear();
            tempList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
    public interface CurrencyAdapterClickListener{
        void onClick(Unit Unit);
    }
}
