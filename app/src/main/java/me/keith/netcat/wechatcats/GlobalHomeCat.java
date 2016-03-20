package me.keith.netcat.wechatcats;

import android.support.annotation.NonNull;

import me.keith.netcat.EmptyCat;
import me.keith.netcat.IServiceRequest;
import me.keith.netcat.IWinCat;
import me.keith.netcat.IWinCatLeader;

/**
 * Created by swd3 on 3/18/16.
 */
public class GlobalHomeCat extends EmptyCat {

    private IServiceRequest m_sr;

    public GlobalHomeCat(@NonNull IWinCatLeader parent,
                         @NonNull IWinCat next,
                         @NonNull IServiceRequest sr) {
        super(parent, next);
        m_sr = sr;
    }

    @Override
    public boolean onNeedTraverse() {
        m_sr.home();
        super.onTraversed();
        return false;
    }
}
