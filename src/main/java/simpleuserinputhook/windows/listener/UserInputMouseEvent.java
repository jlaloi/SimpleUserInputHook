package simpleuserinputhook.windows.listener;

public class UserInputMouseEvent {

	private MouseEventButton mouseEventButton;
	private int x;
	private int y;

	public enum MouseEventButton {
		left, middle, right;
	}

	public UserInputMouseEvent(MouseEventButton mouseEventButton, int x, int y) {
		super();
		this.mouseEventButton = mouseEventButton;
		this.x = x;
		this.y = y;
	}

	public MouseEventButton getMouseEventButton() {
		return mouseEventButton;
	}

	public void setMouseEventButton(MouseEventButton mouseEventButton) {
		this.mouseEventButton = mouseEventButton;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String toString() {
		return mouseEventButton.name() + " " + x + "x" + y;
	}

}
