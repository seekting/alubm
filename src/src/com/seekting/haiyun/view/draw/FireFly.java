
package com.seekting.haiyun.view.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;

import com.seekting.haiyun.activity.MainActivity;
import com.seekting.haiyun.utils.Utils;
import com.seekting.haiyun.view.Draw;

/**
 * 画萤火虫 Amanda
 * 
 * @author seekting.x.zhang
 */
public class FireFly implements Draw {
    Paint paint = new Paint();

    /**
     * 萤火半径
     */
    int shixing = 5;

    int colorStart = Color.argb(255, 255, 255, 200);

    int colorEnd = Color.argb(0, 0, 0, 0);

    /**
     * 暗量周期
     */
    int cycle;

    /**
     * 最小周期
     */
    int cycle_min = 5000;

    /**
     * 最大周期为cycle_main+cycle_add
     */
    int cycle_add = 5000;

    /**
     * 最小量度
     */
    int liangdu_min = 50;

    int red;

    int green;

    int blue;

    float x;

    Path path;

    float y;

    public FireFly(Context context, int width, int height) {
        paint.setAntiAlias(true);
        // 随机颜色的发光
        initColor();

        // 随机大小
        shixing = (int)(Math.random() * 10 + 5);
        //
        cycle = (int)(Math.random() * cycle_add + cycle_min);
        System.out.println("cycle" + cycle);
        System.out.println("screen_width" + width);
        doudong = (float)((float)Math.random() * 0.5);

        changeOrication = (int)(Math.random() * 1000 + 500);
        outCanvas = (int)(Math.random() * 500 + 50);
        x = (float)(Math.random() * width + 2 * outCanvas) - outCanvas;
        y = (float)(Math.random() * width + 2 * outCanvas) - outCanvas;

    }

    void initColor() {

        int color = Utils.getFireFlyColor();
        red = Color.red(color);
        green = Color.green(color);
        blue = Color.blue(color);

    }

    /**
     * 运动趋势x
     */
    float ddx = 0;

    /**
     * 运动趋势y
     */
    float ddy = 0;

    /**
     * 改变运动状态的帧数
     */
    int change = 500;

    boolean needChange = false;

    /**
     * 允许离开画布的距离
     */
    int outCanvas = 50;

    /**
     * 抖动
     */
    float doudong = 0.4f;

    /**
     * 改变运动方向的频率
     */
    int changeOrication = 500;

    @Override
    public void draw(Canvas c, long frame, int fps, int width, int height, boolean isPlaying) {

        if (frame % changeOrication == 0) {

            ddx = (float)(Math.random() * 0.5 + 0.3);
            ddy = (float)(Math.random() * 0.5 + 0.3);
        }
        // 如果走出画布就尽快让它走进画布
        if (x < 0 - outCanvas) {
            ddx = -Math.abs(ddx);
        }
        // 如果走出画布就尽快让它走进画布
        if (y < -outCanvas) {
            ddy = -Math.abs(ddy);

        }
        if (x > width + outCanvas) {
            ddx = Math.abs(ddx);
        }
        if (y > height + outCanvas) {
            ddy = Math.abs(ddy);
        }

        x = (float)(x + Math.random() * doudong - ddx);
        y = (float)(y + Math.random() * doudong - ddy);

        if (x > width + outCanvas || x < -outCanvas || y > height + outCanvas || y < -outCanvas) {
        } else {
        }
        double time = frame * fps;

        // 生命周期转成0-cycle之间
        double cc = time % cycle;

        // 在中间的时候最亮，两边的时候不亮
        int alpha = 0;
        if (cc >= cycle / 3 && cc <= cycle * 2 / 3) {
            alpha = 255;
        } else if (cc < cycle / 3d) {

            alpha = (int)((cc * 3d / cycle) * (255 - liangdu_min)) + liangdu_min;
        } else if (cc > 2 * cycle / 3d) {
            cc = Math.abs(cc - 2 * cycle / 3d);
            alpha = 255 - (int)((cc * 3 / cycle) * (255 - liangdu_min) + liangdu_min);
        }
        colorStart = Color.argb(alpha, red, green, blue);
        RadialGradient mRadialGradient = new RadialGradient(x, y, shixing, new int[] {
                colorStart, colorEnd
        }, null, Shader.TileMode.MIRROR);
        SweepGradient sweepGradient = new SweepGradient(x, y, colorStart, colorEnd);
        paint.setShader(mRadialGradient);

        c.drawCircle(x, y, shixing, paint);

    }
}
