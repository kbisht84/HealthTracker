package com.kanakb.healthtracker;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kanakbisht on 1/23/16.
 */
public class CustomAdapter extends CursorAdapter implements Filterable{

    private ArrayList<String> items;
    private ArrayList<String> orig;






    public CustomAdapter(Context context, Cursor cursor, ArrayList<String> items) {

        super(context, cursor, false);

        this.items = new ArrayList<>();
        this.items=items;
        orig=new ArrayList<>();
        orig.addAll(items);

    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                constraint = constraint.toString().toLowerCase();
                FilterResults result = new FilterResults();

                if (constraint != null
                        && constraint.toString().length() > 0) {
                    List<String> founded = new ArrayList<>();
                    for (int i = 0, l = orig.size(); i < l; i++) {
                        if (orig.get(i)
                                .toString()
                                .toLowerCase()
                                .startsWith(
                                        constraint.toString().toLowerCase()))
                            founded.add(orig.get(i));
                    }

                    if(founded.size()>0){
                        result.values = founded;
                        result.count = founded.size();
                    } else {
                        synchronized (this) {
                            result.values = orig;
                            result.count = orig.size();
                        }

                    }

                }
                else{
                    result.values = orig;
                    result.count = orig.size();
                }
                return result;

            }

            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {

                items.clear();
//                items = new ArrayList<>();
                items.addAll((ArrayList<String>) results.values);
                notifyDataSetChanged();

            }

        };
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolder holder = (ViewHolder) view.getTag();
        holder.textView.setText(items.get(cursor.getPosition()));

    }

    public class ViewHolder {
        public TextView textView;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item, parent, false);

        holder.textView = (TextView) view.findViewById(R.id.item);
        view.setTag(holder);

        return view;

    }

}
