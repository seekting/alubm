
package com.seekting.haiyun.view;

import android.graphics.Canvas;

public interface Draw {
    void draw(Canvas c, long frame, int fps, int width, int height,boolean isPlaying);
}
