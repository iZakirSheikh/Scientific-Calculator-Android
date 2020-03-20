package com.prime.calculator.Utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prime.calculator.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


/**
 * This Class Creates an adapter for Database Element
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    private List<History> historyList;
    private RecyclerViewClickListener mListener;

    public class MyViewHolder extends RecyclerView.ViewHolder implements OnClickListener{
        TextView expression;
        public TextView result;
        TextView timestamp;
        View spltLine;

        MyViewHolder(View view, RecyclerViewClickListener listener) {
            super(view);
            expression = view.findViewById(R.id.expression);
            result = view.findViewById(R.id.result);
            timestamp = view.findViewById(R.id.timestamp);
            spltLine = view.findViewById(R.id.SplitLine_hor1);
            mListener = listener;
            expression.setOnClickListener(this);
            result.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            if(pos != RecyclerView.NO_POSITION)
              mListener.onClick(view, getAdapterPosition());
        }
    }

    public HistoryAdapter(Context context, RecyclerViewClickListener mListener, List<History> historyList) {
        Context context1 = context;
        this.historyList = historyList;
        this.mListener = mListener;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_item, parent, false);

        return new MyViewHolder(itemView, mListener);
    }


    @Override
    public void onViewRecycled(@NonNull MyViewHolder holder) {
        super.onViewRecycled(holder);
        holder.spltLine.setVisibility(View.GONE);
        holder.timestamp.setVisibility(View.GONE);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        History history = historyList.get(position);

        holder.expression.setText(history.getExpression());
        holder.result.setText(history.getResult());
        // Formatting and displaying timestamp
        String date = formatDate(historyList.get(position).getTimestamp());
        String prevDate = historyList.size() > position + 1 ? formatDate(historyList.get(position + 1)
                .getTimestamp()) : null;
        if(!date.equals(prevDate)) {
            holder.spltLine.setVisibility(View.VISIBLE);
            holder.timestamp.setVisibility(View.VISIBLE);
            holder.timestamp.setText(formatDate(history.getTimestamp()));
        }
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    /**
     * Formatting timestamp to `MMM d` format
     * Input: 2018-02-21 00:15:42
     * Output: Feb 21
     */
    private String formatDate(String dateStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM d", Locale.US);
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        String fdate = null;
        try {
            Date d = fmt.parse(dateStr);
            fdate = simpleDateFormat.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return fdate;
    }

    public interface RecyclerViewClickListener {
        void onClick(View view, int position);
    }
}