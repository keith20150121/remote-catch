package me.keith.netcat;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;
import android.app.Notification;
import android.app.PendingIntent;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Keith on 3/16/16.
 */
public class WeChatExtService extends AccessibilityService implements IServiceRequest {
    public static String TAG = "WeChatExtService";
    public static String WECHAT = "com.tencent.mm";

    private CatLeader m_cl = new CatLeader(this);

    @Override
    public void onInterrupt() {

    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        m_cl.setTransferTarget("Keith");
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent e) {
        final int type = e.getEventType();
        Log.d(TAG, "event coming:" + e);
        switch (type) {
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                peekNotification(e);
                jump(e);
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                traverseWindow(e);
                break;
        }
    }

    @Override
    public void home() {
        performGlobalAction(GLOBAL_ACTION_HOME);
    }

    @Override
    public void back() {
        performGlobalAction(GLOBAL_ACTION_BACK);
    }

    public static void process(int level, AccessibilityNodeInfo node) {
        final String clz = String.valueOf(node.getClassName());
        final String text = String.valueOf(node.getText());
        StringBuffer sb = new StringBuffer();
        while (level-- > 0) {
            sb.append('\t');
        }
        Log.d(TAG, String.format("%sClass:%s, Text:%s", sb.toString(), clz, text));
    }

    public boolean traverseNode(int level, AccessibilityNodeInfo node) {
        if (null == node) {
            return false;
        }
        process(level, node);
        if (!m_cl.onTraversing(level, node)) {
            return false;
        }
        final int count = node.getChildCount();
        level += 1;
        for (int i = 0; i < count; ++i) {
            if (!traverseNode(level, node.getChild(i))) {
                return false;
            }
        }
        return true;
    }

    private void traverseWindow(AccessibilityEvent e) {
        Log.d(TAG, String.valueOf(e.getClassName()));
        AccessibilityNodeInfo node = getRootInActiveWindow();
        if (node == null) {
            Log.w(TAG, "rootWindow is null!");
            return;
        }
        traverseNode(0, node);
        m_cl.onTraversed();
    }

    private void jump(AccessibilityEvent e) {
        if (e.getParcelableData() == null || !(e.getParcelableData() instanceof Notification)) {
            return;
        }
        Notification notification = (Notification)e.getParcelableData();
        PendingIntent pendingIntent = notification.contentIntent;
        try {
            pendingIntent.send();
        } catch (PendingIntent.CanceledException exp) {
            exp.printStackTrace();
        }
    }

    private void peekNotification(AccessibilityEvent e) {
        if (null == e) {
            return;
        }
        List<CharSequence> txts = e.getText();
        if (null == txts) {
            return;
        }
        for (CharSequence cs : txts) {
            final String txt = String.valueOf(cs);
            Toast.makeText(this, txt, Toast.LENGTH_SHORT).show();
        }
    }

}
