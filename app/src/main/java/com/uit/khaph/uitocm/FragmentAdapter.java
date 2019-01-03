package com.uit.khaph.uitocm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class FragmentAdapter extends FragmentStatePagerAdapter {

    private String listTab[]={"Meeting","User"};

    private FragmentUserDetail fragmentUserDetail;

    private FragmentClassMeeting fragmentClassMeeting;

    public FragmentAdapter(FragmentManager fm, @Nullable String userName,@Nullable String className,@Nullable String pictureUrl){
        super(fm);

        //User Detail
        fragmentUserDetail = new FragmentUserDetail();
        Bundle userDetailBundle = new Bundle();
        userDetailBundle.putString("userName",userName);
        userDetailBundle.putString("pictureUrl",pictureUrl);
        fragmentUserDetail.setArguments(userDetailBundle);

        //Class Meeting

        fragmentClassMeeting = new FragmentClassMeeting();
        Bundle classMeetingBundle = new Bundle();
        classMeetingBundle.putString("className",className);
        classMeetingBundle.putString("userName",userName);
        classMeetingBundle.putString("pictureUrl",pictureUrl);
        fragmentClassMeeting.setArguments(classMeetingBundle);
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 1){
            return fragmentUserDetail;
        }else if(i==0){
            return fragmentClassMeeting;
        }
        return null;
    }

    @Override
    public int getCount() {
        return listTab.length;
    }

    @Override
    public CharSequence getPageTitle(int i){
        return listTab[i];
    }
}
