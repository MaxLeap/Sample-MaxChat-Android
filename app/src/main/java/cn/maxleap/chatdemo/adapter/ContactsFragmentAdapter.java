package cn.maxleap.chatdemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.maxleap.chatdemo.activity.ContactsDialogActivity;
import cn.maxleap.chatdemo.R;
import cn.maxleap.chatdemo.bean.ContactsBean;
import de.hdodenhof.circleimageview.CircleImageView;

public class ContactsFragmentAdapter extends BaseAdapter {

    private final Context            context;
    private final List<ContactsBean> ls;

    public ContactsFragmentAdapter(Context context, List<ContactsBean> ls){

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
            convertView = View.inflate(context, R.layout.contactsfragment_item_contacts, null);
             holder.person_icon= (CircleImageView) convertView.findViewById(R.id.person_icon);
            holder.tv_username= (TextView) convertView.findViewById(R.id.tv_username);
            holder.delete_person= (ImageView) convertView.findViewById(R.id.iv_delete_person);
            convertView.setTag(holder);
        }else{
             holder = (ViewHolder) convertView.getTag();
        }

         holder.tv_username.setText(ls.get(position).username);
          holder.delete_person.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  context.startActivity(new Intent(context, ContactsDialogActivity.class));
              }
          });

        return convertView;
    }

    class  ViewHolder {
        public CircleImageView  person_icon;
        public TextView tv_username;
        public ImageView  delete_person;
    }

}
