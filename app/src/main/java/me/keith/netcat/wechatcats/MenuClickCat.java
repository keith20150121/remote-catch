package me.keith.netcat.wechatcats;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.Stack;

import me.keith.netcat.EmptyCat;
import me.keith.netcat.IServiceRequest;
import me.keith.netcat.IWinCat;
import me.keith.netcat.IWinCatLeader;

/**
 * Created by Keith on 3/18/16.
 */
public class MenuClickCat extends EmptyCat {

    private final String m_text;
    private final boolean m_bCallOnPreviousNode;
    private Stack<AccessibilityNodeInfo> m_Stack = new Stack<>();
    private boolean m_performed = false;
    private AccessibilityNodeInfo m_cached;
    private final IWinCat m_viewPagerCat;
    private final IServiceRequest m_sr;

    public MenuClickCat(@NonNull IWinCatLeader parent,
                        @NonNull IWinCat next,
                        @NonNull final String text,
                        @NonNull final IServiceRequest sr,
                        boolean callOnPreviousNode) {
        super(parent, next);
        m_viewPagerCat = new ViewPagerLongClickCat(parent, next, sr);
        m_sr = sr;
        m_text = text;
        m_bCallOnPreviousNode = callOnPreviousNode;
    }

    @Override
    public boolean onTraversing(int level, AccessibilityNodeInfo node) {
        CharSequence c = node.getText();
        if (null == c) {
            m_Stack.push(node);
            return true;
        }

        final String text = c.toString();
        Log.d(TAG, String.format("level:%d, text:%s", level, text));
        if (m_text.equals(text)) {
            if (m_bCallOnPreviousNode) {
                node = m_Stack.pop();
            }
            m_performed = true;
            m_cached = node;
            node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        } else {
            m_Stack.push(node);
            return true;
        }

        m_Stack.clear();
        return false;
    }

    @Override
    public void onTraversed() {
        if (m_performed) {
            m_performed = false;
            super.onTraversed();
        } else {
            m_parent.next(m_viewPagerCat);
            m_sr.traverse();
        }
    }

    @Override
    public void populate() {
        if (m_cached != null) {
            m_cached.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
    }
}
