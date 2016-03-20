package me.keith.netcat;

/**
 * Created by Keith on 3/18/16.
 */
public class CatLeader {/* extends EmptyCat implements IWinCatLeader {
    public final static String TAG = "CatsLoader";

    private String m_Receiver;
    private ArrayList<IWinCat> m_cats = new ArrayList<>();
    private int m_current;
    private int COUNT;
    private IServiceRequest m_sr;
    private Object m_arg;

    // TODO: 3/18/16 international
    private final static String REPOST = "转发";

    public CatLeader(@NonNull IServiceRequest sr) {
        m_cats.add(new ChatLogCat(this, sr));
        m_cats.add(new MenuClickCat(this, REPOST, true));
        m_cats.add(new MenuClickCat(this, "Keith", true));
        m_cats.add(new MenuClickCat(this, "发送", false));
        m_cats.add(new GlobalHomeCat(sr));
        COUNT = m_cats.size();
        m_current = 0;
        m_sr = sr;
    }

    public void setTransferTarget(final String target) {
        m_Receiver = target;
    }

    private void relay() {
        m_current = m_current < COUNT ? m_current + 1 : 0;
    }

    @Override
    public boolean onNeedTraverse() {
        if (m_current >= COUNT) {
            relay();
        }
        IWinCat cat = m_cats.get(m_current);
        cat.onArgPassed(m_arg);
        m_arg = null;
        Log.d(TAG, "NeedTraverse:Cat:" + m_current);
        boolean result = cat.onNeedTraverse();
        if (!result) {
            relay();
        }
        return result;
    }

    @Override
    public boolean onTraversing(int level, AccessibilityNodeInfo node) {
        if (m_current >= COUNT) {
            relay();
        }
        Log.d(TAG, "Traversing:Cat:" + m_current);
        return m_cats.get(m_current).onTraversing(level, node);
    }

    @Override
    public void onTraversed() {
        if (m_current >= COUNT) {
            return;
        }
        Log.d(TAG, "Traversed:Cat:" + m_current);
        m_cats.get(m_current).onTraversed();
        relay();
    }

    @Override
    public void onTypeWindowStateChanged(AccessibilityNodeInfo node) {
        if (m_current >= COUNT) {
            relay();
        }
        m_cats.get(m_current).onTypeWindowStateChanged(node);
    }

    @Override
    public void populate() {
        if (m_current >= COUNT) {
            relay();
        }
        m_cats.get(m_current).populate();
    }

    @Override
    public void next() {
        relay();
    }

    @Override
    public void previous() {
        m_current = m_current > 0 ? m_current - 1 : 0;
        populate();
    }

    @Override
    public void pass(Object obj) {
        m_arg = obj;
    }
    */
}
