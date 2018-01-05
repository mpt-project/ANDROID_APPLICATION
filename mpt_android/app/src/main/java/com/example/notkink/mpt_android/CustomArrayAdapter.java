package com.example.notkink.mpt_android;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Notkink on 20.11.2017.
 */

public class CustomArrayAdapter extends ArrayAdapter<BillEntry>  {
    private List<BillEntry> billEntries;
    private Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView title;
        TextView description;
        ImageView thumbnail;
        TextView monthsLeft;
    }

    public CustomArrayAdapter(List<BillEntry> entries, Context context) {
        super(context, R.layout.row, entries);
        billEntries = entries;
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        BillEntry entry = getItem(position);
        ViewHolder viewHolder;


        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row, parent, false);
            viewHolder.title = convertView.findViewById(R.id.titleId);
            viewHolder.description = convertView.findViewById(R.id.descriptionId);
            viewHolder.thumbnail = convertView.findViewById(R.id.thmbnailId);
            viewHolder.monthsLeft = convertView.findViewById(R.id.monthsLeft);



            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }

        viewHolder.title.setText(entry.getBillName());
        viewHolder.description.setText(entry.getShopName());
//        viewHolder.description.setText( entry.getPurchaseDate().toString());
//        viewHolder.info.setOnClickListener(this);

        if(Integer.parseInt(entry.getBillEntryMonthsLeft()) < 5) {
             viewHolder.monthsLeft.setBackgroundColor(ContextCompat.getColor(mContext, R.color.red));
        }else if(Integer.parseInt(entry.getBillEntryMonthsLeft()) < 9){
            viewHolder.monthsLeft.setBackgroundColor(ContextCompat.getColor(mContext, R.color.orange));


        } else{
            viewHolder.monthsLeft.setBackgroundColor(ContextCompat.getColor(mContext, R.color.green));
        }

        viewHolder.monthsLeft.setText(entry.getBillEntryMonthsLeft() + " " + "mies.");
        viewHolder.thumbnail.setTag(position);
        viewHolder.thumbnail.setImageBitmap(entry.getPhoto());
        // Return the completed view to render on screen
        return convertView;
    }
}
