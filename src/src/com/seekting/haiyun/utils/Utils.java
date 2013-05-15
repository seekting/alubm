
package com.seekting.haiyun.utils;

import android.graphics.Color;

public class Utils {

    public static int getFireFlyColor() {

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

}
