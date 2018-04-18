package com.example.lenovo.geographysharing.Details;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;

import com.example.lenovo.geographysharing.BaseClass.BaseActivity;
import com.example.lenovo.geographysharing.BaseClass.BaseFragment;
import com.example.lenovo.geographysharing.EntityClass.Channel;
import com.example.lenovo.geographysharing.R;
import com.example.lenovo.geographysharing.others.FragmentManagerWrapper;

/**
 * Created by lenovo on 2017/12/30.
 */

public class DetaiListActivity extends BaseActivity {
    private FragmentManager mFragmentManager;
    private BaseFragment mCurrentFragment;
    private static final String CHANNEL_ID = "channid";
    private int mChannId;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_detailist;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        if (intent != null) {
            mChannId = intent.getIntExtra(CHANNEL_ID, 0);
        }
        Channel channel = new Channel(mChannId, this);
        String titleName = channel.getChannelName();

        setSupportActionBar();//表示当前页面支持ActionBar
        setSupportArrowActionBar(true);
        setTitle(titleName);

        initFragemnt();
    }

    //处理左上角返回箭头
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initData() {

    }
    //初始化Fragment
    private void initFragemnt() {

        mFragmentManager = getSupportFragmentManager();
        //初始化传入的homefragment
        mCurrentFragment = FragmentManagerWrapper.getInstance().createFragment(DetailListFragment.class,true);
        //fragmentmanager 事务的回滚
        mFragmentManager.beginTransaction().add(R.id.fl_details_content,mCurrentFragment).commit();
    }



    public static void launchDetaiListActivity(Context context, int channelId) {
        Intent intent = new Intent(context, DetaiListActivity.class);
        //防止多次实例调用
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(CHANNEL_ID, channelId);
        context.startActivity(intent);
    }
}
