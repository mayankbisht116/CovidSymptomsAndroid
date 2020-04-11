package com.covid.ui.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.covid.R;
import com.covid.ui.history.HistoryAdapter;
import com.covid.ui.history.HistoryItemModel;

import java.util.List;

public class CountryListAdapter extends RecyclerView.Adapter<CountryListAdapter.ViewHolder> {

    private List<String> listItems;
    private CountryClickListener countryClickListener;

    public CountryListAdapter(List<String> listItems, CountryListAdapter.CountryClickListener countryClickListener) {
        this.listItems = listItems;
        this.countryClickListener=countryClickListener;
    }


    @NonNull
    @Override
    public CountryListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.historylist_item,parent,false);
        return new CountryListAdapter.ViewHolder(v,countryClickListener);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String country=listItems.get(position);
        holder.country.setText(country);
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView country;

        CountryClickListener onCountryClickListener;

        public ViewHolder(@NonNull View itemView, CountryClickListener onCountryClickListener) {
            super(itemView);
            country= itemView.findViewById(R.id.tvDate);
            itemView.findViewById(R.id.tvTime).setVisibility(View.GONE);
            this.onCountryClickListener=onCountryClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
                 int index = getAdapterPosition();
                onCountryClickListener.onCountryClick(listItems.get(index));
        }
    }

    public interface CountryClickListener{
        void onCountryClick(String countryName);
    }

}
