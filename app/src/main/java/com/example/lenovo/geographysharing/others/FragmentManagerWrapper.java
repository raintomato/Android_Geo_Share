package com.example.lenovo.geographysharing.others;


import com.example.lenovo.geographysharing.BaseClass.BaseFragment;

import java.util.HashMap;

public class FragmentManagerWrapper {

    private volatile static FragmentManagerWrapper mInstance = null;
/*
 * getInstance是少部分类才有的一个方法，各自的实现也不同。
 * getInstance在单例模式(保证一个类仅有一个实例，
 * 并提供一个访问它的全局访问点)的类中常见，用来生成唯一的实例，getInstance往往是static的
 * (1) 对象使用之前通过getInstance得到而不需要自己定义，用完之后不需要delete；
 * (2)new一定要生成一个新对象，分配内存；getInstance() 则不一定要再次创建，它可以把一个已存在的引用给你使用，这在效能上优于new；
 * (3) new创建后只能当次使用，而getInstance()可以跨栈区域使用，或者远程跨区域使用。所以getInstance()通常是创建static静态实例方法的。
      */
    public static FragmentManagerWrapper getInstance(){
        if (mInstance == null) {
            //线程只允许执行一个
            synchronized (FragmentManagerWrapper.class) {
                if (mInstance == null) {
                    mInstance = new FragmentManagerWrapper();
                }
            }
        }
        return mInstance;
    }
//存储fragment
    private HashMap<String, BaseFragment> mHashMap = new HashMap<>();
//创建Fragment
    public BaseFragment createFragment(Class<?> clazz){
        return createFragment(clazz, true);
    }

    public BaseFragment createFragment(Class<?> clazz, boolean isobtain){
        BaseFragment fragment = null;
        String className = clazz.getName();
        //哈希表中有这个类键值，就找到这个类
        if (mHashMap.containsKey(className)) {
            fragment = mHashMap.get(className);
        } else {
            try {
                fragment = (BaseFragment) Class.forName(className).newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        //如果含有的话
        if (isobtain) {
            mHashMap.put(className,fragment);
        }
        return fragment;
    }

}
