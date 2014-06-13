package simpleuserinputhook.windows.key;

import java.util.List;

import simpleuserinputhook.windows.listener.UserInputListener;

import com.sun.jna.platform.win32.WinDef.WPARAM;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinUser.KBDLLHOOKSTRUCT;

public class KeyHookHelper {

	public static void notifyKeyHook(List<UserInputListener> hookListeners, int nCode, WPARAM wParam, KBDLLHOOKSTRUCT info) {
		switch (wParam.intValue()) {
		case WinUser.WM_KEYUP:
			notifyKeyReleased(hookListeners, info.vkCode);
			break;
		case WinUser.WM_KEYDOWN:
			notifyKeyPressed(hookListeners, info.vkCode);
			break;
		default:
			break;
		}
	}

	private static void notifyKeyPressed(List<UserInputListener> hookListeners, int keyCode) {
		for (UserInputListener hookListener : hookListeners) {
			hookListener.keyPressed(keyCode);
		}
	}

	private static void notifyKeyReleased(List<UserInputListener> hookListeners, int keyCode) {
		for (UserInputListener hookListener : hookListeners) {
			hookListener.keyReleased(keyCode);
		}
	}

}
