package com.covid.ui.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.covid.R;
import com.covid.Utils;
import com.covid.model.SelfReport;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<SelfReport> listItems;
    private Context context;
    private onHistoryClickListener MonHistoryClickListener;

    public HistoryAdapter(List<SelfReport> listItems, Context context, onHistoryClickListener MonHistoryClickListener) {
        this.listItems = listItems;
        this.context = context;
        this.MonHistoryClickListener=MonHistoryClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.historylist_item,parent,false);
        return new ViewHolder(v,MonHistoryClickListener);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SelfReport report=listItems.get(position);
        holder.date.setText(Utils.getHistoryFormatedDate(report.getCreationTime()));
        holder.time.setText(Utils.getHistoryFormatedTime(report.getCreationTime()));

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public  TextView date;
        public TextView time;

        onHistoryClickListener onHistoryClickListener;

        public ViewHolder(@NonNull View itemView,onHistoryClickListener onHistoryClickListener) {
            super(itemView);
            date= itemView.findViewById(R.id.tvDate);
            time=itemView.findViewById(R.id.tvTime);

            this.onHistoryClickListener=onHistoryClickListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onHistoryClickListener.onHistoryClick(listItems.get(getAdapterPosition()));
        }
    }

    public interface onHistoryClickListener{
        void onHistoryClick(SelfReport report);
    }

}
