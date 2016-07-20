package cn.maxleap.chatdemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.maxleap.chatdemo.R;
import cn.maxleap.chatdemo.bean.ChatMessageBean;

public class ChatUiAdapter extends BaseAdapter {


    public final static int OTHER = 1;
    public final static int ME    = 0;
    private final Context               context;
    private final List<ChatMessageBean> ls;


    public ChatUiAdapter(Context context, List<ChatMessageBean> ls) {

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

        if (ls.get(position).isme) {
            return ME;
        } else {
            return OTHER;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);

              ViewHolder1 holder1=null;
              ViewHolder2 holder2=null;

        if(convertView==null){
            switch (type){
                case ME:
                    convertView=View.inflate(context, R.layout.item_me,null);
                    holder1=new ViewHolder1();
                    holder1.imageView= (ImageView) convertView.findViewById(R.id.chatlist_image_me);
                    holder1.textView= (TextView) convertView.findViewById(R.id.chatlist_text_me);
                     convertView.setTag(holder1);
                    break;
                case OTHER:

                    convertView=View.inflate(context,R.layout.item_he,null);
                    holder2=new ViewHolder2();
                    holder2.imageView= (ImageView) convertView.findViewById(R.id.chatlist_image_other);
                    holder2.textView= (TextView) convertView.findViewById(R.id.chatlist_text_other);
                    convertView.setTag(holder2);
                    break;
            }
        } else{
            switch (type){
                case  ME:
                    holder1= (ViewHolder1) convertView.getTag();
                    break;
                case  OTHER:
                    holder2= (ViewHolder2) convertView.getTag();
                    break;
            }
        }

        switch (type){
            case  ME:
                holder1.textView.setText(ls.get(position).text);
                break;
            case OTHER:
                holder2.textView.setText(ls.get(position).text);
                break;
        }

        return convertView;
    }

    class  ViewHolder1{
        public ImageView imageView;
        public TextView textView;
    }

    class  ViewHolder2{
        public ImageView imageView;
        public TextView textView;
    }

}
