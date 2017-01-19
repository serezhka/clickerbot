package com.github.serezhka.clickerbot.jna;

import com.github.serezhka.clickerbot.jna.lib.GDI32Ext;
import com.sun.jna.platform.win32.GDI32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 */
public class DisplayUtil {

    private static WinDef.HDC HDC = User32.INSTANCE.GetDC(null);

    public static int getPixelColor(int x, int y) {
        return GDI32Ext.INSTANCE.GetPixel(HDC, x, y);
    }

    public static int setPixelColor(int x, int y, int color) {
        return GDI32.INSTANCE.SetPixel(HDC, x, y, color);
    }
}
