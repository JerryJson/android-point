package com.muhe.kindy.floatlistview;

import android.view.View;

/**
 * Created by Kindy on 2015/9/2.
 */
public interface IGroupHelper {

    public int getPreviousGroupPosition(int position);
    public int getNextGroupPosition(int position);
    public boolean isGroup(int position);

}
