package com.example.hozuryab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;

public class Abpresence_grid_adapter extends BaseAdapter {
    Context ctx ;
    String[] names , ids , statuses;
    LayoutInflater layoutInflater;
    public Abpresence_grid_adapter(String[] ids,String[] names , String[] statuses, Context ctx)
    {
        this.ids = ids;
        this.statuses = statuses;
        this.names = names;
        this.ctx = ctx;
    }
    @Override
    public int getCount() {
        return ids.length;
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
            view = layoutInflater.inflate(R.layout.abpresent_grid,null);
        }
        TextView name,id;
        Switch status = view.findViewById(R.id.status);
        name = view.findViewById(R.id.name_in_abpresence);
        id = view.findViewById(R.id.id_in_abpresence);
        name.setText(names[i]);
        id.setText(ids[i]);
        if(statuses[i].equals("p"))
        {
            status.setChecked(true);
        }else status.setChecked(false);
        status.setOnClickListener(view1 -> {

        });

        return view;
    }
}
