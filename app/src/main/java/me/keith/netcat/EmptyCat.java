package me.keith.netcat;

import android.view.accessibility.AccessibilityNodeInfo;

/**
 * Created by Keith on 3/18/16.
 */
public abstract class EmptyCat implements IWinCat {
    public final static String TAG = "IWinCat";
    public final static String RELATIVE_LAYOUT = "android.widget.RelativeLayout";
    public final static String LINEAR_LAYOUT = "android.widget.LinearLayout";
    public final static String TEXT_VIEW = "android.widget.TextView";
    public final static String IMAGE_VIEW = "android.widget.ImageView";

    @Override
    public boolean onTraversing(int level, AccessibilityNodeInfo node) {
        return true;
    }

    @Override
    public void onTraversed() {

    }

    @Override
    public void onTypeWindowStateChanged(AccessibilityNodeInfo node) {

    }

    @Override
    public void populate() {

    }
}
