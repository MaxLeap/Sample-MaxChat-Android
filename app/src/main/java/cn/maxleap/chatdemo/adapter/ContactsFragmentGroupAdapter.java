package cn.maxleap.chatdemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.maxleap.chatdemo.activity.GroupDialogActivity;
import cn.maxleap.chatdemo.R;
import cn.maxleap.chatdemo.bean.GroupBean;

public class ContactsFragmentGroupAdapter extends BaseAdapter {

    private final Context         context;
    private final List<GroupBean> ls;

    public ContactsFragmentGroupAdapter(Context context, List<GroupBean> ls){

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

        ViewHolder holder=null;

        if(convertView==null){
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.contactsfragment_item_group, null);
            holder.tv_username= (TextView) convertView.findViewById(R.id.tv_username);
            holder.delete_person= (ImageView) convertView.findViewById(R.id.iv_delete_person);
            convertView.setTag(holder);
        }else{
             holder = (ViewHolder) convertView.getTag();
        }

         holder.tv_username.setText(ls.get(position).name);
         holder.delete_person.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                  context.startActivity(new Intent(context, GroupDialogActivity.class));
             }
         });

        return convertView;
    }

    class  ViewHolder {
        public TextView tv_username;
        public ImageView  delete_person;
    }

}
