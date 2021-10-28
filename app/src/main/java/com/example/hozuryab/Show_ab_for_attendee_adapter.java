package com.example.hozuryab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Show_ab_for_attendee_adapter extends BaseAdapter {
    String[] dates , statuses;
    Context ctx;
    LayoutInflater layoutInflater;
    public Show_ab_for_attendee_adapter(String[] dates ,String[] statuses, Context ctx)
    {
        this.ctx = ctx;
        this.dates = dates;
        this.statuses = statuses;
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
            view = layoutInflater.inflate(R.layout.abpresence_for_attendee_grid,null);
        }
        TextView date,status;

        date = view.findViewById(R.id.session_date_in_show_checklist);
        status = view.findViewById(R.id.session_status_in_show_checklist);
        date.setText(dates[i]);

        if(statuses[i].contains("p"))
        {
            status.setText("حاضر");
        }else status.setText("غایب");

        return view;
    }
}
