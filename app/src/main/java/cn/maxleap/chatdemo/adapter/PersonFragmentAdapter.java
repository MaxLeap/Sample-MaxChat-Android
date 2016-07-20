package cn.maxleap.chatdemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class PersonFragmentAdapter extends BaseAdapter {

    private final Context context;
    private final List<String> ls;

    public  PersonFragmentAdapter(Context context, List<String> ls){

        this.context = context;
        this.ls = ls;
    }
    @Override
    public int getCount() {
        return ls.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {

        return position==3?0:1;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        /*View view = View.inflate(context, R.layout.personfragment_item, null);
        TextView person_content = (TextView) view.findViewById(R.id.tv_person_content);
        TextView system_content = (TextView) view.findViewById(R.id.tv_system_content);*/

       // system_content.setText(ls.get(position));

        return null;
    }
}
