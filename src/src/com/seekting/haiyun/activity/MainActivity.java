
package com.seekting.haiyun.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

import com.seekting.haiyun.R;
import com.seekting.haiyun.base.BaseActivity;

public class MainActivity extends BaseActivity {

    public static int screen_width;

    public static int screen_height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager windowManager = getWindowManager();
        screen_width = Math.min(windowManager.getDefaultDisplay().getWidth(), windowManager
                .getDefaultDisplay().getHeight());
        screen_height = Math.max(windowManager.getDefaultDisplay().getWidth(), windowManager
                .getDefaultDisplay().getHeight());
        setContentView(R.layout.activity_main);

    }

}
