package com.github.serezhka.clickerbot.jna;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.*;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 */
public class KeyboardUtil {

    public static void sendInput(int keyCode, long releaseDelay) {
        sendInput(keyCode, true);
        try {
            Thread.sleep(releaseDelay);
        } catch (InterruptedException ignored) {
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

    public static class KeyboardHook extends Thread implements WinUser.LowLevelKeyboardProc {

        private final KeyProcessor keyProcessor;
        private WinUser.HHOOK hhk;

        public KeyboardHook(KeyProcessor keyProcessor) {
            this.keyProcessor = keyProcessor;
        }

        @Override
        public void run() {
            hhk = User32.INSTANCE.SetWindowsHookEx(WinUser.WH_KEYBOARD_LL, this, Kernel32.INSTANCE.GetModuleHandle(null), 0);
            int result;
            WinUser.MSG msg = new WinUser.MSG();
            while ((result = User32.INSTANCE.GetMessage(msg, null, 0, 0)) != 0) {
                if (result == -1) {
                    break;
                } else {
                    User32.INSTANCE.TranslateMessage(msg);
                    User32.INSTANCE.DispatchMessage(msg);
                }
            }
            User32.INSTANCE.UnhookWindowsHookEx(hhk);
        }

        @Override
        public WinDef.LRESULT callback(int nCode, WinDef.WPARAM wParam, WinUser.KBDLLHOOKSTRUCT lParam) {
            if (nCode >= 0 && WinUser.WM_KEYDOWN == wParam.intValue()) {
                keyProcessor.onKeyPress(lParam.vkCode);
            }
            long peer = Pointer.nativeValue(lParam.getPointer());
            return User32.INSTANCE.CallNextHookEx(hhk, nCode, wParam, new WinDef.LPARAM(peer));
        }

        @FunctionalInterface
        public interface KeyProcessor {
            void onKeyPress(int keyCode);
        }
    }
}
