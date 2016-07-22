package cn.maxleap.chatdemo.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.maxleap.chatdemo.R;
import cn.maxleap.chatdemo.activity.MyFriendCircleActivity;
import cn.maxleap.chatdemo.activity.MyShuoShuoActivity;
import cn.maxleap.chatdemo.activity.SquareActivity;
import cn.maxleap.chatdemo.adapter.FriendCircleFragmentAdapter;
import cn.maxleap.chatdemo.event.FriendCircleEvent;
import cn.maxleap.chatdemo.global.Contants;
import cn.maxleap.chatdemo.utils.PrefUtils;

public class FriendCircleFragment extends BaseFragment {

    private List<String> ls = new ArrayList<>();
    private ListView mListView;
    private LinearLayout ll_fragment_friend_circle;

    @Override
    public View initView() {

        view = View.inflate(getActivity(), R.layout.fragment_friend_circle, null);
        mListView = (ListView) view.findViewById(R.id.listview);
        ll_fragment_friend_circle = (LinearLayout) view.findViewById(R.id.ll_fragment_friend_circle);

       // MLUser currentUser = MLUser.getCurrentUser();
        if(!TextUtils.isEmpty(PrefUtils.getString(getActivity(), Contants.USERNAME,""))){
            ll_fragment_friend_circle.setVisibility(View.VISIBLE);
        }else{
            ll_fragment_friend_circle.setVisibility(View.GONE);
        }


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        startActivity(new Intent(getActivity(), SquareActivity.class));

                        break;
                    case 1:
                          startActivity(new Intent(getActivity(), MyFriendCircleActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getActivity(), MyShuoShuoActivity.class));
                        break;
                }
            }
        });


        EventBus.getDefault().register(this);

        return view;
    }

    @Override
    protected void initData() {
        ls.clear();
        ls.add("广场");
        ls.add("我的朋友圈");
        ls.add("我的说说");
        mListView.setAdapter(new FriendCircleFragmentAdapter(getActivity(), ls));
    }

    @Subscribe(threadMode= ThreadMode.MAIN,sticky = true)
    public void soos(FriendCircleEvent event){
          if(!TextUtils.isEmpty(PrefUtils.getString(getActivity(),Contants.USERNAME,""))){
              ll_fragment_friend_circle.setVisibility(View.VISIBLE);
          }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }
}
