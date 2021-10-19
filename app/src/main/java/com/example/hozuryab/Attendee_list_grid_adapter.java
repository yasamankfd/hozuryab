package com.example.hozuryab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Attendee_list_grid_adapter extends BaseAdapter {
    Context ctx ;
    String[] names , ids;
    LayoutInflater layoutInflater;

    public Attendee_list_grid_adapter(Context ctx, String[] names,String[] ids) {
        this.ctx = ctx;
        this.names = names;
        this.ids = ids;
    }

    @Override
    public int getCount() {
        return names.length;
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
            view = layoutInflater.inflate(R.layout.attendees_list_grid,null);
        }
        TextView name,id;
        name = view.findViewById(R.id.attendee_name_in_list);
        id = view.findViewById(R.id.attendee_id_in_list);
        name.setText(names[i]);
        id.setText(ids[i]);


        return view;
    }
}
