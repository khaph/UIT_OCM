package com.uit.khaph.uitocm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class FragmentAdapter extends FragmentStatePagerAdapter {

    private String listTab[]={"","","",""};

    private FragmentUserDetail fragmentUserDetail;

    private FragmentClassMeeting fragmentClassMeeting;

    private FragmentNotification fragmentNotification;

    private FragmentStatus fragmentStatus;

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

        //Notification

        fragmentNotification = new FragmentNotification();
        Bundle notificationBundle = new Bundle();
        notificationBundle.putString("className",className);
        notificationBundle.putString("userName",userName);
        notificationBundle.putString("pictureUrl",pictureUrl);
        fragmentNotification.setArguments(notificationBundle);

        //Meeting now
        fragmentStatus = new FragmentStatus();
        Bundle meetingNowBundle = new Bundle();
        meetingNowBundle.putString("className",className);
        meetingNowBundle.putString("userName",userName);
        meetingNowBundle.putString("pictureUrl",pictureUrl);
        meetingNowBundle.putString("meetingName","1");
        fragmentStatus.setArguments(meetingNowBundle);


    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0){
            return fragmentClassMeeting;
        }else if(i==1){
            return fragmentStatus;
        }else if(i==2){
            return fragmentNotification;
        }else if(i==3){
            return fragmentUserDetail;
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
