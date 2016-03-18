package me.keith.netcat.wechatcats;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.ArrayList;
import java.util.Stack;

import me.keith.netcat.EmptyCat;

/**
 * Created by Keith on 3/18/16.
 */
public class MenuClickCat extends EmptyCat {

    private Stack<AccessibilityNodeInfo> m_Stack = new Stack<>();
    private final String m_text;
    private final boolean m_preTarget;

    public MenuClickCat(@NonNull final String text, boolean previousTarget) {
        m_text = text;
        m_preTarget = previousTarget;
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
            if (m_preTarget) {
                node = m_Stack.pop();
            }
            node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        } else {
            m_Stack.push(node);
            return true;
        }

        m_Stack.clear();
        return false;
    }
}
