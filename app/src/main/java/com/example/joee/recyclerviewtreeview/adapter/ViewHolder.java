package com.example.joee.recyclerviewtreeview.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * 万能适配器,用于RecyclerView
 * Created by joee on 2016/7/28.
 */
public class ViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;
    private Context mContext;

    //    private
    public ViewHolder(Context context, View itemView) {
        super(itemView);
        mContext = context;
        mViews = new SparseArray<>();
    }

    public static ViewHolder createViewHolder(Context context, ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        ViewHolder hodler = new ViewHolder(context, itemView);
        return hodler;
    }

    //根据控件的资源文件获取控件
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public ViewHolder setText(int tv_id, String text) {
        TextView tv = getView(tv_id);
        tv.setText(text);
        return this;
    }

    public ViewHolder setImage(int img_id, Bitmap bitmap) {
        ImageView img = getView(img_id);
        img.setImageBitmap(bitmap);
        return this;
    }

    public ViewHolder setImageDrawable(int img_id, Drawable drawable) {
        ImageView img = getView(img_id);
        img.setImageDrawable(drawable);
        return this;
    }

    public ViewHolder setViewOnclick(int img_id, View.OnClickListener listener) {
        View view = getView(img_id);
        view.setOnClickListener(listener);
        return this;
    }

    public ViewHolder setViewOnLongclick(int img_id, View.OnLongClickListener listener) {
        View view = getView(img_id);
        view.setOnLongClickListener(listener);
        return this;
    }

    public ViewHolder setToggleCheck(int img_id, boolean isChecked) {
        ToggleButton toggleButton = getView(img_id);
        toggleButton.setChecked(isChecked);
        return this;
    }

    public ViewHolder setImage(int img_id, int ResId) {
        ImageView img = getView(img_id);
        img.setImageResource(ResId);
        return this;
    }

    public void hideView(int viewId) {
        View view = getView(viewId);
        view.setVisibility(View.INVISIBLE);
    }

    public void setViewGone(int viewId) {
        View view = getView(viewId);
        view.setVisibility(View.GONE);
    }

    public void showView(int viewId) {
        View view = getView(viewId);
        view.setVisibility(View.VISIBLE);
    }

    public void setTextViewOnclick(int tvId, View.OnClickListener listener) {
        TextView tv = getView(tvId);
        tv.setOnClickListener(listener);
    }

    public void setImageViewOnclick(int tvId, View.OnClickListener listener) {
        ImageView img = getView(tvId);
        img.setOnClickListener(listener);
    }

    public void setButtonOnclick(int tvId, View.OnClickListener listener) {
        Button btn = getView(tvId);
        btn.setOnClickListener(listener);
    }
}
