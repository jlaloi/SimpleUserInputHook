package simpleuserinputhook.example;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.text.DefaultCaret;

import simpleuserinputhook.windows.WindowsHook;
import simpleuserinputhook.windows.listener.UserInputAdapter;
import simpleuserinputhook.windows.listener.UserInputListener;
import simpleuserinputhook.windows.listener.UserInputMouseEvent;

public class SimpleUserLoggerFrame {

	private static WindowsHook windowsHook;
	private static JTextArea jTextArea;
	private static JButton button;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		button = new JButton("START");
		button.setEnabled(false);
		jTextArea = new JTextArea();
		jTextArea.setEditable(false);
		jTextArea.append("Waiting for hook to be ready... ");
		DefaultCaret caret = (DefaultCaret) jTextArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		JScrollPane scrollPane = new JScrollPane(jTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		JFrame frame = new JFrame("Simple User Logger Example");
		frame.setLayout(new BorderLayout());
		frame.add(button, BorderLayout.NORTH);
		frame.add(scrollPane, BorderLayout.CENTER);
		frame.pack();
		frame.setSize(new Dimension(400, 600));
		frame.setLocationRelativeTo(frame.getParent());
		frame.setVisible(true);

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (windowsHook.isHooking()) {
					try {
						windowsHook.stopHook();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else {
					try {
						windowsHook.startHook();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		UserInputListener hookListener = new UserInputAdapter() {
			public void mouseReleased(UserInputMouseEvent mouseEvent) {
				jTextArea.append("Mouse Released " + mouseEvent + "\n");
			}

			public void mousePressed(UserInputMouseEvent mouseEvent) {
				jTextArea.append("Mouse Pressed " + mouseEvent + "\n");
			}

			public void mouseMouved(UserInputMouseEvent mouseEvent) {
			}

			public void mouseWheelUp(UserInputMouseEvent mouseEvent) {
				jTextArea.append("Mouse Wheel UP " + mouseEvent + "\n");
			}

			public void mouseWheelDown(UserInputMouseEvent mouseEvent) {
				jTextArea.append("Mouse Wheel DOWN " + mouseEvent + "\n");
			}

			public void keyReleased(int keyCode) {
				jTextArea.append("Key Released " + keyCode + "(" + ((char) keyCode) + ")\n");
			}

			public void keyPressed(int keyCode) {
				jTextArea.append("Key Pressed " + keyCode + "(" + ((char) keyCode) + ")\n");
			}

			public void hookInitialised() {
				jTextArea.append("Ready!\n");
				button.setEnabled(true);
			}

			public void hookStopped() {
				jTextArea.append("Hook Stopped!\n");
				button.setText("START");
			}

			public void hookStared() {
				jTextArea.append("Hook Started!\n");
				button.setText("STOP");
			}

		};

		windowsHook = new WindowsHook();
		WindowsHook.addHookListener(hookListener);
		try {
			windowsHook.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
