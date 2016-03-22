package me.keith.netcat;

import android.view.accessibility.AccessibilityNodeInfo;

/**
 * Created by Keith on 3/18/16.
 */
public abstract class EmptyCat implements IWinCat {
    public final static String TAG = "IWinCat";
    public final static String RELATIVE_LAYOUT = "android.widget.RelativeLayout";
    public final static String LINEAR_LAYOUT = "android.widget.LinearLayout";
    public final static String FRAME_LAYOUT = "android.widget.FrameLayout";
    public final static String TEXT_VIEW = "android.widget.TextView";
    public final static String IMAGE_VIEW = "android.widget.ImageView";
    public final static String VIEW_PAGER = "android.support.v4.view.ViewPager";

    protected ICatLeader m_parent;
    protected IWinCat m_next;

    public EmptyCat(ICatLeader parent, IWinCat next) {
        m_parent = parent;
        m_next = next;
    }

    @Override
    public void onArgPassed(Object obj) {

    }

    @Override
    public boolean onTraversing(int level, AccessibilityNodeInfo node) {
        return true;
    }

    @Override
    public void onTraversed() {
        m_parent.next(m_next);
    }

    @Override
    public void onTypeWindowStateChanged(AccessibilityNodeInfo node) {

    }

    @Override
    public void populate() {

    }

    @Override
    public boolean onNeedTraverse() {
        return true;
    }
}
