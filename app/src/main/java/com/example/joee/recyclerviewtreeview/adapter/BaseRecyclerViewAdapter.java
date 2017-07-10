package com.example.joee.recyclerviewtreeview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * RecyclerView的万能适配器
 * Created by joee on 2016/7/29.
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    protected List<T> mData;
    protected Context mContext;
    protected int mLayoutId;
    protected IonClickListener ionClickListener;
    protected List<Boolean> mCheckList;//用来控制checkbox是否被勾选的

    public BaseRecyclerViewAdapter(Context context, Collection<T> datas, int resId) {
        mContext = context;
        mData = datas == null ? new ArrayList<T>() : new ArrayList<T>(datas);
        mLayoutId = resId;
        mCheckList = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ViewHolder.createViewHolder(mContext, parent, mLayoutId);
    }

    //获取所有被选中的item集合
    public List<T> getCheckItem() {
        if (mCheckList == null)
            return null;
        List<T> data = new ArrayList<>();
        for (Boolean b : mCheckList) {
            if (b)
                data.add(mData.get(mCheckList.indexOf(b)));
        }
        return data;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        bindView(holder, getItem(position), position);
    }

    public void setIonClickListener(IonClickListener ionClickListener) {
        this.ionClickListener = ionClickListener;
    }

    public abstract void bindView(ViewHolder holder, T t, int position);

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public T getItem(int pos) {
        return mData.get(pos);
    }

    public void bindData(Collection<T> data) {
        if (data != null) {
            mData = new ArrayList<>(data);
            notifyDataSetChanged();
        }
    }

    public void addAll(Collection<T> data) {
        if (data != null) {
            mData.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void addItem(T t) {
        mData.add(t);
        notifyDataSetChanged();
    }

    public void removeItem(int pos) {
        mData.remove(pos);
    }

    public void removeItem(T t) {
        mData.remove(t);
        notifyDataSetChanged();
    }

    public void setList(List<T> list) {
        mData.clear();
        mData.addAll(list);
    }

    public List<T> getList() {
        return mData;
    }

    public interface IonClickListener {
        void onClick(int pos);
    }
}
