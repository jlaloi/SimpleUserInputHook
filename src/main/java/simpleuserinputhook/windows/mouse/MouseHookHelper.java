package simpleuserinputhook.windows.mouse;

import java.util.List;

import simpleuserinputhook.windows.listener.HookListener;
import simpleuserinputhook.windows.listener.HookMouseEvent;
import simpleuserinputhook.windows.listener.HookMouseEvent.MouseEventButton;

import com.sun.jna.platform.win32.WinDef.WPARAM;

public class MouseHookHelper {

	public static final int WM_MOUSEMOVED = 512;
	public static final int WM_LBUTTONDOWN = 513;
	public static final int WM_LBUTTONUP = 514;
	public static final int WM_RBUTTONDOWN = 516;
	public static final int WM_RBUTTONUP = 517;
	public static final int WM_MBUTTONDOWN = 519;
	public static final int WM_MBUTTONUP = 520;
	public static final int WM_MBUTTONWHEEL = 522;

	public static void notifyMouseEvent(List<HookListener> hookListeners, int nCode, WPARAM wParam, MouseHookStructure mouseHookStructure) {
		int x = mouseHookStructure.point.x;
		int y = mouseHookStructure.point.y;
		switch (wParam.intValue()) {
		case WM_MOUSEMOVED:
			notifyMouseMoved(hookListeners, new HookMouseEvent(MouseEventButton.middle, x, y));
			break;
		case WM_LBUTTONDOWN:
			notifyMousePressed(hookListeners, new HookMouseEvent(MouseEventButton.left, x, y));
			break;
		case WM_LBUTTONUP:
			notifyMouseReleased(hookListeners, new HookMouseEvent(MouseEventButton.left, x, y));
			break;
		case WM_RBUTTONDOWN:
			notifyMousePressed(hookListeners, new HookMouseEvent(MouseEventButton.right, x, y));
			break;
		case WM_RBUTTONUP:
			notifyMouseReleased(hookListeners, new HookMouseEvent(MouseEventButton.right, x, y));
			break;
		case WM_MBUTTONDOWN:
			notifyMousePressed(hookListeners, new HookMouseEvent(MouseEventButton.middle, x, y));
			break;
		case WM_MBUTTONUP:
			notifyMouseReleased(hookListeners, new HookMouseEvent(MouseEventButton.middle, x, y));
			break;
		case WM_MBUTTONWHEEL:
			if (mouseHookStructure.mouseData.intValue() >= 0) {
				notifyMouseWheelUp(hookListeners, new HookMouseEvent(MouseEventButton.middle, x, y));
			} else {
				notifyMouseWheelDown(hookListeners, new HookMouseEvent(MouseEventButton.middle, x, y));
			}
			break;
		default:
			break;
		}
	}

	private static void notifyMouseWheelDown(List<HookListener> hookListeners, HookMouseEvent hookMouseEvent) {
		for (HookListener hookListener : hookListeners) {
			hookListener.mouseWheelDown(hookMouseEvent);
		}
	}

	private static void notifyMouseWheelUp(List<HookListener> hookListeners, HookMouseEvent hookMouseEvent) {
		for (HookListener hookListener : hookListeners) {
			hookListener.mouseWheelUp(hookMouseEvent);
		}
	}

	private static void notifyMouseMoved(List<HookListener> hookListeners, HookMouseEvent hookMouseEvent) {
		for (HookListener hookListener : hookListeners) {
			hookListener.mouseMouved(hookMouseEvent);
		}
	}

	private static void notifyMouseReleased(List<HookListener> hookListeners, HookMouseEvent hookMouseEvent) {
		for (HookListener hookListener : hookListeners) {
			hookListener.mouseReleased(hookMouseEvent);
		}
	}

	private static void notifyMousePressed(List<HookListener> hookListeners, HookMouseEvent hookMouseEvent) {
		for (HookListener hookListener : hookListeners) {
			hookListener.mousePressed(hookMouseEvent);
		}
	}

}
