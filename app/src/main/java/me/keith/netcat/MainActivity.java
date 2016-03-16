package me.keith.netcat;

import android.app.Instrumentation;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread () {
            public void run () {
                try {
                    Instrumentation inst = new Instrumentation();
                    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_VOLUME_DOWN);
                    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_POWER);

                    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_VOLUME_DOWN);
                    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_POWER);

                    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_VOLUME_DOWN);
                    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_POWER);

                    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_VOLUME_DOWN);
                    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_POWER);
                    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_VOLUME_DOWN);
                    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_POWER);

                } catch(Exception e) {
                    Log.e("netcat", e.toString());
                }
            }
        }.start();

    }
}
