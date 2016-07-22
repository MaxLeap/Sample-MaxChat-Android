package cn.maxleap.chatdemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.maxleap.chatdemo.R;
import cn.maxleap.chatdemo.bean.RecentChatBean;
import de.hdodenhof.circleimageview.CircleImageView;

public class RecentFragmentAdapter extends BaseAdapter {


    private final Context context;
    private final List<RecentChatBean> ls;

    public  RecentFragmentAdapter(Context context, List<RecentChatBean> ls){

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

            View view = View.inflate(context, R.layout.fragment_recenttalk_listview_item, null);

        CircleImageView mIcon = (CircleImageView) view.findViewById(R.id.person_icon);
        TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_name.setText(ls.get(position).username);
        return view;
    }
}
