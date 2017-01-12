package com.github.serezhka.clickerbot.jna.lib;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.win32.W32APIOptions;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 */
public interface GDI32Ext extends W32APIOptions {

    GDI32Ext INSTANCE = (GDI32Ext) Native.loadLibrary("gdi32", GDI32Ext.class, W32APIOptions.DEFAULT_OPTIONS);

    int GetPixel(WinDef.HDC hDC, int nXPos, int nYPos);
}
