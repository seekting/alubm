
package com.seekting.haiyun.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.SweepGradient;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;

import com.seekting.haiyun.R;
import com.seekting.haiyun.activity.MainActivity;

public class HaiYunView extends SurfaceView implements Callback, OnClickListener {
    private SurfaceHolder holder;

    List<BitmapDrawable> pics;

    MyThread myThread;

    MediaPlayer mediaPlayer = new MediaPlayer();

    /**
     * 刷屏
     */
    long frame;

    /**
     * 刷新时间
     */
    int fps = 5;

    CD cd;

    public HaiYunView(Context context, AttributeSet attrs) {
        super(context, attrs);
        holder = this.getHolder();
        holder.addCallback(this);
        pics = new ArrayList<BitmapDrawable>();
        pics.add((BitmapDrawable)getResources().getDrawable(R.drawable.pic));

        mediaPlayer = MediaPlayer.create(context, R.raw.canon);
        setOnClickListener(this);
        mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {

                stop = true;
            }
        });

        myThread = new MyThread(holder);
        myThread.start();

    }

    boolean stop = true;

    public class MyThread extends Thread {
        SurfaceHolder holder;

        Text text = new Text();

        /**
         * 萤火虫 个数
         */
        int size = (int)(Math.random() * 50 + 30);

        int starSize = (int)(Math.random() * 10 + 5);

        List<ChongZhi> list;

        List<Star> stars;

        Pic pic;

        public MyThread(SurfaceHolder holder) {
            this.holder = holder;
            list = new ArrayList<HaiYunView.ChongZhi>();
            stars = new ArrayList<Star>();
            for (int i = 0; i < size; i++) {
                ChongZhi chongZhi = new ChongZhi();
                list.add(chongZhi);

            }

            for (int i = 0; i < starSize; i++) {
                Star chongZhi = new Star();
                stars.add(chongZhi);
            }
            pic = new Pic();
            cd = new CD();
        }

        @Override
        public void run() {

            while (true) {
                Canvas c = null;
                synchronized (holder) {
                    try {

                        frame++;
                        Thread.sleep(fps);
                        c = holder.lockCanvas();
                        if (c == null) {
                            continue;
                        }
                        c.drawColor(Color.BLACK);
                        pic.draw(c, frame, fps);
                        if (c != null) {
                            for (int i = 0; i < size; i++) {
                                ChongZhi chongZhi = list.get(i);

                                chongZhi.draw(c, frame, fps);
                            }
                            for (int i = 0; i < starSize; i++) {
                                Star star = stars.get(i);

                                star.draw(c, frame, fps);
                            }

                            text.draw(c, frame, fps);
                            cd.draw(c, frame, fps);

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (c != null) {
                            holder.unlockCanvasAndPost(c);
                        }
                    }
                }

            }
        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub

    }

    /**
     * 画萤火虫
     * 
     * @author seekting.x.zhang
     */
    public static class ChongZhi implements Draw {
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

        public ChongZhi() {
            paint.setAntiAlias(true);
            // 随机颜色的发光
            initColor();

            // 随机大小
            shixing = (int)(Math.random() * 10 + 5);
            //
            cycle = (int)(Math.random() * cycle_add + cycle_min);
            System.out.println("cycle" + cycle);
            System.out.println("screen_width" + MainActivity.screen_width);
            doudong = (float)((float)Math.random() * 0.5);

            changeOrication = (int)(Math.random() * 1000 + 500);
            outCanvas = (int)(Math.random() * 500 + 50);
            x = (float)(Math.random() * MainActivity.screen_width + 2 * outCanvas) - outCanvas;
            y = (float)(Math.random() * MainActivity.screen_height + 2 * outCanvas) - outCanvas;

        }

        void initColor() {

            int color = getChongZhiColor();
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
        public void draw(Canvas c, long frame, int fps) {

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
            if (x > MainActivity.screen_width + outCanvas) {
                ddx = Math.abs(ddx);
            }
            if (y > MainActivity.screen_height + outCanvas) {
                ddy = Math.abs(ddy);
            }

            x = (float)(x + Math.random() * doudong - ddx);
            y = (float)(y + Math.random() * doudong - ddy);

            if (x > MainActivity.screen_width + outCanvas || x < -outCanvas
                    || y > MainActivity.screen_height + outCanvas || y < -outCanvas) {
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

        public void draw(Canvas c, long frame, int fps) {

            LinearGradient lg = new LinearGradient(frame, frame, frame + 200, frame + 200,
                    Color.RED, Color.BLUE, Shader.TileMode.MIRROR);
            paint.setShader(lg);
            long time = frame * fps;

            // c.drawText("五一劳动节", MainActivity.screen_width / 2, 100, paint);
        }
    }

    class Star implements Draw {

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
            color = getRandomColor();

            yPadding = (int)(Math.random() * 400) - 200;

            chuxi = (float)(5 + Math.random() * 3);
            v = (float)(Math.random() + 4);
            length = (int)(50 + 30 * Math.random());
        }

        float currentCycle;

        @Override
        public void draw(Canvas c, long frame, int fps) {

            currentCycle = currentCycle + v;
            cycle = (MainActivity.screen_width + 2 * length / Math.cos(degree));

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

            LinearGradient linearGradient = new LinearGradient(x + xLength, y + yLength, x, y,
                    color, Color.argb(0, 0, 0, 0), TileMode.CLAMP);
            paint.setShader(linearGradient);
            c.drawArc(rectF, fangxiang, chuxi, true, paint);
            // c.drawCircle(x, y, 100, paint);
        }
    }

    public interface Draw {
        void draw(Canvas c, long frame, int fps);
    }

    public static int getRandomColor() {

        int red = 0;
        int blue = 0;
        int green = 0;
        int color = (int)(Math.random() * 5);
        switch (color) {

            case 0:
                // 主绿
                red = (int)(Math.random() * 100);
                green = 255;
                blue = (int)(Math.random() * 100);
                break;

            case 1:
                // 主红
                green = (int)(Math.random() * 100);
                red = 255;
                blue = (int)(Math.random() * 100);
                break;
            case 2:
                // 主蓝
                green = (int)(Math.random() * 100);
                blue = 255;
                red = (int)(Math.random() * 100);
                break;
            case 3:
                // 主黄
                blue = (int)(Math.random() * 100);
                green = 255;
                red = 255;

            case 4:
                // 主
                blue = (int)(Math.random() * 100);
                red = 255;
                green = 255;
                break;
        }

        return Color.argb(255, red, green, blue);

    }

    public static int getChongZhiColor() {

        int red = 0;
        int blue = 0;
        int green = 0;
        int color = (int)(Math.random() * 5);
        switch (0) {

            case 0:
                // 主绿
                red = (int)(Math.random() * 100);
                green = 255;
                blue = (int)(Math.random() * 100);
                break;

            case 1:
                // 主红
                green = (int)(Math.random() * 100);
                red = 255;
                blue = (int)(Math.random() * 100);
                break;
            case 2:
                // 主蓝
                green = (int)(Math.random() * 100);
                blue = 255;
                red = (int)(Math.random() * 100);
                break;
            case 3:
                // 主黄
                blue = (int)(Math.random() * 100);
                green = 255;
                red = 255;

            case 4:
                // 主黄
                blue = (int)(Math.random() * 100);
                red = 255;
                green = 255;
                break;
        }

        return Color.argb(255, red, green, blue);

    }

    class Pic implements Draw {
        Camera camera;

        Paint paint;

        Matrix matrix;

        Paint paint2;

        BitmapDrawable pic;

        int width = 250;

        int cycle = 200;

        int indexPic = 0;

        public Pic() {
            camera = new Camera();
            paint = new Paint();
            matrix = new Matrix();
            paint.setAntiAlias(true);
            paint2 = new Paint();
        }

        @Override
        public void draw(Canvas c, long frame, int fps) {
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
            c.translate(50, (float)(MainActivity.screen_height - width * 1.5));
            Bitmap b = pic.getBitmap();
            Bitmap min = Bitmap.createScaledBitmap(b, width, width, true);
            Matrix matrix1 = new Matrix();
            matrix1.preScale(1, -1);

            Bitmap min1 = Bitmap.createBitmap(min, 0, 0, width, width, matrix1, false);
            camera.rotateY(30);
            camera.rotateX(20);
            camera.getMatrix(matrix);
            c.drawBitmap(min, matrix, paint);
            // 画镜像
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

    class CD implements Draw {

        Paint paint;

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
            LinearGradient lg = new LinearGradient(frame, frame, frame + 200, frame + 200,
                    startColor, endColor, Shader.TileMode.MIRROR);
            textPaint.setShader(lg);
            Path path = new Path();
            path.addCircle(radius, radius, radius * 3 / 4, Direction.CCW);
            path.close();
            lg = new LinearGradient(frame, frame, frame + 200, frame + 200, Color.YELLOW,
                    Color.GREEN, Shader.TileMode.MIRROR);
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
            // 横
            canvas.drawLine(pad, radius, 2 * radius - pad, radius, paint2);
            // 竖
            canvas.drawLine(radius, pad, radius, 2 * radius - pad, paint2);

            // canvas.drawLine(radius, (int)0.25 * radius + pad, radius, 2 *
            // radius, paint2);
            // canvas.drawLine(0, 0, 2 * radius, 2 * radius, paint2);
            // canvas.drawLine(2 * radius, 0, 0, 2 * radius, paint2);

            // canvas.drawText(text, 100, 100, textPaint);
        }

        @Override
        public void draw(Canvas c, long frame, int fps) {
            radius = MainActivity.screen_width / 2;

            if (stop) {
            } else {
                degree = degree + 0.4f;
            }
            if (bitmap == null) {

                initBitmap();
            }
            if (degree >= 360) {
                bitmap.recycle();
                degree = 0;
                startColor = getRandomColor();
                endColor = getRandomColor();
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

    @Override
    protected void onDetachedFromWindow() {
        // TODO Auto-generated method stub
        super.onDetachedFromWindow();
        mediaPlayer.release();
    }

    float x;

    float y;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x = event.getX();

        y = event.getY();
        int width = MainActivity.screen_width;
        if (x > width / 2 && y < width / 2) {
        } else {
            return false;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View v) {

        if (stop) {
            stop = false;
            mediaPlayer.start();
        } else {
            stop = true;
            mediaPlayer.pause();
        }
    }
}
