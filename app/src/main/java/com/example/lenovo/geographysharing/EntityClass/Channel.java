package com.example.lenovo.geographysharing.EntityClass;

import android.content.Context;

import com.example.lenovo.geographysharing.R;

import java.io.Serializable;

/**
 * Created by lenovo on 2017/12/30.
 */

public class Channel implements Serializable {
    public static final int DEZHI = 1;//地质类
    public static final int WUTAN = 2;//物探类
    public static final int HUATAN = 3;//化探类
    public static final int ZUANTAN = 4;//钻探类
    public static final int DIZHISHIYAN = 5;//地质实验类
    public static final int RENYUAN = 6;//软件类
    public static final int  RUANJIAN= 7;//人员类
    public static final int QITA = 8;//其他类
    public static final int MEIYOU = 9;//没有
    public static final int MAX_COUNT = 9;//频道数


    private int channelId;
    private String channelName;
    private Context mContext;

    public Channel(int id, Context context) {
        channelId = id;
        mContext=context;
        switch (channelId){
            case DEZHI:
                channelName = mContext.getResources().getString(R.string.channel_series);
                break;
            case WUTAN:
                channelName = mContext.getResources().getString(R.string.channel_movie);
                break;
            case HUATAN:
                channelName = mContext.getResources().getString(R.string.channel_comic);
                break;
            case ZUANTAN:
                channelName = mContext.getResources().getString(R.string.channel_documentary);
                break;
            case DIZHISHIYAN:
                channelName = mContext.getResources().getString(R.string.channel_music);
                break;
            case RENYUAN:
                channelName = mContext.getResources().getString(R.string.channel_variety);
                break;
            case RUANJIAN:
                channelName = mContext.getResources().getString(R.string.channel_live);
                break;
            case QITA:
                channelName = mContext.getResources().getString(R.string.channel_favorite);
                break;
            case MEIYOU:
                channelName = mContext.getResources().getString(R.string.channel_history);
                break;
        }
    }

    public int getChannelId() {
        return channelId;
    }

    public String getChannelName() {
        return channelName;
    }




}
