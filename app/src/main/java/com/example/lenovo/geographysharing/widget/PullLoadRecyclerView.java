package com.example.lenovo.geographysharing.widget;

import android.animation.Animator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.lenovo.geographysharing.R;
import com.example.lenovo.geographysharing.others.DividerItemDecoration;

/**
 * Created by lenovo on 2017/12/31.
 */

public class PullLoadRecyclerView extends LinearLayout {

    private Context mContext;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private boolean mIsRefresh = false;
    private boolean mIsLoadMore = false;
    private RecyclerView mRecyclerView;
    private View mFootView;
    private AnimationDrawable mAnimationDrawable;
    private OnPullLoadMoreListener mOnPullLoadMoreListener;

    public PullLoadRecyclerView(Context context) {
        super(context);
        initView(context);
    }

    public PullLoadRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public PullLoadRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        View view = LayoutInflater.from(mContext).inflate(R.layout.pull_loadmore_layout, null);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        //设置刷新时，颜色渐变。
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_dark, android.R.color.holo_blue_dark, android.R.color.holo_orange_dark);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayoutOnRefresh());

        //处理Recyclerview
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        //设置固定大小
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return mIsRefresh || mIsLoadMore;
            }
        });
        //隐藏滚动条
        mRecyclerView.setVerticalScrollBarEnabled(false);
        mRecyclerView.addOnScrollListener(new RecyclerViewOnScroll());
        mFootView = view.findViewById(R.id.foot_view);
        ImageView imageview = (ImageView) mFootView.findViewById(R.id.iv_load_image);
        imageview.setBackgroundResource(R.drawable.imooc_loading);
        mAnimationDrawable = (AnimationDrawable) imageview.getBackground();
//        TextView textview = (TextView) mFootView.findViewById(R.id.iv_load_text);
        mFootView.setVisibility(View.GONE);
        //此时的view包含mSwipeRefreshLayout，mRecyclerView，mFootView
        this.addView(view);
    }
    //外部可以设置Recyclerview的列数
    public void setGridLayout(int sapnCount){
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayout.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        //设置Recyclerview的分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
    }
    class SwipeRefreshLayoutOnRefresh implements SwipeRefreshLayout.OnRefreshListener {

        public void setAdapter(RecyclerView.Adapter adapter){
            if (adapter!=null){
                mRecyclerView.setAdapter(adapter);
            }
        }
        @Override
        public void onRefresh() {
            if (!mIsRefresh) {
                mIsRefresh = true;
                refreshData();
            }
        }
    }

    class RecyclerViewOnScroll extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int firstItem = 0;
            int lastItem = 0;
            RecyclerView.LayoutManager manger = recyclerView.getLayoutManager();
            int totalCount = manger.getItemCount();

            //这里是网格的，日后记得修改，根据自己的

            if (manger instanceof GridLayoutManager) {
                GridLayoutManager gridLayoutManager = (GridLayoutManager) manger;
                //第一个完全可见
                firstItem = gridLayoutManager.findFirstCompletelyVisibleItemPosition();
                //最后一个完全可见
                lastItem = gridLayoutManager.findLastCompletelyVisibleItemPosition();
                if (firstItem == 0 || firstItem == RecyclerView.NO_POSITION) {
                    lastItem = gridLayoutManager.findLastVisibleItemPosition();
                }
            }
            //什么触发上拉加载更多
            if (mSwipeRefreshLayout.isEnabled()) {
                mSwipeRefreshLayout.setEnabled(true);
            } else {
                mSwipeRefreshLayout.setEnabled(false);
            }
            //加载更多是false
            //总数等于最后一个
            //刷新布局Swipe可以使用
            //不是出于下拉刷新状态
            //偏移量dy大于零
            if (!mIsLoadMore && totalCount-1== lastItem && (dx > 0 || dy > 0) && mSwipeRefreshLayout.isEnabled()) {
                mIsLoadMore = true;
                loadMoreData();
            }
        }
    }

    private void loadMoreData() {
        if (mOnPullLoadMoreListener!=null)
        {
            mOnPullLoadMoreListener.loadMore();
            mFootView.animate().translationY(mFootView.getHeight()).setInterpolator(new AccelerateDecelerateInterpolator())
                    .setDuration(300).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    mFootView.setVisibility(View.VISIBLE);
                    mOnPullLoadMoreListener.loadMore();
                    mAnimationDrawable.start();

                }

                @Override
                public void onAnimationEnd(Animator animator) {

                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            }).start();
            invalidate();
        }
    }
    //设置刷新完成
    public void setRefreshCompletely(){
        mIsRefresh=false;
        setRefreshing(false);
    }
//设置是否正在刷新
    private void setRefreshing(final boolean isRefreshing) {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(isRefreshing);
            }
        });
    }

    public void setLoadMoreCompleted(){
        mIsLoadMore = false;
        mIsRefresh = false;
        setRefreshing(false);
        mFootView.animate().translationY(mFootView.getHeight()).setInterpolator(new AccelerateDecelerateInterpolator())
                .setDuration(300).start();
    }

    private void refreshData() {
        if (mOnPullLoadMoreListener!=null){
            mOnPullLoadMoreListener.refresh();
        }
        //TODO
    }
    public void setAdapter(RecyclerView.Adapter adapter) {
        if (adapter != null) {
            mRecyclerView.setAdapter(adapter);
        }
    }

    public interface OnPullLoadMoreListener{
        void refresh();
        void loadMore();
    }
    public void setOnPullLoadMoreListener(OnPullLoadMoreListener listener){
        mOnPullLoadMoreListener = listener;
    }
}
