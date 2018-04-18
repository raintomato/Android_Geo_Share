package com.example.lenovo.geographysharing.Home;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.geographysharing.BaseClass.BaseFragment;
import com.example.lenovo.geographysharing.EntityClass.Channel;
import com.example.lenovo.geographysharing.R;
import com.example.lenovo.geographysharing.Details.DetaiListActivity;
import com.hejunlin.superindicatorlibray.CircleIndicator;
import com.hejunlin.superindicatorlibray.LoopViewPager;

import static com.example.lenovo.geographysharing.EntityClass.Channel.DEZHI;
import static com.example.lenovo.geographysharing.EntityClass.Channel.DIZHISHIYAN;
import static com.example.lenovo.geographysharing.EntityClass.Channel.HUATAN;
import static com.example.lenovo.geographysharing.EntityClass.Channel.MAX_COUNT;
import static com.example.lenovo.geographysharing.EntityClass.Channel.MEIYOU;
import static com.example.lenovo.geographysharing.EntityClass.Channel.QITA;
import static com.example.lenovo.geographysharing.EntityClass.Channel.RENYUAN;
import static com.example.lenovo.geographysharing.EntityClass.Channel.RUANJIAN;
import static com.example.lenovo.geographysharing.EntityClass.Channel.WUTAN;
import static com.example.lenovo.geographysharing.EntityClass.Channel.ZUANTAN;

/**
 * Created by lenovo on 2017/12/30.
 */

public class HomeFragment extends BaseFragment {
    private static final String TAG = HomeFragment.class.getSimpleName();
    private GridView mgridView;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        //轮播图以及小圆点
        LoopViewPager viewPager = bindViewId(R.id.looperviewpager);
        CircleIndicator circleIndicator = bindViewId(R.id.indicator);
        viewPager.setAdapter(new HomePicAdapter(getActivity()));
        viewPager.setLooperPic(true);
        circleIndicator.setViewPager(viewPager);
        //
        mgridView = bindViewId(R.id.gv_channel);
        mgridView.setAdapter(new ChannelAdapter());
        mgridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                Log.d(TAG, ">>onItemClick: "+position);
                switch (position){
                    default:
                        //TODO
                        DetaiListActivity.launchDetaiListActivity(getActivity(),position + 1);
                        break;

                }
            }
        });

    }

    @Override
    protected void initData() {

    }

    class ChannelAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return MAX_COUNT;
        }

        @Override
        public Channel getItem(int position) {
            return new Channel(position+1,getActivity());
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            Channel channel = getItem(position);
            ViewHolder holder = null;
            if (view == null) {
                view = LayoutInflater.from(getActivity()).inflate(R.layout.home_gird_item,null);
                holder = new ViewHolder();
                holder.textView =(TextView)view.findViewById(R.id.tv_home_item_text);
                holder.imageView = (ImageView)view.findViewById(R.id.iv_home_item_image);
                view.setTag(holder);
            }else {
                holder = (ViewHolder)view.getTag();
            }

            holder.textView.setText(channel.getChannelName());
            int id  = channel.getChannelId();
            int imgResId = -1;
            switch (id){
                case DEZHI:
                    imgResId = R.drawable.ic_show;
                    break;
                case WUTAN:
                    imgResId = R.drawable.ic_movie;
                    break;
                case HUATAN:
                    imgResId = R.drawable.ic_comic;
                    break;
                case ZUANTAN:
                    imgResId = R.drawable.ic_zuan;
                    break;
                case DIZHISHIYAN:
                    imgResId = R.drawable.ic_music;
                    break;
                case RENYUAN:
                    imgResId = R.drawable.ic_variety;
                    break;
                case RUANJIAN:
                    imgResId = R.drawable.ic_live;
                    break;
                case QITA:
                    imgResId = R.drawable.ic_bookmark;
                    break;
                case MEIYOU:
                    imgResId = R.drawable.ic_history;
                    break;
            }
            holder.imageView.setImageDrawable(getActivity().getResources().getDrawable(imgResId));
            return view;
        }
    }
    class ViewHolder{
        TextView textView;
        ImageView imageView;
    }


}
