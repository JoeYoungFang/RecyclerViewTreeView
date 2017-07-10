package com.example.joee.recyclerviewtreeview.bean;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

/**
 * 节点和折叠部分的公共父类,任意一个节点最多只有一个父节点，一个父节点可以有多个子节点，子节点持有父节点的引用，可以找到父节点
 * Created by joee on 2016/9/7.
 */
public class BaseItem<T extends BaseItem> implements Comparable<T> {
    protected String name;              //可以是包名，路径
    protected Drawable icon;            //图标

    protected boolean isChecked;        //是否被选中
    protected boolean isExpanded;       //是否展开

    protected long size;                //大小
    private int viewType;               //显示类型
    private int curDeep;                //当前深度
    private int pos;                    //用来记录当前item在RecyclerView中位置的

    protected T mParent;                //父节点
    protected List<T> mChildList;       //子item

    public BaseItem() {
    }

    public BaseItem(String name, long size) {
        this.name = name;
        this.size = size;
    }

    public T getmParent() {
        return mParent;
    }

    public void setmParent(T mParent) {
        this.mParent = mParent;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public int getCurDeep() {
        return curDeep;
    }

    public void setCurDeep(int curDeep) {
        this.curDeep = curDeep;
    }

    public void setmChildList(List<T> mChildList) {
        for (T t : mChildList) {
            t.setmParent(this);
        }
        this.mChildList = mChildList;
    }

    public void addChildItem(T child) {
        if (mChildList == null)
            mChildList = new ArrayList<>();
        child.setmParent(this);
        mChildList.add(child);
    }

    public List<T> getmChildList() {
        return mChildList;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(T o) {
        return (int) (this.size - o.getSize());//升序，但注意：显示的时候升序容易误以为是降序
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
