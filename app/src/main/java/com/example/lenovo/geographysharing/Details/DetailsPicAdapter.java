package com.example.lenovo.geographysharing.Details;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.lenovo.geographysharing.R;

/**
 * Created by lenovo on 2018/1/1.
 */

public class DetailsPicAdapter extends PagerAdapter {

    private Context context;

    private int[] mImg = new int[]{
            R.drawable.img_text_equipment1,
            R.drawable.img_text_equipment2,
    };
    public DetailsPicAdapter(Activity activity) {
        context = activity;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.details_pic_item,null);
        ImageView imageView = (ImageView)view.findViewById(R.id.iv_details_image);
        imageView.setImageResource(mImg[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
