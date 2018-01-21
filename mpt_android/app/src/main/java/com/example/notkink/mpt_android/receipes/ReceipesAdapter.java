package com.example.notkink.mpt_android.receipes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.notkink.mpt_android.BillEntry;
import com.example.notkink.mpt_android.R;
import com.example.notkink.mpt_android.auth.Receipt;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Notkink on 20.11.2017.
 */

public class ReceipesAdapter extends ArrayAdapter<Receipt> {
    private List<Receipt> billEntries;
    private Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView title;
        TextView description;
        ImageView thumbnail;
    }

    public ReceipesAdapter(List<Receipt> entries, Context context) {
        super(context, R.layout.row, entries);
        billEntries = entries;
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Receipt entry = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row, parent, false);
            viewHolder.title = convertView.findViewById(R.id.titleId);
            viewHolder.description = convertView.findViewById(R.id.descriptionId);
            viewHolder.thumbnail = convertView.findViewById(R.id.thmbnailId);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        if (entry == null) return convertView;

        viewHolder.title.setText(entry.name);
        viewHolder.description.setText(entry.shop);
//        viewHolder.description.setText( entry.getPurchaseDate().toString());
//        viewHolder.info.setOnClickListener(this);
        viewHolder.thumbnail.setTag(position);
        Picasso.with(mContext).load(entry.url).into(viewHolder.thumbnail);
        // Return the completed view to render on screen
        return convertView;
    }
}
