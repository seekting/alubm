
package com.seekting.haiyun.view.draw;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;

import com.seekting.haiyun.R;
import com.seekting.haiyun.view.Draw;

public class Pic implements Draw {
    List<BitmapDrawable> pics;

    Camera camera;

    Paint paint;

    Matrix matrix;

    Paint paint2;

    BitmapDrawable pic;

    int width = 250;

    int cycle = 200;

    int indexPic = 0;

    public Pic(Context context, int width, int height) {
        camera = new Camera();
        paint = new Paint();
        matrix = new Matrix();
        paint.setAntiAlias(true);
        paint2 = new Paint();
        pics = new ArrayList<BitmapDrawable>();
        pics.add((BitmapDrawable)context.getResources().getDrawable(R.drawable.pic));
    }

    @Override
    public void draw(Canvas c, long frame, int fps, int width, int height, boolean isPlaying) {
        matrix.reset();
        camera.save();

        int a = (int)(frame % cycle);
        pic = pics.get(indexPic);
        if (a == 0) {

            indexPic++;
            if (indexPic >= pics.size()) {
                indexPic = 0;
            }
            pic = pics.get(indexPic);
        }

        if (pic == null) {
            return;
        }
        c.save(Canvas.MATRIX_SAVE_FLAG);
        c.translate(50, (float)(height - width * 1.5));
        Bitmap b = pic.getBitmap();
        Bitmap min = Bitmap.createScaledBitmap(b, width, width, true);
        Matrix matrix1 = new Matrix();
        matrix1.preScale(1, -1);

        Bitmap min1 = Bitmap.createBitmap(min, 0, 0, width, width, matrix1, false);
        camera.rotateY(30);
        camera.rotateX(20);
        camera.getMatrix(matrix);
        c.drawBitmap(min, matrix, paint);
        // »­¾µÏñ
        c.translate(width / 5, width + 20);

        Paint paint2 = new Paint();
        matrix.reset();
        camera.restore();
        camera.save();
        camera.rotateY(30);
        camera.rotateX(0);
        camera.getMatrix(matrix);
        c.drawBitmap(min1, matrix, paint2);
        LinearGradient shader = new LinearGradient(100, 0, 100, width, 0x55ffffff, 0x00ffffff,
                TileMode.MIRROR);
        paint2.setShader(shader);
        paint2.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        c.drawRect(0, 0, width, width, paint2);
        paint2.setShader(null);
        c.restore();
        min.recycle();
        min1.recycle();
        camera.restore();
    }
}
