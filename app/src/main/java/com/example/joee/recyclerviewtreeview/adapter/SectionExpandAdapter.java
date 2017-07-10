package com.example.joee.recyclerviewtreeview.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.joee.recyclerviewtreeview.bean.BaseItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joee on 2016/9/7.
 */
public class SectionExpandAdapter<T extends BaseItem> extends RecyclerView.Adapter<ViewHolder> {
    private IMultipleItem mMultipleItem;

    private List<T> mDatas;
    private Context mContext;
    private SectionStateChangeListener msectionStateChangeListener;

    //此处获取到helper的引用（回调接口方法）
    public SectionExpandAdapter(Context context, List<T> data, final GridLayoutManager gridLayoutManager,
                                SectionStateChangeListener stateChangeListener, IMultipleItem iMultipleItem) {
        mContext = context;
        msectionStateChangeListener = stateChangeListener;
        mDatas = data == null ? new ArrayList<T>() : data;
        mMultipleItem = iMultipleItem;
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                //如果是section的话spansiz = 1
                return mMultipleItem.isSection(mDatas.get(position)) ? gridLayoutManager.getSpanCount() : 1;
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ViewHolder.createViewHolder(mContext, parent, viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        mMultipleItem.bindData(holder, position, mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mMultipleItem != null)
            return mMultipleItem.getItemLayout(mDatas.get(position));
        else
            return 0;
    }

}
