package com.github.angads25.daterangepicker.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.angads25.daterangepicker.R;

import java.util.ArrayList;

/**
 * <p>
 * Created by Angad Singh on 23/12/17.
 * </p>
 */

public class YearPickerAdapter extends RecyclerView.Adapter <YearPickerAdapter.YearPickerViewHolder>{
    private Context context;
    private ArrayList<Integer> years;

    public YearPickerAdapter(Context context, ArrayList<Integer> years) {
        this.context = context;
        this.years = years;
    }

    @Override
    public YearPickerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_year, parent, false);
        return new YearPickerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(YearPickerViewHolder holder, int position) {
        holder.yearLabel.setText(String.valueOf(years.get(position)));
    }

    @Override
    public int getItemCount() {
        return years.size();
    }

    class YearPickerViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView yearLabel;

        public YearPickerViewHolder(View itemView) {
            super(itemView);
            yearLabel = itemView.findViewById(R.id.label_year);
        }
    }
}
