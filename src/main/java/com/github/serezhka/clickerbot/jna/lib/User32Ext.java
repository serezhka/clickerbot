package com.github.serezhka.clickerbot.jna.lib;

import com.sun.jna.Native;
import com.sun.jna.win32.W32APIOptions;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 */
public interface User32Ext extends W32APIOptions {

    User32Ext INSTANCE = (User32Ext) Native.loadLibrary("user32", User32Ext.class, W32APIOptions.DEFAULT_OPTIONS);

    boolean GetCursorPos(long[] lpPoint);

    boolean BlockInput(boolean fBlockIt);
}
