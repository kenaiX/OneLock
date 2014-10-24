package cc.kenai.onekeylock;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.kenai.function.lock.XLock;

public class LockActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new FrameLayout(this));
        XLock.lockNow(this);
        finish();
    }

    public final static class ICON1{

    }
    public final static class ICON2{

    }
    public final static class ICON3{

    }
}
