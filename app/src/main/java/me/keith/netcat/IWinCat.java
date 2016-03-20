package me.keith.netcat;

import android.view.accessibility.AccessibilityNodeInfo;

/**
 * Created by swd3 on 3/18/16.
 */
public interface IWinCat {

    void onArgPassed(Object obj);

    boolean onNeedTraverse();

    boolean onTraversing(int level, AccessibilityNodeInfo node);

    void onTraversed();

    void onTypeWindowStateChanged(AccessibilityNodeInfo node);

    void populate();
}
