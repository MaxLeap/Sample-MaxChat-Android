package cn.maxleap.chatdemo.fragment;

import android.view.View;

import cn.maxleap.chatdemo.R;

public class RecentFragment extends  BaseFragment {
    @Override
    public View initView() {
        view = View.inflate(getActivity(), R.layout.fragment_recenttalk, null);
        return view;
    }

    @Override
    protected void initData() {

    }
}
