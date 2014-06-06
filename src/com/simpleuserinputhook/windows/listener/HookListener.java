package com.simpleuserinputhook.windows.listener;

public interface HookListener {

	public void mouseReleased(HookMouseEvent mouseEventButton);

	public void mousePressed(HookMouseEvent mouseEventButton);

	public void mouseMouved(HookMouseEvent mouseEventButton);

	public void mouseWheelUp(HookMouseEvent mouseEventButton);

	public void mouseWheelDown(HookMouseEvent mouseEventButton);

	public void keyReleased(int keyCode);

	public void keyPressed(int keyCode);

	public void hookInitialised();

	public void hookStopped();

	public void hookStared();

}
