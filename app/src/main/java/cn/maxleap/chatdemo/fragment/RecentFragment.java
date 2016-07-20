package cn.maxleap.chatdemo.fragment;

import android.view.View;
import android.widget.ListView;

import cn.maxleap.chatdemo.R;

public class RecentFragment extends  BaseFragment {

    private ListView mListView;

    @Override
    public View initView() {
        view = View.inflate(getActivity(), R.layout.fragment_recenttalk, null);
        mListView = (ListView) view.findViewById(R.id.listview);
        return view;
    }

    @Override
    protected void initData() {

    }
}
