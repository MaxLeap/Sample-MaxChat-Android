package cn.maxleap.chatdemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.maxleap.chatdemo.R;

public class FriendCircleFragmentAdapter extends BaseAdapter {

    private final Context context;
    private final List<String> ls;

    public  FriendCircleFragmentAdapter(Context context, List<String> ls){

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
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.settingfragment_item, null);
        TextView mContent = (TextView) view.findViewById(R.id.tv_content);
        mContent.setText(ls.get(position));
        return view;
    }
}
