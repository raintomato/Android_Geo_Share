package com.example.lenovo.geographysharing.EntityClass;

import android.content.Context;

import com.example.lenovo.geographysharing.R;

import java.io.Serializable;

/**
 * Created by lenovo on 2017/12/30.
 */

public class Issue implements Serializable {
    public static final int IDEZHI = 1;//地质类
    public static final int IWUTAN = 2;//物探类
    public static final int IHUATAN = 3;//化探类
    public static final int IZUANTAN = 4;//钻探类
    public static final int IDIZHISHIYAN = 5;//地质实验类
    public static final int IRENYUAN = 6;//软件类
    public static final int  IRUANJIAN= 7;//人员类
    public static final int IQITA = 8;//其他类
    public static final int IMEIYOU = 9;//没有
    public static final int IMAX_COUNT = 9;//频道数


    private int issueId;
    private String issueName;
    private Context mContext;

    public Issue(int id, Context context) {
        issueId = id;
        mContext=context;
        switch (issueId){
            case IDEZHI:
                issueName = mContext.getResources().getString(R.string.channel_series);
                break;
            case IWUTAN:
                issueName = mContext.getResources().getString(R.string.channel_movie);
                break;
            case IHUATAN:
                issueName = mContext.getResources().getString(R.string.channel_comic);
                break;
            case IZUANTAN:
                issueName = mContext.getResources().getString(R.string.channel_documentary);
                break;
            case IDIZHISHIYAN:
                issueName = mContext.getResources().getString(R.string.channel_music);
                break;
            case IRENYUAN:
                issueName = mContext.getResources().getString(R.string.channel_variety);
                break;
            case IRUANJIAN:
                issueName = mContext.getResources().getString(R.string.channel_live);
                break;
            case IQITA:
                issueName = mContext.getResources().getString(R.string.channel_others);
                break;
            case IMEIYOU:
                issueName = mContext.getResources().getString(R.string.channel_mycollection);
                break;
        }
    }

    public int getissueId() {
        return issueId;
    }

    public String getissueName() {
        return issueName;
    }




}
