package com.example.lenovo.geographysharing.widget;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.geographysharing.BaseClass.BaseFragment;
import com.example.lenovo.geographysharing.Details.DetailListFragment;
import com.example.lenovo.geographysharing.EntityClass.Equipment;
import com.example.lenovo.geographysharing.R;
import com.example.lenovo.geographysharing.others.FragmentManagerWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/12/31.
 */

public class PullLoadRVAdapter extends RecyclerView.Adapter<PullLoadRVAdapter.EViewholer> implements View.OnClickListener{
    //声明接口变量
    private OnItemClickListener mOnItemClickListener = null;

    //接口
    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    private FragmentManager mFragmentManager;
    private BaseFragment mCurrentFragment;
    //创建存储设备的数组。
    private List<Equipment> myEquipList = new ArrayList<>();

    static class EViewholer extends RecyclerView.ViewHolder {
        //声明控件
        TextView equip_name;
        TextView equip_property;
        TextView equip_address;
        TextView equip_price;
        ImageView equip_image;
        //声明item
        View equipView;

        public EViewholer(View itemView) {
            super(itemView);
            equipView = itemView;
            //实例化控件
            equip_name = (TextView) itemView.findViewById(R.id.equip_name);
            equip_property = (TextView) itemView.findViewById(R.id.equip_property);
            equip_address = (TextView) itemView.findViewById(R.id.equip_address);
            equip_price = (TextView) itemView.findViewById(R.id.equip_price);
            equip_image = (ImageView) itemView.findViewById(R.id.equip_image);
        }
    }

    //adapter构造函数
    public PullLoadRVAdapter(List<Equipment> equipments) {
        myEquipList = equipments;
    }

    @Override
    public EViewholer onCreateViewHolder(ViewGroup parent, int viewType) {
        //加载布局
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.equipment_item, parent, false);
        //实例化Viewholder
        //点击事件

          final EViewholer eViewholer = new EViewholer(view);
//        eViewholer.equipView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position=eViewholer.getAdapterPosition();
//                Equipment equipment=myEquipList.get(position);
//                Toast.makeText(v.getContext(), "You click the View "+equipment.getEquipName(), Toast.LENGTH_SHORT).show();
////                initFragemnt();
////                switchFragment(DetailsFragment.class);
//            }
//        });
        eViewholer.equipView.setOnClickListener(this);
        return eViewholer;
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(view, (int) view.getTag());
        }
    }
    //初始化Fragment
    private void initFragemnt() {

//        mFragmentManager = getSupportFragmentManager();
        //初始化传入的homefragment
        mCurrentFragment = FragmentManagerWrapper.getInstance().createFragment(DetailListFragment.class,true);
        //fragmentmanager 事务的回滚
        mFragmentManager.beginTransaction().add(R.id.fl_details_content,mCurrentFragment).commit();
    }
    private void switchFragment(Class<?> clazz) {
        BaseFragment fragment = FragmentManagerWrapper.getInstance().createFragment(clazz);
        if (fragment.isAdded()) {
            mFragmentManager.beginTransaction().hide(mCurrentFragment).show(fragment).commitAllowingStateLoss();
        } else {
            mFragmentManager.beginTransaction().hide(mCurrentFragment).add(R.id.fl_details_content, fragment).commitAllowingStateLoss();
        }
        mCurrentFragment = fragment;
    }

    @Override
    public void onBindViewHolder(EViewholer holder, int position) {
        Equipment equipment = myEquipList.get(position);
        holder.equip_name.setText(equipment.getEquipName());
        holder.equip_property.setText(equipment.getEquipProperty());
        holder.equip_address.setText(equipment.getEquipAdress());
        holder.equip_price.setText(equipment.getEquipPrice());
        holder.equip_image.setImageResource(equipment.getEquipImageID());

        //将position保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return myEquipList.size();
    }
    //暴露给外面的调用者
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
