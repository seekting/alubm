
package com.seekting.haiyun.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.Toast;

import com.seekting.haiyun.utils.LogManager;
import com.umeng.analytics.MobclickAgent;

public class BaseActivity extends Activity {

    protected Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        MobclickAgent.onError(this);
        handler = new Handler();
    }

    @Override
    protected void onResume() {
        LogManager.d(this.getClass().getSimpleName() + " onResume() invoked!!");
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        LogManager.d(this.getClass().getSimpleName() + " onPause() invoked!!");
        super.onPause();
        MobclickAgent.onPause(this);
    }

    protected void showLongToast(final String pMsg) {
        this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(BaseActivity.this, pMsg, Toast.LENGTH_LONG).show();
            }
        });

    }

    protected void showShortToast(final String pMsg) {
        this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(BaseActivity.this, pMsg, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
