
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
 * ��ө��� Amanda
 * 
 * @author seekting.x.zhang
 */
public class FireFly implements Draw {
    Paint paint = new Paint();

    /**
     * ө��뾶
     */
    int shixing = 5;

    int colorStart = Color.argb(255, 255, 255, 200);

    int colorEnd = Color.argb(0, 0, 0, 0);

    /**
     * ��������
     */
    int cycle;

    /**
     * ��С����
     */
    int cycle_min = 5000;

    /**
     * �������Ϊcycle_main+cycle_add
     */
    int cycle_add = 5000;

    /**
     * ��С����
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
        // �����ɫ�ķ���
        initColor();

        // �����С
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
     * �˶�����x
     */
    float ddx = 0;

    /**
     * �˶�����y
     */
    float ddy = 0;

    /**
     * �ı��˶�״̬��֡��
     */
    int change = 500;

    boolean needChange = false;

    /**
     * �����뿪�����ľ���
     */
    int outCanvas = 50;

    /**
     * ����
     */
    float doudong = 0.4f;

    /**
     * �ı��˶������Ƶ��
     */
    int changeOrication = 500;

    @Override
    public void draw(Canvas c, long frame, int fps, int width, int height, boolean isPlaying) {

        if (frame % changeOrication == 0) {

            ddx = (float)(Math.random() * 0.5 + 0.3);
            ddy = (float)(Math.random() * 0.5 + 0.3);
        }
        // ����߳������;��������߽�����
        if (x < 0 - outCanvas) {
            ddx = -Math.abs(ddx);
        }
        // ����߳������;��������߽�����
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

        // ��������ת��0-cycle֮��
        double cc = time % cycle;

        // ���м��ʱ�����������ߵ�ʱ����
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
