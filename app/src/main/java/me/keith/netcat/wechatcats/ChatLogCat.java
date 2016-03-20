package me.keith.netcat.wechatcats;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.Enumeration;
import java.util.Stack;

import me.keith.netcat.EmptyCat;
import me.keith.netcat.IServiceRequest;
import me.keith.netcat.IWinCat;
import me.keith.netcat.IWinCatLeader;

/**
 * Created by Keith on 3/18/16.
 */
public class ChatLogCat extends EmptyCat {
    private Stack<AccessibilityNodeInfo> m_Stack = new Stack<>();
    private AccessibilityNodeInfo m_cached;
    private IServiceRequest m_sr;

    public ChatLogCat(@NonNull IWinCatLeader parent, IWinCat next, @NonNull IServiceRequest sr) {
        super(parent, next);
        m_sr = sr;
    }

    public void setNext(@NonNull IWinCat next) {
        m_next = next;
    }

    @Override
    public boolean onTraversing(int level, AccessibilityNodeInfo node) {
        if (level > 4) {
            m_Stack.push(node);
        }
        return true;
    }

    @Override
    public void onTraversed() {
        Enumeration<AccessibilityNodeInfo> e = m_Stack.elements();
        AccessibilityNodeInfo node;
        String clz;
        while (e.hasMoreElements()) {
            node = m_Stack.pop();
            clz = node.getClassName().toString();
            if (TEXT_VIEW.equals(clz) || FRAME_LAYOUT.equals(clz)) {
                m_cached = node;
                node.performAction(AccessibilityNodeInfo.ACTION_LONG_CLICK);
                break;
            } else {
                Log.e(TAG, String.format("ClassName:%s, out of expectation: TextView or ImageView!",
                        clz));
                if (LINEAR_LAYOUT.equals(clz) || RELATIVE_LAYOUT.equals(clz)) {
                    break;
                }
            }
        }
        m_Stack.clear();
        super.onTraversed();
    }

    @Override
    public void onTypeWindowStateChanged(AccessibilityNodeInfo node) {
        m_Stack.clear();
    }

    @Override
    public void populate() {
        if (null != m_cached) {
            m_cached.performAction(AccessibilityNodeInfo.ACTION_LONG_CLICK);
        }
    }
}
