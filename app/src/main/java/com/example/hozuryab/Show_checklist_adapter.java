package com.example.hozuryab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hozuryab.R;

public class Show_checklist_adapter extends BaseAdapter {
    String[] ids , names , statuses;
    Context ctx;
    LayoutInflater layoutInflater;
    public Show_checklist_adapter(String[] ids , String[] names ,String[] statuses, Context ctx)
    {
        this.ctx = ctx;
        this.ids = ids;
        this.names = names;
        this.statuses = statuses;
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
            view = layoutInflater.inflate(R.layout.attendee_in_checklist_grid,null);
        }
        TextView name,id,status;
        name = view.findViewById(R.id.attendee_name_in_show_checklist);
        id = view.findViewById(R.id.attendee_id_in_show_checklist);
        status = view.findViewById(R.id.attendee_status_in_show_checklist);
        name.setText(names[i]);
        id.setText(ids[i]);
        if(statuses[i].equals("p"))
        {
            status.setText("حاضر");
        }else status.setText("غایب");

        return view;
    }
}
