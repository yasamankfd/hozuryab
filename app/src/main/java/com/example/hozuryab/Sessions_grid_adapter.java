package com.example.hozuryab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Sessions_grid_adapter extends BaseAdapter {
    LayoutInflater layoutInflater;
    String[] dates;
    Context ctx;
    public Sessions_grid_adapter(String[] dates,Context ctx)
    {
        this.dates = dates;
        this.ctx = ctx;

    }

    @Override
    public int getCount() {
        return dates.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(layoutInflater == null)
        {
            layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(view == null)
        {
            view = layoutInflater.inflate(R.layout.sessions_list_grid,null);
        }
        TextView date;
        date = view.findViewById(R.id.session_date_in_list);
        date.setText(dates[i]);

        return view;
    }
}
