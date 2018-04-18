package com.example.lenovo.geographysharing.Home;

import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.MenuItem;

import com.example.lenovo.geographysharing.BaseClass.BaseActivity;
import com.example.lenovo.geographysharing.BaseClass.BaseFragment;
import com.example.lenovo.geographysharing.R;
import com.example.lenovo.geographysharing.Welcom.LoginActivity;
import com.example.lenovo.geographysharing.others.FragmentManagerWrapper;

public class HomeActivity extends BaseActivity {
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private MenuItem mPreItem;
    private FragmentManager mFragmentManager;
    private BaseFragment mCurrentFragment;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        setSupportActionBar();
        setActionBarIcon(R.drawable.ic_drawer_home);
        setTitle("首页");

        mDrawerLayout = bindViewId(R.id.drawer_layout);
        mNavigationView = bindViewId(R.id.navigation_view);

        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolBar,R.string.drawer_open,R.string.drawer_close);
        mActionBarDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);


        mPreItem = mNavigationView.getMenu().getItem(0);
        mPreItem.setChecked(true);
        initFragemnt();
        //设置监听
        handleNavigationView();

    }
//初始化Fragment
    private void initFragemnt() {

        mFragmentManager = getSupportFragmentManager();
        //初始化传入的homefragment
        mCurrentFragment = FragmentManagerWrapper.getInstance().createFragment(HomeFragment.class,true);
        //fragmentmanager 事务的回滚
        mFragmentManager.beginTransaction().add(R.id.fl_main_content,mCurrentFragment).commit();
    }

    private void handleNavigationView() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if(mPreItem!=null){
                    mPreItem.setCheckable(false);
                }
                switch (item.getItemId()){
                    case R.id.navigation_item_video:
                        switchFragment(HomeFragment.class);
                        mToolBar.setTitle("首页");
                        break;
                    case R.id.navigation_item_blog:
                        switchFragment(MyIssueFragment.class);
                        mToolBar.setTitle("我的发布");
                        break;
                    case R.id.navigation_item_about:
                        switchFragment(AboutFragment.class);
                        mToolBar.setTitle("关于我们");
                        break;
                    case R.id.navigation_item_issue:
                        switchFragment(IssueFragment.class);
                        mToolBar.setTitle("选择一个发布类型");
                        break;
                    case R.id.navigation_item_person:
                        switchFragment(CertificateFragment.class);
                        mToolBar.setTitle("信息认证");
                        break;
                    case R.id.navigation_item_quit:
                        LoginActivity.launchLoginActivity(HomeActivity.this);
                        finish();
                    default:
                        break;
                }
                mPreItem = item;
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                item.setChecked(true);
                return false;
            }
        });
    }




    private void switchFragment(Class<?> clazz) {
        BaseFragment fragment = FragmentManagerWrapper.getInstance().createFragment(clazz);
        if (fragment.isAdded()) {
            mFragmentManager.beginTransaction().hide(mCurrentFragment).show(fragment).commitAllowingStateLoss();
        } else {
            mFragmentManager.beginTransaction().hide(mCurrentFragment).add(R.id.fl_main_content, fragment).commitAllowingStateLoss();
        }
        mCurrentFragment = fragment;
    }
    @Override
    protected void initData() {
        //// TODO
    }
}
