
package com.seekting.haiyun.view.draw;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;

import com.seekting.haiyun.activity.MainActivity;
import com.seekting.haiyun.utils.Utils;
import com.seekting.haiyun.view.Draw;

public class Star implements Draw {

    int fangxiang = 30;

    float degree = (float)(Math.PI * fangxiang / 180f);

    Paint paint;

    double cycle;

    float x;

    float y;

    int color;

    /**
     * 流星的长度
     */
    int length = 100;

    /**
     * y偏移
     */
    int yPadding = 100;

    /**
     * 轨迹粗线
     */
    float chuxi = 3;

    /**
     * 流星滑过的速度
     */
    float v = 1;

    int startFrame = 1000;

    public Star() {
        paint = new Paint();
        paint.setAntiAlias(true);
        startFrame = (int)(500 * Math.random());
        changeStyle();

    }

    public void changeStyle() {
        color = Utils.getRandomColor();

        yPadding = (int)(Math.random() * 400) - 200;

        chuxi = (float)(5 + Math.random() * 3);
        v = (float)(Math.random() + 4);
        length = (int)(50 + 30 * Math.random());
    }

    float currentCycle;

    @Override
    public void draw(Canvas c, long frame, int fps, int width, int height, boolean isPlaying) {

        currentCycle = currentCycle + v;
        cycle = (width + 2 * length / Math.cos(degree));

        // 每个周期换一种风格
        if (currentCycle >= cycle) {
            changeStyle();
            currentCycle = 0;

        }

        x = (float)(currentCycle * Math.cos(degree)) - length;
        y = (float)(currentCycle * Math.sin(degree)) - length + yPadding;

        float xLength = (float)(length * Math.cos(degree));
        float yLength = (float)(length * Math.sin(degree));
        RectF rectF = new RectF(x, y, x + length, y + length);
        // RadialGradient mRadialGradient = new RadialGradient(x + length, y
        // + length, length,
        // new int[] {
        // color, Color.argb(0, 0, 0, 0)
        // }, null, Shader.TileMode.CLAMP);

        LinearGradient linearGradient = new LinearGradient(x + xLength, y + yLength, x, y, color,
                Color.argb(0, 0, 0, 0), TileMode.CLAMP);
        paint.setShader(linearGradient);
        c.drawArc(rectF, fangxiang, chuxi, true, paint);
        // c.drawCircle(x, y, 100, paint);
    }
}
