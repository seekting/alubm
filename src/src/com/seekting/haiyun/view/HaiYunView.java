
package com.seekting.haiyun.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
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
import com.seekting.haiyun.view.draw.CD;
import com.seekting.haiyun.view.draw.FireFly;
import com.seekting.haiyun.view.draw.Pic;
import com.seekting.haiyun.view.draw.Star;
import com.seekting.haiyun.view.draw.Text;

public class HaiYunView extends SurfaceView implements Callback, OnClickListener {
    private SurfaceHolder holder;

    /**
     * 相册需要展示的图片List
     */
    List<BitmapDrawable> pics;

    /**
     * 控制重绘的Thread
     */
    MyThread myThread;

    /**
     * mp3播放器
     */
    MediaPlayer mediaPlayer = new MediaPlayer();

    /**
     * 目前处在的帧数
     */
    long frame;

    /**
     * 刷新频率(毫秒/次)
     */
    int fps = 5;

    /**
     * 画cd的对象元素
     */
    CD cd;

    public HaiYunView(Context context, AttributeSet attrs) {
        super(context, attrs);
        holder = this.getHolder();
        holder.addCallback(this);

        mediaPlayer = MediaPlayer.create(context, R.raw.canon);
        setOnClickListener(this);
        mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {

                isPlaying = false;
            }
        });

        myThread = new MyThread(holder);
        myThread.start();

        MainActivity mainActivity = (MainActivity)context;
        screen_width = mainActivity.absolute_screen_width;
        screen_height = mainActivity.absolute_screen_height;

    }

    int screen_width;

    int screen_height;

    boolean isPlaying = false;

    public class MyThread extends Thread {
        SurfaceHolder holder;

        Text text = new Text();

        /**
         * 萤火虫 个数
         */
        int size = (int)(Math.random() * 50 + 30);

        /**
         * 流星数量
         */
        int starSize = (int)(Math.random() * 10 + 5);

        List<FireFly> list;

        List<Star> stars;

        Pic pic;

        public MyThread(SurfaceHolder holder) {
            this.holder = holder;
            list = new ArrayList<FireFly>();
            stars = new ArrayList<Star>();
            for (int i = 0; i < size; i++) {
                FireFly fireFly = new FireFly(getContext(), screen_width, screen_height);
                list.add(fireFly);

            }

            for (int i = 0; i < starSize; i++) {
                Star chongZhi = new Star();
                stars.add(chongZhi);
            }
            pic = new Pic(getContext(), screen_width, screen_height);
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
                        if (c != null) {
                            drawElements(c);

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

        /**
         * 画所有的元素
         * 
         * @param c
         */
        private void drawElements(Canvas c) {
            pic.draw(c, frame, fps, screen_width, screen_height, isPlaying);
            for (int i = 0; i < size; i++) {
                FireFly chongZhi = list.get(i);

                chongZhi.draw(c, frame, fps, screen_width, screen_height, isPlaying);
            }
            for (int i = 0; i < starSize; i++) {
                Star star = stars.get(i);

                star.draw(c, frame, fps, screen_width, screen_height, isPlaying);
            }

            text.draw(c, frame, fps, screen_width, screen_height, isPlaying);
            cd.draw(c, frame, fps, screen_width, screen_height, isPlaying);
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
        int width = screen_width;
        if (x > width / 2 && y < width / 2) {
        } else {
            return false;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View v) {

        if (isPlaying) {
            isPlaying = true;
            mediaPlayer.start();
        } else {
            isPlaying = false;
            mediaPlayer.pause();
        }
    }
}
