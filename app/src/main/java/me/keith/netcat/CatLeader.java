package me.keith.netcat;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.ArrayList;

import me.keith.netcat.wechatcats.ChatLogCat;
import me.keith.netcat.wechatcats.GlobalHomeCat;
import me.keith.netcat.wechatcats.MenuClickCat;

/**
 * Created by Keith on 3/18/16.
 */
public class CatLeader extends EmptyCat implements IWinCatLeader {
    public final static String TAG = "CatsLoader";

    private String m_Receiver;
    private ArrayList<IWinCat> m_cats = new ArrayList<>();
    private int m_current;
    private int COUNT;
    private IServiceRequest m_sr;

    // TODO: 3/18/16 international
    private final static String REPOST = "转发";

    public CatLeader(@NonNull IServiceRequest sr) {
        m_cats.add(new ChatLogCat());
        m_cats.add(new MenuClickCat(REPOST, true));
        m_cats.add(new MenuClickCat("Keith", true));
        m_cats.add(new MenuClickCat("发送", false));
        m_cats.add(new GlobalHomeCat(sr));
        COUNT = m_cats.size();
        m_current = 0;
        m_sr = sr;
    }

    public void setTransferTarget(final String target) {
        m_Receiver = target;
    }

    private void relay() {
        m_current = m_current < COUNT ? m_current + 1 : 0;
    }

    @Override
    public boolean onTraversing(int level, AccessibilityNodeInfo node) {
        if (m_current >= COUNT) {
            relay();
        }
        Log.d(TAG, "Traversing:Cat:" + m_current);
        return m_cats.get(m_current).onTraversing(level, node);
    }

    @Override
    public void onTraversed() {
        if (m_current >= COUNT) {
            return;
        }
        Log.d(TAG, "Traversed:Cat:" + m_current);
        m_cats.get(m_current).onTraversed();
        relay();
    }

    @Override
    public void onTypeWindowStateChanged(AccessibilityNodeInfo node) {
        if (m_current >= COUNT) {
            relay();
        }
        m_cats.get(m_current).onTypeWindowStateChanged(node);
    }

    @Override
    public void populate() {
        if (m_current >= COUNT) {
            relay();
        }
        m_cats.get(m_current).populate();
    }

    @Override
    public void next() {

    }
}
