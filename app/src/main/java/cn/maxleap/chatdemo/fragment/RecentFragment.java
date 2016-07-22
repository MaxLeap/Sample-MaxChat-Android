package cn.maxleap.chatdemo.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.maxleap.chatdemo.R;
import cn.maxleap.chatdemo.activity.ChatUiActivity2;
import cn.maxleap.chatdemo.adapter.RecentFragmentAdapter;
import cn.maxleap.chatdemo.bean.RecentChatBean;
import cn.maxleap.chatdemo.event.LogOutEvent;
import cn.maxleap.chatdemo.event.RecentTalkEvent;

public class RecentFragment extends  BaseFragment {

    private ListView mListView;
    private List<RecentChatBean> ls=new ArrayList<>();
    private RecentFragmentAdapter recentFragmentAdapter;

    @Override
    public View initView() {
        view = View.inflate(getActivity(), R.layout.fragment_recenttalk, null);
        mListView = (ListView) view.findViewById(R.id.listview);
        EventBus.getDefault().register(this);
        recentFragmentAdapter = new RecentFragmentAdapter(getActivity(), ls);
        mListView.setAdapter(recentFragmentAdapter);


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ChatUiActivity2.class);
                intent.putExtra("name", ls.get(position).username);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    protected void initData() {

    }

    private  boolean isFirst=true;

      @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void  receiveMessageFromContactsFragmnet(RecentTalkEvent event){

          if(isFirst){

              RecentChatBean recentChatBean = new RecentChatBean(event.bitmap, event.name);
              ls.add(recentChatBean);
              isFirst=false;
          } else{
              for (RecentChatBean bean:ls){
                  if(!bean.username.equals(event.name)){
                      RecentChatBean bean1 = new RecentChatBean(event.bitmap, event.name);
                      ls.add(bean1);
                  }
              }
          }

         recentFragmentAdapter.notifyDataSetChanged();
      }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void receiveLogoutMessage(LogOutEvent event){
        mListView.setVisibility(View.GONE);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
