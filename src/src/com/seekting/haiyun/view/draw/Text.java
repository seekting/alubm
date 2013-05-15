
package com.seekting.haiyun.view.draw;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Paint.Align;

import com.seekting.haiyun.view.Draw;

public class Text implements Draw {

    Paint paint = new Paint();

    int textSize = 50;

    int textGradient = 2 * textSize;

    public Text() {
        paint.setTextSize(textSize);
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setTextAlign(Align.CENTER);

    }

    public void draw(Canvas c, long frame, int fps, int width, int height, boolean isPlaying) {

        LinearGradient lg = new LinearGradient(frame, frame, frame + 200, frame + 200, Color.RED,
                Color.BLUE, Shader.TileMode.MIRROR);
        paint.setShader(lg);
        long time = frame * fps;

        // c.drawText("五一劳动节", MainActivity.screen_width / 2, 100, paint);
    }
}
