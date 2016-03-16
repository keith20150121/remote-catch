package me.keith.netcat;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
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

    private void accessWindow(AccessibilityEvent e) {

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
