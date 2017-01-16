package com.github.serezhka.clickerbot.jna;

import com.sun.jna.platform.win32.BaseTSD;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 */
public class KeyboardUtil {

    public static void sendInput(int keyCode, long releaseDelay) throws InterruptedException {
        sendInput(keyCode, true);
        try {
            Thread.sleep(releaseDelay);
        } finally {
            sendInput(keyCode, false);
        }
    }

    public static void sendInput(int keyCode, boolean keyDown) {
        WinUser.INPUT input = new WinUser.INPUT();
        input.type = new WinDef.DWORD(WinUser.INPUT.INPUT_KEYBOARD);
        input.input.setType("ki");
        input.input.ki.wScan = new WinDef.WORD(0);
        input.input.ki.time = new WinDef.DWORD(0);
        input.input.ki.dwExtraInfo = new BaseTSD.ULONG_PTR(0);
        input.input.ki.wVk = new WinDef.WORD(keyCode);
        input.input.ki.dwFlags = new WinDef.DWORD(keyDown ? 0 : 2); // 0 - down ; 2 - release
        User32.INSTANCE.SendInput(new WinDef.DWORD(1), (WinUser.INPUT[]) input.toArray(1), input.size());
    }

    public static boolean isKeyPressed(int vKey) {
        return User32.INSTANCE.GetAsyncKeyState(vKey) < 0;
    }
}
