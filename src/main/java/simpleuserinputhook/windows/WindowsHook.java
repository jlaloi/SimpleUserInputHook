package simpleuserinputhook.windows;

import java.util.ArrayList;
import java.util.List;

import simpleuserinputhook.windows.key.KeyHookHelper;
import simpleuserinputhook.windows.listener.UserInputListener;
import simpleuserinputhook.windows.mouse.MouseHookHelper;
import simpleuserinputhook.windows.mouse.MouseHookStructure;

import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.LRESULT;
import com.sun.jna.platform.win32.WinDef.WPARAM;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinUser.HHOOK;
import com.sun.jna.platform.win32.WinUser.HOOKPROC;
import com.sun.jna.platform.win32.WinUser.KBDLLHOOKSTRUCT;
import com.sun.jna.platform.win32.WinUser.LowLevelKeyboardProc;
import com.sun.jna.platform.win32.WinUser.MSG;

public class WindowsHook {

	private static User32 user32;
	private static HHOOK hhookMouse, hhookKey;
	private static List<UserInputListener> hookListeners = new ArrayList<UserInputListener>();

	private boolean isHooking = false;
	private boolean initialised = false;

	public void init() throws Exception {
		if (!Platform.isWindows()) {
			throw new Exception("Unsupported OS");
		}
		if (user32 == null) {
			user32 = User32.INSTANCE;
		}
		Native.setProtected(true);
		initialised = true;
		notifyInitialised();
	}

	public void startHook() throws Exception {
		if (!initialised) {
			throw new Exception("Not initialised");
		}
		if (isHooking) {
			throw new Exception("Already started");
		}
		Thread mousehook_thread = new Thread(new Runnable() {
			public void run() {
				try {
					if (!isHooking) {
						hhookKey = user32.SetWindowsHookEx(WinUser.WH_KEYBOARD_LL, keyHook(), Kernel32.INSTANCE.GetModuleHandle(null), 0);
						hhookMouse = user32.SetWindowsHookEx(User32.WH_MOUSE_LL, mouseHook(), Kernel32.INSTANCE.GetModuleHandle(null), 0);
						isHooking = true;
						notifyStarted();
						MSG msg = new MSG();
						while ((user32.GetMessage(msg, null, 0, 0)) != 0) {
							user32.TranslateMessage(msg);
							user32.DispatchMessage(msg);
							if (!isHooking) {
								break;
							}
							Thread.sleep(10);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		mousehook_thread.start();
	}

	public void stopHook() throws Exception {
		if (!initialised) {
			throw new Exception("Not initialised");
		}
		if (!isHooking) {
			throw new Exception("Not started");
		}
		isHooking = false;
		user32.UnhookWindowsHookEx(hhookMouse);
		user32.UnhookWindowsHookEx(hhookKey);
		notifyStopped();
	}

	private interface LowLevelMouseProc extends HOOKPROC {
		LRESULT callback(int nCode, WPARAM wParam, MouseHookStructure mouseHookStructure);
	}

	private static LowLevelMouseProc mouseHook() {
		return new LowLevelMouseProc() {
			public LRESULT callback(int nCode, WPARAM wParam, MouseHookStructure mouseHookStructure) {
				if (nCode >= 0) {
					MouseHookHelper.notifyMouseEvent(hookListeners, nCode, wParam, mouseHookStructure);
				}
				return user32.CallNextHookEx(hhookMouse, nCode, wParam, mouseHookStructure.getPointer());
			}
		};
	}

	private static LowLevelKeyboardProc keyHook() {
		return new LowLevelKeyboardProc() {
			public LRESULT callback(int nCode, WPARAM wParam, KBDLLHOOKSTRUCT info) {
				if (nCode >= 0) {
					KeyHookHelper.notifyKeyHook(hookListeners, nCode, wParam, info);
				}
				return user32.CallNextHookEx(hhookKey, nCode, wParam, info.getPointer());
			}
		};
	}

	public void setUser32(User32 user32) {
		WindowsHook.user32 = user32;
	}

	public boolean isHooking() {
		return isHooking;
	}

	public static void addHookListener(UserInputListener hookListener) {
		hookListeners.add(hookListener);
	}

	public static boolean removeHookListener(UserInputListener hookListener) {
		return hookListeners.remove(hookListener);
	}

	private static void notifyInitialised() {
		for (UserInputListener hookListener : hookListeners) {
			hookListener.hookInitialised();
		}
	}

	private static void notifyStopped() {
		for (UserInputListener hookListener : hookListeners) {
			hookListener.hookStopped();
		}
	}

	private static void notifyStarted() {
		for (UserInputListener hookListener : hookListeners) {
			hookListener.hookStared();
		}
	}

}
