package cn.maxleap.chatdemo.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.maxleap.MLHelpCenter;

import java.util.ArrayList;
import java.util.List;

import cn.maxleap.chatdemo.R;
import cn.maxleap.chatdemo.adapter.SettingFragmentAdapter;

public class SettingFragment extends BaseFragment {

    private ListView mListView;
    private List<String> ls = new ArrayList<>();

    @Override
    public View initView() {
        view = View.inflate(getActivity(), R.layout.fragment_setting, null);
        mListView = (ListView) view.findViewById(R.id.listview);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        MLHelpCenter.openFaqs(getActivity());
                        break;
                    case 1:
                         MLHelpCenter.openConversation(getActivity());
                        break;
                }
            }
        });
        return this.view;
    }

    @Override
    protected void initData() {
        ls.clear();
        ls.add("FAQ");
        ls.add("用户反馈");
        mListView.setAdapter(new SettingFragmentAdapter(getActivity(), ls));
    }
}
