package com.example.lenovo.geographysharing.Details;

import android.app.Activity;
import android.content.Intent;

import com.example.lenovo.geographysharing.BaseClass.BaseActivity;
import com.example.lenovo.geographysharing.R;
import com.hejunlin.superindicatorlibray.CircleIndicator;
import com.hejunlin.superindicatorlibray.LoopViewPager;

public class AlbumDetailActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_album_detail;
    }

    @Override
    protected void initView() {
        //轮播图以及小圆点
        LoopViewPager viewPager = bindViewId(R.id.details_viewpager);
        CircleIndicator circleIndicator = bindViewId(R.id.details_indicator);
        viewPager.setAdapter(new DetailsPicAdapter(this));
        viewPager.setLooperPic(true);
        circleIndicator.setViewPager(viewPager);
//        Equipment equipment = new Equipment();
//        String titleName = equipment.getEquipName();
        setSupportActionBar();//表示当前页面支持ActionBar
        setSupportArrowActionBar(true);
        setTitle("详情页面");
    }

    @Override
    protected void initData() {

    }
    public static void launchAlbumDetailActivity(Activity activity){
        Intent intent = new Intent(activity,AlbumDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        activity.startActivity(intent);
    }
}
