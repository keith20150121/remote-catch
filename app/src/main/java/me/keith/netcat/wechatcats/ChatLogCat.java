package me.keith.netcat.wechatcats;

import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Stack;

import me.keith.netcat.EmptyCat;

/**
 * Created by Keith on 3/18/16.
 */
public class ChatLogCat extends EmptyCat {
    private Stack<AccessibilityNodeInfo> m_Stack = new Stack<>();

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
            if (TEXT_VIEW.equals(clz) || IMAGE_VIEW.equals(clz)) {
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
        /*
        for (int i = count - 1; i >= 0; --i) {
            node = m_Stack.get(i);
            clz = node.getClassName().toString();
            if (TEXT_VIEW.equals(clz) || IMAGE_VIEW.equals(clz)) {
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
        */
        m_Stack.clear();
    }

    @Override
    public void onTypeWindowStateChanged(AccessibilityNodeInfo node) {
        m_Stack.clear();
    }

    @Override
    public void populate() {

    }
}
