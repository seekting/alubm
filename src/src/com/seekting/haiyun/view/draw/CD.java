
package com.seekting.haiyun.view.draw;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Shader;

import com.seekting.haiyun.utils.Utils;
import com.seekting.haiyun.view.Draw;

public class CD implements Draw {

    Paint paint;

    long frame;

    int radius = 0;

    float currentCycle;

    int ox = radius * 2;

    String d = "  ";

    String text = "Happy Birthday!";

    Paint textPaint = new Paint();

    public CD() {

        paint = new Paint();
        paint.setAntiAlias(true);
        paint2.setAntiAlias(true);

        int textSize = 40;

        int textGradient = 2 * textSize;

        textPaint.setTextSize(textSize);
        textPaint.setColor(Color.WHITE);
        textPaint.setAntiAlias(true);
        paint2.setStrokeWidth(4);
        // textPaint.setTextAlign(Align.LEFT);

    }

    int cycle = 360;

    Bitmap bitmap;

    float degree;

    Paint paint2 = new Paint();

    Paint paint3 = new Paint();

    int startColor = Color.RED;

    int endColor = Color.BLUE;

    private void initBitmap() {
        bitmap = Bitmap.createBitmap(2 * radius, 2 * radius, Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        LinearGradient lg = new LinearGradient(frame, frame, frame + 200, frame + 200, startColor,
                endColor, Shader.TileMode.MIRROR);
        textPaint.setShader(lg);
        Path path = new Path();
        path.addCircle(radius, radius, radius * 3 / 4, Direction.CCW);
        path.close();
        lg = new LinearGradient(frame, frame, frame + 200, frame + 200, Color.YELLOW, Color.GREEN,
                Shader.TileMode.MIRROR);
        paint2.setStyle(Style.STROKE);
        paint2.setColor(Color.GRAY);
        canvas.drawTextOnPath(text, path, 1, 1, textPaint);
        int pad = radius / 2 + 30;

        int add = 4;
        paint3.setStyle(Style.STROKE);
        paint3.setColor(Color.argb(255, 200, 200, 200));
        paint3.setStrokeWidth(0.1f);
        paint3.setAntiAlias(true);
        int size = (radius * 3 / 4) / add;
        for (int i = 0; i < size; i++) {
            canvas.drawCircle(radius, radius, radius / 4 + i * add, paint3);
        }
        // ºá
        canvas.drawLine(pad, radius, 2 * radius - pad, radius, paint2);
        // Êú
        canvas.drawLine(radius, pad, radius, 2 * radius - pad, paint2);

        // canvas.drawLine(radius, (int)0.25 * radius + pad, radius, 2 *
        // radius, paint2);
        // canvas.drawLine(0, 0, 2 * radius, 2 * radius, paint2);
        // canvas.drawLine(2 * radius, 0, 0, 2 * radius, paint2);

        // canvas.drawText(text, 100, 100, textPaint);
    }

    @Override
    public void draw(Canvas c, long frame, int fps, int width, int height, boolean isPlaying) {
        radius = width / 2;
        this.frame = frame;

        if (!isPlaying) {
        } else {
            degree = degree + 0.4f;
        }
        if (bitmap == null) {

            initBitmap();
        }
        if (degree >= 360) {
            bitmap.recycle();
            degree = 0;
            startColor = Utils.getRandomColor();
            endColor = Utils.getRandomColor();
            initBitmap();

        }
        int currentCycle = (int)(frame % cycle);

        ox = radius * 2;
        paint.setColor(Color.WHITE);
        c.drawCircle(radius * 2, 0, radius, paint);
        int black = Color.argb(200, 0, 0, 0);
        paint.setColor(black);
        c.drawCircle(radius * 2, 0, radius / 2, paint);
        drawBitmap(c);
        int x = (int)(radius * Math.cos(currentCycle * Math.PI / 180f)) + ox;
        int y = (int)(radius * Math.sin(currentCycle * Math.PI / 180f));

        paint.setColor(Color.BLACK);
        c.drawCircle(radius * 2, 0, radius / 4, paint);

    }

    private void drawBitmap(Canvas c) {
        c.save();
        c.translate(radius, -radius);
        Matrix matrix = new Matrix();
        matrix.setTranslate(radius, radius);
        matrix.preRotate(degree);
        matrix.preTranslate(-radius, -radius);
        c.drawBitmap(bitmap, matrix, paint);
        c.restore();
    }
}
