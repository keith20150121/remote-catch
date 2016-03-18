package me.keith.netcat.wechatcats;

import android.support.annotation.NonNull;
import android.view.accessibility.AccessibilityNodeInfo;

import me.keith.netcat.EmptyCat;
import me.keith.netcat.IServiceRequest;

/**
 * Created by swd3 on 3/18/16.
 */
public class GlobalHomeCat extends EmptyCat {

    private IServiceRequest m_sr;

    public GlobalHomeCat(@NonNull IServiceRequest sr) {
        m_sr = sr;
    }

    @Override
    public boolean onTraversing(int level, AccessibilityNodeInfo node) {
        m_sr.home();
        return false;
    }
}
