package cn.maxleap.chatdemo.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import cn.maxleap.chatdemo.R;
import cn.maxleap.chatdemo.activity.ChatUiActivity2;
import cn.maxleap.chatdemo.adapter.ContactsFragmentAdapter;
import cn.maxleap.chatdemo.adapter.ContactsFragmentGroupAdapter;
import cn.maxleap.chatdemo.bean.ContactsBean;
import cn.maxleap.chatdemo.bean.GroupBean;
import cn.maxleap.chatdemo.event.LogOutEvent;
import cn.maxleap.chatdemo.event.RecentTalkEvent;
import cn.maxleap.chatdemo.event.ShowContactsEvent;
import cn.maxleap.chatdemo.event.ShowGroupEvent;
import cn.maxleap.chatdemo.global.Contants;
import cn.maxleap.chatdemo.utils.PrefUtils;

public class ContactsFragment extends BaseFragment {

    private ListView mListView_contacts;
    private ListView mListView_group;

    private ArrayList<ContactsBean> ls_contacts= new ArrayList<>();
    private ArrayList<GroupBean> ls_group = new ArrayList<>();
    private ImageView line;

    @Override
    public View initView() {

        view = View.inflate(getActivity(), R.layout.fragment_contacts, null);
        mListView_contacts = (ListView) view.findViewById(R.id.contacts_listview);
        mListView_group = (ListView) view.findViewById(R.id.group_listview);
        line = (ImageView) view.findViewById(R.id.iv_line);
        EventBus.getDefault().register(this);
        String sp_name= PrefUtils.getString(getActivity(), Contants.USERNAME, "");
        if(!TextUtils.isEmpty(sp_name)){
            mListView_contacts.setVisibility(View.VISIBLE);
            line.setVisibility(View.VISIBLE);
            mListView_group.setVisibility(View.GONE);
        }


        mListView_contacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ChatUiActivity2.class);
                intent.putExtra("name",ls_contacts.get(position).username);
                startActivity(intent);
                EventBus.getDefault().postSticky(new RecentTalkEvent(ls_contacts.get(position).username,null));

            }
        });

        mListView_group.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ChatUiActivity2.class);
                intent.putExtra("name",ls_group.get(position).name);
                startActivity(intent);

            }
        });

        return view;
    }

    @Override
    protected void initData() {
        ls_contacts.clear();
        ls_group.clear();
        for (int i = 0; i < 5; i++) {
            ContactsBean bean = new ContactsBean("123456");
            GroupBean groupBean = new GroupBean();
            groupBean.name="soso_haha";

            ls_group.add(groupBean);
            ls_contacts.add(bean);
        }

        mListView_contacts.setAdapter(new ContactsFragmentAdapter(getActivity(),ls_contacts));
        mListView_group.setAdapter(new ContactsFragmentGroupAdapter(getActivity(),ls_group));
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void showContactsView(ShowContactsEvent event){
        if(!TextUtils.isEmpty(PrefUtils.getString(getActivity(),Contants.USERNAME,""))){

            mListView_contacts.setVisibility(View.VISIBLE);
            line.setVisibility(View.VISIBLE);
        }
        mListView_group.setVisibility(View.GONE);
    }


    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void showContactsView(ShowGroupEvent event){
        mListView_contacts.setVisibility(View.GONE);
        if(!TextUtils.isEmpty(PrefUtils.getString(getActivity(),Contants.USERNAME,""))){
            mListView_group.setVisibility(View.VISIBLE);
            line.setVisibility(View.VISIBLE);
        } else{
            mListView_group.setVisibility(View.GONE);
        }
    }

     @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void receiveLogoutMessage(LogOutEvent event){
         System.out.println("接受到了吗？");
          mListView_group.setVisibility(View.GONE);
         mListView_contacts.setVisibility(View.GONE);
          line.setVisibility(View.GONE);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
