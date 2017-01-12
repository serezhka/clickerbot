package com.github.serezhka.clickerbot.jna;

import com.github.serezhka.clickerbot.jna.lib.User32Ext;

import java.awt.*;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 */
public class MouseUtil {

    public static Point getCursorPos(Point point) {
        long[] lpPoint = new long[1];
        User32Ext.INSTANCE.GetCursorPos(lpPoint);
        point.setLocation(POINT_X(lpPoint[0]), POINT_Y(lpPoint[0]));
        return point;
    }

    private static int POINT_Y(long i) {
        return (int) (i >> 32);
    }

    private static int POINT_X(long i) {
        return (int) (i & 0xFFFF);
    }
}
