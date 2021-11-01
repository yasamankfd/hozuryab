import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hozuryab.R;

public class Controllers_grid_adapter extends BaseAdapter {
    LayoutInflater layoutInflater;
    String[] ids,names;
    Context ctx;
    public Controllers_grid_adapter(String[] ids,String[] names , Context ctx)
    {
        this.ctx = ctx;
        this.ids = ids;
        this.names = names;
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
            view = layoutInflater.inflate(R.layout.attendees_list_grid,null);
        }
        TextView id, name;

        id = view.findViewById(R.id.attendee_id_in_list);
        name = view.findViewById(R.id.attendee_name_in_list);

        id.setText(ids[i]);
        name.setText(names[i]);

        return view;
    }
}
