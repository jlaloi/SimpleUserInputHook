package simpleuserinputhook.windows.listener;

public interface HookListener {

	public void mouseReleased(HookMouseEvent hookMouseEvent);

	public void mousePressed(HookMouseEvent hookMouseEvent);

	public void mouseMouved(HookMouseEvent hookMouseEvent);

	public void mouseWheelUp(HookMouseEvent hookMouseEvent);

	public void mouseWheelDown(HookMouseEvent hookMouseEvent);

	public void keyReleased(int keyCode);

	public void keyPressed(int keyCode);

	public void hookInitialised();

	public void hookStopped();

	public void hookStared();

}
