
package com.seekting.haiyun.base;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.seekting.haiyun.utils.LogManager;
import com.umeng.analytics.MobclickAgent;

public class BaseActivity extends Activity {

    protected Handler handler;

    public int screen_width;

    public int screen_height;

    public int absolute_screen_width;

    public int absolute_screen_height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager windowManager = getWindowManager();
        screen_width = Math.min(windowManager.getDefaultDisplay().getWidth(), windowManager
                .getDefaultDisplay().getHeight());
        screen_height = Math.max(windowManager.getDefaultDisplay().getWidth(), windowManager
                .getDefaultDisplay().getHeight());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        MobclickAgent.onError(this);
        handler = new Handler();
        absolute_screen_width = windowManager.getDefaultDisplay().getWidth();
        absolute_screen_height = windowManager.getDefaultDisplay().getHeight();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
        WindowManager windowManager = getWindowManager();
        absolute_screen_width = windowManager.getDefaultDisplay().getWidth();
        absolute_screen_height = windowManager.getDefaultDisplay().getHeight();

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
