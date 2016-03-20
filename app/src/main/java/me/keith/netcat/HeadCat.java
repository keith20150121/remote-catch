package me.keith.netcat;


import android.view.accessibility.AccessibilityNodeInfo;

import me.keith.netcat.wechatcats.ChatLogCat;
import me.keith.netcat.wechatcats.GlobalHomeCat;
import me.keith.netcat.wechatcats.MenuClickCat;

/**
 * Created by Keith on 2016/3/20.
 */
public class HeadCat implements IWinCatLeader, IWinCat {

    private final IServiceRequest m_sr;
    private final ChatLogCat m_first;
    private IWinCat m_cat;

    public HeadCat(IServiceRequest sr) {
        m_sr = sr;

        m_first = new ChatLogCat(this, null, sr);
        IWinCat last = new GlobalHomeCat(this, m_first, sr);
        IWinCat send = new MenuClickCat(this, last, "发送", sr, false);
        IWinCat keith = new MenuClickCat(this, send, "Keith", sr, true);
        IWinCat repost = new MenuClickCat(this, keith, "转发", sr, true);
        m_first.setNext(repost);
        m_cat = m_first;
    }

    @Override
    public void pass(Object obj) {

    }

    @Override
    public void next(IWinCat cat) {
        m_cat = cat;
    }

    @Override
    public void again() {
        m_cat = m_first;
    }

    @Override
    public void populate() {

    }

    @Override
    public void onArgPassed(Object obj) {

    }

    @Override
    public boolean onNeedTraverse() {
        return m_cat.onNeedTraverse();
    }

    @Override
    public boolean onTraversing(int level, AccessibilityNodeInfo node) {
        return m_cat.onTraversing(level, node);
    }

    @Override
    public void onTraversed() {
        m_cat.onTraversed();
    }

    @Override
    public void onTypeWindowStateChanged(AccessibilityNodeInfo node) {
        m_cat.onTypeWindowStateChanged(node);
    }
}
