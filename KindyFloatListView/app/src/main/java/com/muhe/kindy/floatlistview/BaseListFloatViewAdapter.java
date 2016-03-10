package com.muhe.kindy.floatlistview;

import android.view.View;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;

/**
 * Created by Kindy on 2015/9/2.
 */
public abstract class BaseListFloatViewAdapter<T> extends BaseAdapter implements IGroupHelper , AbsListView.OnScrollListener {

    protected ArrayList<T> mData;
    protected View mFloatView;

    private OnChangeFloatViewContentListener mOnChangeFloatViewContentListener;

    public void setOnChangeFloatViewContentListener(OnChangeFloatViewContentListener l) {
        this.mOnChangeFloatViewContentListener = l;
    }

    public BaseListFloatViewAdapter(ArrayList<T> mData, View floatView) {
        this.mData = mData;
        this.mFloatView = floatView;
        if(mFloatView != null) {
            // 截断Touche
            mFloatView.setClickable(true);
        }
    }

    @Override
    public int getPreviousGroupPosition(int position) {
        if(mData != null && position >= 0 && position < mData.size()) {
            for(int i=position; i>=0; i--) {
                if(isGroup(i)) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public int getNextGroupPosition(int position) {
        if(mData != null && position >= 0 && position < mData.size()) {
            for(int i=position; i<mData.size(); i++) {
                if(isGroup(i)) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(AbsListView.OnScrollListener.SCROLL_STATE_IDLE == scrollState) {
            int firstVisibleItem = view.getFirstVisiblePosition();
            int lastVisibleItem = view.getLastVisiblePosition();
            int visibleItemCount = lastVisibleItem - firstVisibleItem + 1;
            this.onScroll(view, firstVisibleItem, visibleItemCount, this.getCount());
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if(mFloatView == null) {
            return;
        }
        if(visibleItemCount == 0) {
            mFloatView.setVisibility(View.INVISIBLE);
            return;
        }
        int position = firstVisibleItem;
        if (position >= 0) {
            int i = this.getPreviousGroupPosition(position);
            mFloatView.setVisibility(i==-1 ? View.INVISIBLE : View.VISIBLE);

            int p1 = this.getNextGroupPosition(position);
            int p2 = -1;
            if(p1 == -1) {
                ViewHelper.setY(mFloatView, 0);
            } else {
                if(p1 == position) {// 第一个View是Group
                    if(mOnChangeFloatViewContentListener != null) {
                        mOnChangeFloatViewContentListener.onChangeFloatViewContent(this.getItem(p1));
                    }
                    ViewHelper.setY(mFloatView, 0);

                    p2 = this.getNextGroupPosition(p1 + 1);
                } else {
                    p2 = p1;
                }

                if(p2 != -1 && p2 - firstVisibleItem < visibleItemCount) {
                    int index = p2-firstVisibleItem;
                    View v2 = view.getChildAt(index);
                    int top = v2.getTop();
                    if(top > 0) {
                        int y = mFloatView.getHeight() - v2.getTop();
                        if (y > 0) {
                            ViewHelper.setY(mFloatView, -y);
                        } else {
                            ViewHelper.setY(mFloatView, 0);
                        }
                    }

                    int pre = this.getPreviousGroupPosition(p2 - 1);
                    if(pre != -1) {
                        if(mOnChangeFloatViewContentListener != null) {
                            mOnChangeFloatViewContentListener.onChangeFloatViewContent(this.getItem(pre));
                        }
                    }
                }
            }
        }
    }
}
