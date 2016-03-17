package me.keith.netcat;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import java.util.List;

/**
 * Created by swd3 on 3/16/16.
 */
public class WeChatExtService extends AccessibilityService {
    public static String TAG = "WeChatExtService";
    public static String WECHAT = "com.tencent.mm";

    @Override
    public void onInterrupt() {

    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent e) {
        final int type = e.getEventType();
        Log.d(TAG, "event coming:" + e);
        switch (type) {
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                accessNotification(e);
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                accessWindow(e);
                break;
        }
    }

    public static void print(AccessibilityNodeInfo node) {
        Log.d(TAG, String.format("Class:%s, Text:%s",
                String.valueOf(node.getClassName()),
                String.valueOf(node.getText())));
    }

    public static void traverseNode(AccessibilityNodeInfo node) {
        print(node);
        final int count = node.getChildCount();
        for (int i = 0; i < count; ++i) {
            traverseNode(node.getChild(i));
        }
    }

    private void accessWindow(AccessibilityEvent e) {
        Log.d(TAG, String.valueOf(e.getClassName()));
        AccessibilityNodeInfo node = getRootInActiveWindow();
        if (node == null) {
            Log.w(TAG, "rootWindow is null!");
            return;
        }
        traverseNode(node);
        /*
        List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByText("领取红包");
        if(list.isEmpty()) {
            list = nodeInfo.findAccessibilityNodeInfosByText(HONGBAO_TEXT_KEY);
            for(AccessibilityNodeInfo n : list) {
                Log.i(TAG, "-->微信红包:" + n);
                n.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                break;
            }
        } else {
            //最新的红包领起
            for(int i = list.size() - 1; i >= 0; i --) {
                AccessibilityNodeInfo parent = list.get(i).getParent();
                Log.i(TAG, "-->领取红包:" + parent);
                if(parent != null) {
                    parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    break;
                }
            }
        }
        */
    }

    private void accessNotification(AccessibilityEvent e) {
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
