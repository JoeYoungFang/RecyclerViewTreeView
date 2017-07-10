package com.example.joee.recyclerviewtreeview.adapter;

import com.example.joee.recyclerviewtreeview.bean.BaseItem;

/**
 * Created by joee on 2016/9/25.
 */

public interface IMultipleItem<T extends BaseItem> {
    public int getItemLayout(T t);//根据判断参数t的具体类型返回不同的类型

    public boolean isSection(T t);//用于判断是否是第一层

    public void bindData(ViewHolder holder, int position, T item);//绑定多有要显示的信息
}
