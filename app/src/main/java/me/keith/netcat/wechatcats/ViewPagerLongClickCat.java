package me.keith.netcat.wechatcats;

import android.support.annotation.NonNull;

import me.keith.netcat.EmptyCat;
import me.keith.netcat.IServiceRequest;
import me.keith.netcat.IWinCat;
import me.keith.netcat.IWinCatLeader;

/**
 * Created by Keith on 2016/3/20.
 */
public class ViewPagerLongClickCat extends EmptyCat {

    private final IServiceRequest m_sr;

    public ViewPagerLongClickCat(@NonNull IWinCatLeader parent,
                                 @NonNull IWinCat next,
                                 @NonNull IServiceRequest sr) {
        super(parent, next);
        m_sr = sr;
    }

    @Override
    public boolean onNeedTraverse() {
        m_sr.back();
        m_parent.again();
        return false;
    }

    //    @Override
//    public boolean onTraversing(int level, AccessibilityNodeInfo node) {
//        if (level != 1) {
//            return true;
//        }
//
//        String clz = node.getClassName().toString();
//        if (FRAME_LAYOUT.equals(clz)) {
//            node.performAction(AccessibilityNodeInfo.ACTION_LONG_CLICK);
//            super.onTraversed();
//            return false;
//        }
//        return true;
//    }
}
