package cn.maxleap.chatdemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.maxleap.chatdemo.R;
import cn.maxleap.chatdemo.bean.MyFriendCircleBean;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyFriendCircleAdapter extends BaseAdapter {

    private final Context context;
    private final List<MyFriendCircleBean> ls;

    private static final int TYPE_1     = 0;
    private static final int TYPE_2     = 1;
    private static final int TYPE_3     = 2;
    private static final int TYPE_COUNT = 3;


    public MyFriendCircleAdapter(Context context, List<MyFriendCircleBean> ls){

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
    public int getItemViewType(int position) {
        int p = position % 6;
        if (p == 0) {
            return TYPE_1;
        } else if (p < 3) {
            return TYPE_2;
        } else if (p < 6) {
            return TYPE_3;
        } else {
            return TYPE_1;
        }
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }



    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder1 holder1 = null;
        ViewHolder2 holder2 = null;
        ViewHolder3 holder3 = null;

        int type = getItemViewType(position);

        if (convertView == null) {
            switch (type) {
                case TYPE_1:
                    convertView = View.inflate(context, R.layout.myfriendcircle_item1, null);
                    holder1 = new ViewHolder1();

                    holder1.icon = (CircleImageView) convertView.findViewById(R.id.civ_icon);
                    holder1.username = (TextView) convertView.findViewById(R.id.tv_name);
                    holder1.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
                    holder1.tv_delete = (TextView) convertView.findViewById(R.id.tv_delete);
                    holder1.tv_shuoshuo= (TextView) convertView.findViewById(R.id.tv_shuoshuo);
                    convertView.setTag(holder1);
                    break;
                case TYPE_2:
                    convertView = View.inflate(context, R.layout.myfriendcircle_item2, null);
                    holder2 = new ViewHolder2();

                    holder2.icon = (CircleImageView) convertView.findViewById(R.id.civ_icon);
                    holder2.username = (TextView) convertView.findViewById(R.id.tv_name);
                    holder2.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
                    holder2.tv_shuoshuo = (TextView) convertView.findViewById(R.id.tv_shuoshuo);
                    holder2.iv_content = (ImageView) convertView.findViewById(R.id.iv_1);
                    holder2.tv_delete= (TextView) convertView.findViewById(R.id.tv_delete);
                    convertView.setTag(holder2);

                    break;
                case TYPE_3:
                    convertView = View.inflate(context, R.layout.myfriendcircle_item3, null);
                    holder3 = new ViewHolder3();

                    holder3.icon = (CircleImageView) convertView.findViewById(R.id.civ_icon);
                    holder3.username = (TextView) convertView.findViewById(R.id.tv_name);
                    holder3.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
                    holder3.tv_delete = (TextView) convertView.findViewById(R.id.tv_delete);
                    holder3.iv_1= (ImageView) convertView.findViewById(R.id.iv_1);
                    convertView.setTag(holder3);

                    break;
            }
        } else {
            switch (type) {
                case TYPE_1:
                    holder1 = (ViewHolder1) convertView.getTag();
                    break;
                case TYPE_2:
                    holder2 = (ViewHolder2) convertView.getTag();
                    break;
                case TYPE_3:
                    holder3 = (ViewHolder3) convertView.getTag();
                    break;

            }
        }

        switch (type) {
            case TYPE_1:
                // TODO: 16/7/18
                break;
            case TYPE_2:
                // TODO: 16/7/18
                break;
            case TYPE_3:
                // TODO: 16/7/18
                break;
        }




        return convertView;
    }



    class ViewHolder1 {
        public CircleImageView icon;
        public TextView        username;
        public TextView        tv_time;
        public TextView       tv_delete;
        public TextView tv_shuoshuo;
    }

    class ViewHolder2 {

        public CircleImageView icon;
        public TextView        username;
        public TextView        tv_time;
        public ImageView       iv_content;
        public TextView        tv_shuoshuo;
        public TextView        tv_delete;
    }

    class ViewHolder3 {

        public CircleImageView icon;
        public TextView        username;
        public TextView        tv_time;
        public TextView        tv_delete;
        public ImageView       iv_1;
    }

}
