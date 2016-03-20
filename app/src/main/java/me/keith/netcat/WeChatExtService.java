package me.keith.netcat;

import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.app.PendingIntent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by Keith on 3/16/16.
 */
public class WeChatExtService extends AccessibilityService implements IServiceRequest {
    public static String TAG = "WeChatExtService";
    public static String WECHAT = "com.tencent.mm";

    private IWinCat m_cl = new HeadCat(this);

    static class DelayHandler extends Handler {
        public static final int DELAY_TRAVERSE = 90;
        public static final int DELAY_JUMP_NOTIFICATION = 91;
        private WeakReference<WeChatExtService> m_service;

        public DelayHandler(WeChatExtService service) {
            m_service = new WeakReference<WeChatExtService>(service);
        }

        @Override
        public void handleMessage(Message msg) {
            WeChatExtService service = m_service.get();
            switch (msg.what) {
                case DELAY_TRAVERSE:
                    if (null != service) {
                        AccessibilityEvent e = (AccessibilityEvent)msg.obj;
                        service.traverseWindow(e);
                    }
                    break;
                case DELAY_JUMP_NOTIFICATION:
                    if (null != service) {
                        AccessibilityEvent e = (AccessibilityEvent)msg.obj;
                        service.jump(e);
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    private DelayHandler m_handler = new DelayHandler(this);
    private AccessibilityEvent m_cache;

    @Override
    public void onInterrupt() {
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent e) {
        final int type = e.getEventType();
        m_cache = e;
        Log.d(TAG, "event coming:" + e);
        switch (type) {
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED: {
                peekNotification(e);
                //Message msg = m_handler.obtainMessage(DelayHandler.DELAY_JUMP_NOTIFICATION, e);
                //m_handler.sendMessageDelayed(msg, 1000);
                jump(e);
                break;
            }
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED: {
                Message msg = m_handler.obtainMessage(DelayHandler.DELAY_TRAVERSE, e);
                m_handler.sendMessageDelayed(msg, 300);
                //traverseWindow(e);
                break;
            }
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

    @Override
    public void traverse() {
        traverseWindow(m_cache);
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
        if (e != null) {
            Log.d(TAG, String.valueOf(e.getClassName()));
        }
        if (!m_cl.onNeedTraverse()) {
            return;
        }
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
