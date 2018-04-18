package com.example.lenovo.geographysharing.Details;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.lenovo.geographysharing.BaseClass.BaseFragment;
import com.example.lenovo.geographysharing.EntityClass.Equipment;
import com.example.lenovo.geographysharing.R;
import com.example.lenovo.geographysharing.widget.PullLoadRVAdapter;
import com.example.lenovo.geographysharing.widget.PullLoadRecyclerView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/12/30.
 */

public class DetailListFragment extends BaseFragment {
    private static final String CHANNEL_ID = "channelid";
    private static final String SITE_ID = "siteid";
    private Context mContext;
    private PullLoadRecyclerView mPullLoadRecyclerView;
    private android.widget.SearchView mSearchView;
    private PullLoadRVAdapter mAdapter;
    private Handler handler= new Handler(Looper.getMainLooper());//表示在主线程的意思
    private static final int REFRESH_DURATION = 1500;
    private static final int LOAD_MORE = 3000;
    public static List<Equipment> myEquipmentList = new ArrayList<>();

    public DetailListFragment() {
    }

    public static Fragment newInstance(int SiteId, int channId) {
        DetailListFragment detailListFragment = new DetailListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(CHANNEL_ID, channId);
        bundle.putInt(SITE_ID, SiteId);
        detailListFragment.setArguments(bundle);
        return detailListFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_detailist;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData();
        mAdapter = new PullLoadRVAdapter(myEquipmentList);
      }


    @Override
    protected void initView() {
        mSearchView = bindViewId(R.id.searchView);
        mSearchView.clearFocus();
        if (mSearchView != null) {
            try {        //--拿到字节码
                Class<?> argClass = mSearchView.getClass();
                //--指定某个私有属性,mSearchPlate是搜索框父布局的名字
                Field ownField = argClass.getDeclaredField("activity_search");
                //--暴力反射,只有暴力反射才能拿到私有属性
                ownField.setAccessible(true);
                View mView = (View) ownField.get(mSearchView);
                //--设置背景
                mView.setBackgroundColor(Color.TRANSPARENT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mPullLoadRecyclerView = bindViewId(R.id.pullloadRecyclerView);
        mPullLoadRecyclerView.setGridLayout(1);
        mPullLoadRecyclerView.setAdapter(mAdapter);
        mPullLoadRecyclerView.setOnPullLoadMoreListener(new OnPullLoadMoreListener());
        mAdapter.setOnItemClickListener(new PullLoadRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                AlbumDetailActivity.launchAlbumDetailActivity(getActivity());
            }
        });
    }

    private void reFreshData(){
        //// TODO: 2017/12/31 请求接口加载数据
    }
    private void loadData(){
        //// TODO: 2017/12/31 加载更多数据，刚进来的时候调用，上拉加载再次调用
    }

    class OnPullLoadMoreListener implements PullLoadRecyclerView.OnPullLoadMoreListener {

        @Override
        public void refresh() {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    reFreshData();
                    mPullLoadRecyclerView.setRefreshCompletely();

                }
            }, REFRESH_DURATION);
        }

        @Override
        public void loadMore() {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadData();
                    mPullLoadRecyclerView.setLoadMoreCompleted();

                }
            },LOAD_MORE);
        }
    }



    @Override
    protected void initData() {

        Equipment equipment1 = new Equipment("取样钻", "性能参数", "北京市海淀区学院路", "100元/天", R.drawable.head_image_1);
        myEquipmentList.add(equipment1);
        Equipment equipment2 = new Equipment("刻槽取样机", "性能参数", "北京市海淀区学院路", "200元/天", R.drawable.head_image_1);
        myEquipmentList.add(equipment2);
        Equipment equipment3 = new Equipment("岩芯切割机", "性能参数", "北京市海淀区学院路", "300元/天", R.drawable.head_image_1);
        myEquipmentList.add(equipment3);
        Equipment equipment11 = new Equipment("双目立体镜", "性能参数", "北京市海淀区学院路", "400元/天", R.drawable.head_image_1);
        myEquipmentList.add(equipment11);
        Equipment equipment21 = new Equipment("偏光显微镜", "性能参数", "北京市海淀区学院路", "500元/天", R.drawable.head_image_1);
        myEquipmentList.add(equipment21);
        Equipment equipment31 = new Equipment("反光显微镜", "性能参数", "北京市海淀区学院路", "600元/天", R.drawable.head_image_1);
        myEquipmentList.add(equipment31);
        Equipment equipment15 = new Equipment("显微照像设备", "性能参数", "北京市海淀区学院路", "700元/天", R.drawable.head_image_1);
        myEquipmentList.add(equipment15);
        Equipment equipment211 = new Equipment("手持GPS", "性能参数", "北京市海淀区学院路", "800元/天", R.drawable.head_image_1);
        myEquipmentList.add(equipment211);
        Equipment equipment311 = new Equipment("钻机", "四万转/秒", "北京市海淀区学院路", "900元/天", R.drawable.head_image_1);
        myEquipmentList.add(equipment311);
    }

}
