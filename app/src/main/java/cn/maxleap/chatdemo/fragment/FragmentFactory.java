package cn.maxleap.chatdemo.fragment;

import java.util.HashMap;

public class FragmentFactory {

    private static HashMap<Integer, BaseFragment> saveFragment = new HashMap<>();

    public static BaseFragment getFragment(int position) {
        BaseFragment baseFragment = saveFragment.get(position);
        if (baseFragment == null) {
            switch (position) {
                case 0:
                    baseFragment = new PersonFragment();
                    break;
                case 1:
                    baseFragment = new ContactsFragment();
                    break;
                case 2:
                    baseFragment = new RecentFragment();
                    break;
                case 3:
                    baseFragment = new FriendCircleFragment();
                    break;
                case 4:
                    baseFragment = new SettingFragment();
                    break;

                default:
                    break;
            }

            saveFragment.put(position,baseFragment);
        }
        return  baseFragment;
    }
}
