package com.example.joee.recyclerviewtreeview.adapter;


import com.example.joee.recyclerviewtreeview.bean.BaseItem;

/**
 * Created by joee on 2016/9/7.
 */
public interface SectionStateChangeListener {
    public void SectionStateChange(BaseItem section, boolean isChecked);

    public void expandStateChange(BaseItem section);

    public void checkStateChange(BaseItem baseItem);
}
