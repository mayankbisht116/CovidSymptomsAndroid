package com.covid.ui.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.covid.R;

import java.util.ArrayList;

public class QuestionAdapter extends ArrayAdapter {


    private ArrayList dataSet;
    Context mContext;

    private static class ViewHolder {
        TextView txtName;
        CheckBox checkBox;
    }

    public QuestionAdapter(ArrayList data, Context context) {
        super(context, R.layout.rowquestion_item, data);
        this.dataSet = data;
        this.mContext = context;

    }
    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public QuestionModel getItem(int position) {
        return (QuestionModel) dataSet.get(position);
    }


    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;
        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowquestion_item, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.tvQuestion);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkQuestion);

            result=convertView;
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        QuestionModel item = getItem(position);


        viewHolder.txtName.setText(item.ques);
        viewHolder.checkBox.setChecked(item.checked);

        return result;
    }
}
