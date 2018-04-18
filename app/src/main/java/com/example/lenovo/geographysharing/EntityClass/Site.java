package com.example.lenovo.geographysharing.EntityClass;

import android.content.Context;

import com.example.lenovo.geographysharing.R;
/**
 * Created by lenovo on 2017/12/30.
 */

public class Site {
    public static final int LETV = 1;
    public static final int SOUHU = 2;

    public static final int MAX_SIZE = 2;


    private int siteId;
    private String siteName;
    private Context mContext;

    public void Channel(int id, Context context) {
        siteId = id;
        mContext=context;
        switch (siteId){
            case SOUHU:
                siteName = mContext.getResources().getString(R.string.site_sohu);
                break;
            case LETV:
                siteName = mContext.getResources().getString(R.string.site_letv);
                break;

        }
    }

    public int getChannelId() {
        return siteId;
    }

    public String getChannelName() {
        return siteName;
    }



}
