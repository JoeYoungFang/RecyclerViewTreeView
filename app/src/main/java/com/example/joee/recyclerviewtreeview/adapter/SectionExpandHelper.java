package com.example.joee.recyclerviewtreeview.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.joee.recyclerviewtreeview.bean.BaseItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

/**
 * 装饰设计模式，装饰recyclerView
 * Created by joee on 2016/9/7.
 */
public class SectionExpandHelper<T extends BaseItem> implements SectionStateChangeListener {
    private Context mContext;
    private SectionExpandAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private List<T> mSectionList;//第一层的item集合
    private List<T> mExpandList;//展开的list
    private Comparator<T> mComparator;//比较器，策略模式

    public interface IBindData {
        public void bindData();
    }

    public SectionExpandHelper(Context context, RecyclerView recyclerView, IMultipleItem multipleItem,
                               GridLayoutManager gridLayoutManager, Comparator<T> comparator) {
        mContext = context;
        mComparator = comparator;
        mExpandList = new ArrayList<>();
        mSectionList = new ArrayList<>();
        mAdapter = new SectionExpandAdapter<T>(context, mExpandList, gridLayoutManager, this, multipleItem);
        mRecyclerView = recyclerView;
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        notifyDataChange();
    }

    //设置比较器
    public void setmComparator(Comparator<T> mComparator) {
        this.mComparator = mComparator;
    }

    //展开变化更新
    private void notifyDataChange() {
        sortData();
        generateData();
        mAdapter.notifyDataSetChanged();
    }

    //数据排序
    public void sortData() {
        if (mComparator != null)
            Collections.sort(mSectionList, mComparator);
    }


    //无展开变化通知,找到t的父节点，遍历子节点，如果子节点的isCheck状态都和父节点不一样，那么父节点的状态就要变化，如果有相同的就不用
    private void notifyCheckStateChange(T t) {
        boolean isChange = false;//默认不变化
        t.setChecked(!t.isChecked());//设置点击后的状态
        traverChilds(t);//遍历子所有节点
        traversalParents(t);
        refreshUI();
    }

    //遍历所有子节点
    public void traverChilds(T t) {
        List<T> childs = t.getmChildList();
        if (childs == null)
            return;
        for (T child : childs) {
            child.setChecked(t.isChecked());
            traverChilds(child);
        }
    }

    //遍历所有父节点
    public void traversalParents(T t) {
        T parent = (T) t.getmParent();
        boolean isChange = false;
        if (parent != null) {
            List<T> pChilds = parent.getmChildList();//t的兄弟节点+t
            boolean state = parent.isChecked();
            if (state == true) {
                for (T child : pChilds) {
                    if (child.isChecked() == false) {
                        isChange = true;//只要有没被选的就能确定变化
                        break;
                    }
                }
            } else {
                for (T child : pChilds) {
                    if (child.isChecked() == true)
                        isChange = true;//只要有为没被选的就能确定不同变化
                    else {
                        isChange = false;
                        break;
                    }
                }
            }
        }
        if (isChange) {
            parent.setChecked(!parent.isChecked());
            traversalParents(parent);
        }
    }

    //刷新界面
    public void refreshUI() {
        if (mAdapter != null)
            mAdapter.notifyDataSetChanged();
    }

    //增加第一层数据，数量1
    public void addSection(T item) {
        mSectionList.add(item);
        notifyDataChange();
    }

    //增加第一层数据，数量list.size()
    public void addAllSection(Collection<T> list) {
        mSectionList.clear();
        mSectionList.addAll(list);
        notifyDataChange();
    }

    private Stack<T> mStack = new Stack<>();

    //广度优先搜索，需要使用到堆栈
    public boolean findAndAdd_BFS(T t, T parent, T child) {
        boolean isFinded = false;//找到父节点标志
        mStack.push(t);
        if (t.equals(parent)) {
            t.addChildItem(child);
            isFinded = true;
        }
        if (!isFinded)
            isFinded = findAndAdd_BFS(mStack.pop(), parent, child);
        return isFinded;
    }

    //深度优先搜索，暂时没处理找不到的情况
    public boolean addChild(T parent, T child, boolean isBFS) {
        boolean isFinded = false;//找到父节点标志
        if (isBFS) {
            for (T t : mSectionList) {
                findAndAdd_BFS(t, parent, child);
            }
        } else {
            for (T t : mSectionList) {
                isFinded = findAndAdd(t, parent, child);
            }
        }
        return isFinded;
    }

    //添加字节点集合，深度优先搜索
    public boolean addChilds(T parent, List<T> childs) {
        boolean isFinded = false;//找到父节点标志
        for (T t : mSectionList) {
            isFinded = findAndAdds(t, parent, childs);
        }
        return isFinded;
    }

    //查找匹配
    public boolean findAndAdd(T t, T parent, T child) {
        boolean isFinded = false;
        if (t.equals(parent)) {
            t.addChildItem(child);
            isFinded = true;
        } else if (t.getmChildList() != null) {
            List<T> childs = t.getmChildList();
            for (T childItem : childs) {
                if (isFinded = findAndAdd(childItem, parent, child))
                    break;
            }
        }
        return isFinded;
    }

    //查找匹配
    public boolean findAndAdds(T t, T parent, List<T> childs) {
        boolean isFinded = false;
        if (t.equals(parent)) {
            t.setmChildList(childs);
            isFinded = true;
        } else if (t.getmChildList() != null) {
            List<T> childList = t.getmChildList();
            for (T childItem : childs) {
                if (isFinded = findAndAdds(childItem, parent, childList))
                    break;
            }
        }
        return isFinded;
    }

    //生产objectList的内容
    private void generateData() {
        mExpandList.clear();
        getChildList(mSectionList);
    }

    //生成所有展开后的item
    public void getChildList(List<T> parentList) {
        if (parentList == null)
            return;
        for (T t : parentList) {
            mExpandList.add(t);
            if (t.isExpanded()) {
                List<T> childs = t.getmChildList();
                getChildList(childs);
            }
        }
    }

    //这是一个回调接口，当Adapter中的Section被点击事回调这个方法
    @Override
    public void SectionStateChange(BaseItem section, boolean isExpand) {
        section.setExpanded(isExpand);
        notifyDataChange();
    }

    @Override
    public void expandStateChange(BaseItem section) {
        if (section.getmChildList() == null)
            return;
        section.setExpanded(!section.isExpanded());
        notifyDataChange();
    }

    @Override
    public void checkStateChange(BaseItem section) {
        if (section.getmChildList() == null && section.getmParent() == null) {//子和父都是空的
            return;
        } else {
            notifyCheckStateChange((T) section);
        }
    }

}
