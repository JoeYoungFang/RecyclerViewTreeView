package com.example.joee.recyclerviewtreeview.adapter;

/**
 * 监听Section和item的点击事件
 * Created by joee on 2016/9/7.
 */
public interface ItemClickListener<T,V> {
    void onItemClick(T item);

    void OnSectionClick(V section);
}
