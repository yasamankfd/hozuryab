package com.example.hozuryab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Con_grid_adapter extends BaseAdapter {
    Context ctx ;
    String[] titles;
    String[] IDs;

    LayoutInflater layoutInflater;
    public Con_grid_adapter(Context ctx, String[] titles, String[] IDs) {
        this.ctx = ctx;
        this.titles = titles;
        this.IDs = IDs;
    }

    @Override
    public int getCount() {
        return titles.length;
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
            view = layoutInflater.inflate(R.layout.controller_grid,null);

        }
        TextView title , id;
        title = view.findViewById(R.id.class_title);
        id = view.findViewById(R.id.class_id);
        title.setText(titles[i]);
        id.setText(IDs[i]);
        return view;
    }
}
